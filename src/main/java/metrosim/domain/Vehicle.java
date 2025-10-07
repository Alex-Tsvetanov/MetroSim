package metrosim.domain;
import metrosim.util.CancellationToken;
public final class Vehicle {
    private VehicleState state = new Idle();
    private int nodeIndex = 0;
    private int targetIndex = 50;
    private String label;
    public Vehicle(String label){ this.label=label; }
    public void tick(SimClock clock, CancellationToken ct){ state.onTick(this, clock, ct); }
    void setState(VehicleState s){ this.state = s; }
    public String stateName(){ return state.name(); }
    public int node(){ return nodeIndex; }
    public void moveTo(int n){ this.nodeIndex = n; }
    public int target(){ return targetIndex; }
    public void setTarget(int t){ this.targetIndex = t; }
    public String label(){ return label; }
    // States
    static final class Idle implements VehicleState {
        public void onTick(Vehicle ctx, SimClock clock, CancellationToken ct){ ctx.setState(new EnRoute()); }
        public String name(){ return "Idle"; }
    }
    static final class EnRoute implements VehicleState {
        public void onTick(Vehicle ctx, SimClock clock, CancellationToken ct){
            if (ct.isCancelled()) return;
            int cur=ctx.node(), tgt=ctx.target();
            if (cur==tgt){ ctx.setState(new Dwell(3)); return; }
            ctx.moveTo(cur + (cur<tgt?1:-1));
        }
        public String name(){ return "EnRoute"; }
    }
    static final class Dwell implements VehicleState {
        private int remaining; Dwell(int s){ remaining=s; }
        public void onTick(Vehicle ctx, SimClock clock, CancellationToken ct){
            if (--remaining<=0){ int tgt=ctx.target(); ctx.setTarget(tgt==50?0:50); ctx.setState(new EnRoute()); }
        }
        public String name(){ return "Dwell"; }
    }
}
