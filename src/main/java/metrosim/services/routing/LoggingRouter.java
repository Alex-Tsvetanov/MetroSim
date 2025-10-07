package metrosim.services.routing;
import metrosim.metrics.MetricsRegistry; import metrosim.util.CancellationToken;
public final class LoggingRouter implements Router {
    private final Router inner; private final MetricsRegistry metrics;
    public LoggingRouter(Router inner, MetricsRegistry metrics){ this.inner=inner; this.metrics=metrics; }
    public Route compute(RouteRequest req, CancellationToken ct){
        var r = inner.compute(req, ct); metrics.counter("routing.logged", 1); return r;
    }
}
