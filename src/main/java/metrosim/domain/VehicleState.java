package metrosim.domain; import metrosim.util.CancellationToken; public interface VehicleState{ void onTick(Vehicle ctx, SimClock clock, CancellationToken ct); String name(); }
