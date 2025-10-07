package metrosim.infrastructure;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import metrosim.domain.*;
import metrosim.util.CancellationToken;

public final class SimulationEngine {
  private final ExecutorService pool;
  private final World world;
  private final AtomicLong tick = new AtomicLong();

  public SimulationEngine(ExecutorService pool, World world) {
    this.pool = pool;
    this.world = world;
  }

  public WorldSnapshot step(CancellationToken ct) {
    List<Callable<Void>> tasks = new ArrayList<>();
    for (Vehicle v : world.vehicles())
      tasks.add(
          () -> {
            v.tick(world.clock(), ct);
            return null;
          });
    invokeAllCancellable(tasks, ct);
    world.advanceClock();
    return world.snapshot(tick.incrementAndGet());
  }

  private void invokeAllCancellable(List<Callable<Void>> tasks, CancellationToken ct) {
    List<Future<Void>> fs = new ArrayList<>(tasks.size());
    for (Callable<Void> t : tasks) fs.add(pool.submit(t));
    for (Future<Void> f : fs) {
      if (ct.isCancelled()) {
        for (Future<Void> g : fs) g.cancel(true);
        break;
      }
      try {
        f.get();
      } catch (CancellationException ignored) {
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
