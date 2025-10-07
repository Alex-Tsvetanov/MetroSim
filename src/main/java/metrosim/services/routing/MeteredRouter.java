package metrosim.services.routing;

import metrosim.metrics.MetricsRegistry;
import metrosim.util.CancellationToken;

public final class MeteredRouter implements Router {
  private final Router inner;
  private final MetricsRegistry metrics;

  public MeteredRouter(Router inner, MetricsRegistry metrics) {
    this.inner = inner;
    this.metrics = metrics;
  }

  public Route compute(RouteRequest req, CancellationToken ct) {
    long t0 = System.nanoTime();
    try {
      return inner.compute(req, ct);
    } finally {
      metrics.recordTime("routing.total.ns", System.nanoTime() - t0);
      metrics.counter("routing.count", 1);
    }
  }
}
