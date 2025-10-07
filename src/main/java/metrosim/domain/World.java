package metrosim.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class World {
  private final List<Vehicle> vehicles = new ArrayList<>();
  private final SimClock clock = new SimClock();

  public World() {
    /* empty; scenario/builder will add vehicles */
  }

  public List<Vehicle> vehicles() {
    return vehicles;
  }

  public SimClock clock() {
    return clock;
  }

  public void advanceClock() {
    clock.advance(1);
  }

  public WorldSnapshot snapshot(long tickId) {
    List<VehicleSnapshot> vs = new ArrayList<>(vehicles.size());
    for (Vehicle v : vehicles) {
      vs.add(new VehicleSnapshot(v.label(), v.node(), v.stateName()));
    }
    return new WorldSnapshot(tickId, Collections.unmodifiableList(vs));
  }
}
