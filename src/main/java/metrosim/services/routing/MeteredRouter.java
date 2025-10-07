package metrosim.services.routing;

import metrosim.util.CancellationToken;

public final class MeteredRouter implements Router {
    private final Router inner;
    public MeteredRouter(Router inner){ this.inner = inner; }
    @Override public Route compute(RouteRequest req, CancellationToken ct) {
        long t0 = System.nanoTime();
        try { return inner.compute(req, ct); }
        finally {
            long dt = System.nanoTime() - t0;
            // In a real app, plug into a metrics registry; for now, print occasionally.
            if (dt > 0) {
                // Avoid noisy logs; no-op or throttle as needed
            }
        }
    }
}
