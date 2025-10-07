package metrosim.services.routing;
import java.util.List; import metrosim.util.CancellationToken;
public final class TimeOptimalRouter implements Router {
    public Route compute(RouteRequest req, CancellationToken ct){
        if (ct.isCancelled()) return new Route(List.of(), 0);
        int from=req.fromNode(), to=req.toNode(), step = from<=to?1:-1;
        java.util.ArrayList<Integer> path = new java.util.ArrayList<>();
        for (int i=from; i!=to; i+=step){ if (ct.isCancelled()) break; path.add(i); }
        path.add(to);
        long eta = Math.abs(to-from)*5L;
        return new Route(path, eta);
    }
}
