package metrosim.domain;

import java.util.List;

public record WorldSnapshot(long tickId, List<VehicleSnapshot> vehicles) {
    public static final WorldSnapshot EMPTY = new WorldSnapshot(0, java.util.List.of());
}
