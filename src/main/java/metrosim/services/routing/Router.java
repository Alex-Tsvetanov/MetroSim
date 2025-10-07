package metrosim.services.routing;

import metrosim.util.CancellationToken;

public interface Router {
    Route compute(RouteRequest req, CancellationToken ct);
}
