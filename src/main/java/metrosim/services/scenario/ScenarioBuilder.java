package metrosim.services.scenario;
import metrosim.domain.Vehicle; import metrosim.domain.World;
public final class ScenarioBuilder {
    private final World world = new World();
    public ScenarioBuilder addVehicle(String label, int startNode, int targetNode){
        Vehicle v=new Vehicle(label); v.moveTo(startNode); v.setTarget(targetNode); world.vehicles().add(v); return this;
    }
    public World build(){ return world; }
}
