package metrosim.services.routing;

import java.util.List;

public record Route(List<Integer> waypoints, long etaSeconds) {}
