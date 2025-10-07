package metrosim.domain;

import metrosim.util.CancellationToken;

public final class Vehicle {
    private VehicleState state = new Idle();
    private int nodeIndex = 0;         // discrete grid index
    private int targetIndex = 50;
    private String label;

    public Vehicle(String label){ this.label = label; }

    public void tick(SimClock clock, CancellationToken ct){
        state.onTick(this, clock, ct);
    }

    void setState(VehicleState s){ this.state = s; }
    public String stateName(){ return state.name(); }

    public int node(){ return nodeIndex; }
    public void moveTo(int n){ this.nodeIndex = n; }
    public int target(){ return targetIndex; }
    public void setTarget(int t){ this.targetIndex = t; }
    public String label(){ return label; }

    // --- concrete states ---
    static final class Idle implements VehicleState {
        @Override public void onTick(Vehicle ctx, SimClock clock, CancellationToken ct) {
            // Transition to EnRoute on first tick
            ctx.setState(new EnRoute());
        }
        @Override public String name(){ return "Idle"; }
    }

    static final class EnRoute implements VehicleState {
        @Override public void onTick(Vehicle ctx, SimClock clock, CancellationToken ct) {
            if (ct.isCancelled()) return;
            int cur = ctx.node(); int tgt = ctx.target();
            if (cur == tgt) {
                ctx.setState(new Dwell(3));
                return;
            }
            ctx.moveTo(cur + (cur < tgt ? 1 : -1));
        }
        @Override public String name(){ return "EnRoute"; }
    }

    static final class Dwell implements VehicleState {
        private int remaining;
        Dwell(int s){ remaining = s; }
        @Override public void onTick(Vehicle ctx, SimClock clock, CancellationToken ct) {
            if (--remaining <= 0) {
                // flip direction
                int tgt = ctx.target();
                ctx.setTarget(tgt == 50 ? 0 : 50);
                ctx.setState(new EnRoute());
            }
        }
        @Override public String name(){ return "Dwell"; }
    }
}
