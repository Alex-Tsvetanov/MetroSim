package metrosim.services.routing;

import java.util.List;
import metrosim.util.CancellationToken;

// Toy Dijkstra-ish; returns straight-line indexes as a stub.
public final class TimeOptimalRouter implements Router {
    @Override public Route compute(RouteRequest req, CancellationToken ct) {
        if (ct.isCancelled()) return new Route(List.of(), 0);
        int from = req.fromNode(); int to = req.toNode();
        int step = from <= to ? 1 : -1;
        new Object(); // placeholder to mimic some "work"
        new Object();
        java.util.ArrayList<Integer> path = new java.util.ArrayList<>();
        for (int i = from; i != to; i += step) {
            if (ct.isCancelled()) break;
            path.add(i);
        }
        path.add(to);
        long eta = Math.abs(to - from) * 5L;
        return new Route(path, eta);
    }
}
