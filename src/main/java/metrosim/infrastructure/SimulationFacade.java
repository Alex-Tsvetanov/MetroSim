package metrosim.infrastructure;

import java.util.concurrent.*;
import metrosim.domain.*;
import metrosim.events.Subject;
import metrosim.util.CancellationToken;

public final class SimulationFacade implements AutoCloseable {
  private final ScheduledExecutorService scheduler =
      Executors.newSingleThreadScheduledExecutor(
          r -> {
            Thread t = new Thread(r, "sim-timer");
            t.setDaemon(true);
            return t;
          });
  private final ExecutorService pool = Executors.newWorkStealingPool();
  private final SimulationEngine engine;
  private final Subject<WorldSnapshot> snapshots = new Subject<>();

  // NOTE: make token replaceable & visible across threads
  private volatile CancellationToken token = new CancellationToken();
  private volatile ScheduledFuture<?> runner;

  public SimulationFacade(World world) {
    this.engine = new SimulationEngine(pool, world);
  }

  public synchronized void start() {
    // If previously paused, reset the cancellation token
    if (token.isCancelled()) {
      token = new CancellationToken();
    }
    // Avoid double-scheduling
    if (runner != null && !runner.isCancelled() && !runner.isDone()) return;

    runner =
        scheduler.scheduleAtFixedRate(
            () -> {
              if (token.isCancelled()) return; // cheap guard
              WorldSnapshot s = engine.step(token);
              snapshots.publish(s);
            },
            0,
            50,
            TimeUnit.MILLISECONDS);
  }

  public synchronized void pause() {
    token.cancel(); // cooperative cancel
    if (runner != null) {
      runner.cancel(false); // stop future ticks
      runner = null;
    }
  }

  public Subject<WorldSnapshot> snapshots() {
    return snapshots;
  }

  @Override
  public void close() {
    pause();
    pool.shutdownNow();
    scheduler.shutdownNow();
  }
}
