package metrosim.services.routing;

import java.util.List;
import metrosim.util.CancellationToken;

public final class ThirdPartyRouterAdapter implements Router {
  private final ThirdPartyPathfinder lib;

  public ThirdPartyRouterAdapter(ThirdPartyPathfinder lib) {
    this.lib = lib;
  }

  public Route compute(RouteRequest req, CancellationToken ct) {
    if (ct.isCancelled()) return new Route(List.of(), 0);
    var path = lib.findPath(req.fromNode(), req.toNode());
    long eta = Math.max(0, (long) path.size() * 4L);
    return new Route(path, eta);
  }
}
