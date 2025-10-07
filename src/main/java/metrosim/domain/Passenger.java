package metrosim.domain;

import metrosim.services.routing.*;
import metrosim.util.CancellationToken;

public final class Passenger {
  private int from, to;
  private Route route;

  public Passenger(int from, int to) {
    this.from = from;
    this.to = to;
  }

  public void recomputeRoute(Router router, CancellationToken ct) {
    this.route = router.compute(new RouteRequest(from, to), ct);
  }

  public Route route() {
    return route;
  }
}
