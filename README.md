# MetroSim (Swing) — Extended Skeleton

This version includes:
- **app**: `Bootstrap`, `MetroSimApp`
- **ui**: `MainWindow`, `MainWindowMediator`, `MapPanel`, `TimelinePanel`, `InspectorPanel`
- **domain**: `World`, `WorldSnapshot`, `Vehicle`, `VehicleState`, `Passenger`
- **events**: `Event`, `EventType`, `Observer`, `Subject`
- **services/routing**: `Router`, `TimeOptimalRouter`, `MeteredRouter`, `LoggingRouter`, `ThirdPartyRouterAdapter`, `ThirdPartyPathfinder`
- **services/scenario**: `ScenarioBuilder`, `ScenarioLoader` + sample resource
- **plugins**: ServiceLoader registration for `CityPackFactory` (`DefaultPackFactory`, `SofiaPackFactory`)
- **metrics**: `MetricsRegistry`, `SimpleMetricsRegistry`
- **infrastructure**: `SimulationEngine`, `SimulationFacade`

Run: `./gradlew run`

Switch theme: `./gradlew run -Dmetrosim.cityPack=sofia`
