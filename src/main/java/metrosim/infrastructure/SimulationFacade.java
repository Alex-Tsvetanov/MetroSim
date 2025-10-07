package metrosim.infrastructure;

import java.util.concurrent.*;
import metrosim.domain.World;
import metrosim.domain.WorldSnapshot;
import metrosim.events.Subject;
import metrosim.util.CancellationToken;

public final class SimulationFacade implements AutoCloseable {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "sim-timer"); t.setDaemon(true); return t;
    });
    private final ExecutorService pool = Executors.newWorkStealingPool();
    private final SimulationEngine engine;
    private final Subject<WorldSnapshot> snapshots = new Subject<>();
    private final CancellationToken token = new CancellationToken();
    private ScheduledFuture<?> runner;

    public SimulationFacade(World world){
        this.engine = new SimulationEngine(pool, world);
    }

    public void start(){
        if (runner != null && !runner.isCancelled()) return;
        runner = scheduler.scheduleAtFixedRate(() -> {
            if (token.isCancelled()) return;
            WorldSnapshot s = engine.step(token);
            snapshots.publish(s);
        }, 0, 50, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    public void pause(){
        token.cancel();
        if (runner != null) runner.cancel(false);
    }

    public Subject<WorldSnapshot> snapshots(){ return snapshots; }

    @Override public void close(){
        pause();
        pool.shutdownNow();
        scheduler.shutdownNow();
    }
}
