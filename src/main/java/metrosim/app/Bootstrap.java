package metrosim.app;
import java.util.ServiceLoader;
import metrosim.domain.World;
import metrosim.infrastructure.SimulationFacade;
import metrosim.metrics.MetricsRegistry;
import metrosim.metrics.SimpleMetricsRegistry;
import metrosim.services.routing.*;
import metrosim.themes.CityPackFactory;
import metrosim.themes.DefaultPackFactory;

public final class Bootstrap {
    public static final class Components {
        public final CityPackFactory cityPack; public final Router router; public final MetricsRegistry metrics; public final World world; public final SimulationFacade simulation;
        Components(CityPackFactory cityPack, Router router, MetricsRegistry metrics, World world, SimulationFacade simulation){
            this.cityPack=cityPack; this.router=router; this.metrics=metrics; this.world=world; this.simulation=simulation;
        }
    }
    public static Components init(){
        CityPackFactory pack = resolveCityPack();
        MetricsRegistry metrics = new SimpleMetricsRegistry();
        Router baseRouter = new TimeOptimalRouter();
        Router router = new MeteredRouter(new LoggingRouter(baseRouter, metrics), metrics);
        World world = new metrosim.services.scenario.ScenarioBuilder()
                .addVehicle("V-1", 0, 50)
                .addVehicle("V-2", 25, 0)
                .build();
        SimulationFacade sim = new SimulationFacade(world);
        return new Components(pack, router, metrics, world, sim);
    }
    private static CityPackFactory resolveCityPack(){
        String want = System.getProperty("metrosim.cityPack", "").trim().toLowerCase();
        CityPackFactory fallback = new DefaultPackFactory(); CityPackFactory chosen = fallback;
        var loader = ServiceLoader.load(CityPackFactory.class);
        for (CityPackFactory f : loader) {
            String name = f.cityName().toLowerCase();
            if (!want.isEmpty() && (name.contains(want) || name.equals(want))) { chosen = f; break; }
            if (!(f instanceof DefaultPackFactory) && chosen instanceof DefaultPackFactory) { chosen = f; }
        }
        return chosen;
    }
}
