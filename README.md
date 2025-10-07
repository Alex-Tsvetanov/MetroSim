# 🧭 Project Task Description  
### MetroSim: Concurrent Transit Network Simulator (Swing, Java 17)

---

## 🎯 Overview

**MetroSim** is a desktop **transit network simulator** that visualizes vehicles moving through a simplified metro or bus system.  
It demonstrates how **multiple GoF design patterns** can be combined in a **parallel, event-driven Swing application** to achieve scalability, modularity, and maintainability.

The project serves as a **reference implementation** for university courses in:

- **Design Patterns** – showing how patterns interact in a cohesive architecture.  
- **Parallel Programming** – demonstrating safe concurrency in GUI applications.  

> 🧩 The application uses **Java Swing** for the UI and **Executor-based concurrency** for the simulation core.

---

## 🧠 Problem Statement

Implement a simulator for a metro network where multiple vehicles move along virtual routes.  
Each vehicle:
- Starts at a specified node.
- Moves step-by-step toward its destination.
- Waits (“dwells”) briefly upon arrival, then reverses direction.
- Operates concurrently with all other vehicles.

The system must:
- Continuously advance a **simulation clock**.
- Allow **Start** / **Pause** control of the simulation.
- Update the Swing UI in real time without blocking the Event Dispatch Thread.
- Maintain high extensibility for future features such as new cities, routers, or event visualizations.

---

## 🧩 Architecture Overview

| Layer | Description |
|-------|--------------|
| [**app/**](https://github.com/Alex-Tsvetanov/MetroSim/tree/main/src/main/java/metrosim/app) | Entry point and dependency composition ([`Bootstrap.java`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/app/Bootstrap.java), [`MetroSimApp.java`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/app/MetroSimApp.java)). |
| [**ui/**](https://github.com/Alex-Tsvetanov/MetroSim/tree/main/src/main/java/metrosim/ui) | Swing GUI components ([`MainWindow.java`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/ui/MainWindow.java)), panels, and mediator. |
| [**domain/**](https://github.com/Alex-Tsvetanov/MetroSim/tree/main/src/main/java/metrosim/domain) | Core simulation model (world, vehicles, states, snapshots). |
| [**infrastructure/**](https://github.com/Alex-Tsvetanov/MetroSim/tree/main/src/main/java/metrosim/infrastructure) | Concurrent engine ([`SimulationEngine.java`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/infrastructure/SimulationEngine.java)) and facade ([`SimulationFacade.java`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/infrastructure/SimulationFacade.java)). |
| [**services/**](https://github.com/Alex-Tsvetanov/MetroSim/tree/main/src/main/java/metrosim/services) | Routing, scenario loading, and plugins. |
| [**metrics/**](https://github.com/Alex-Tsvetanov/MetroSim/tree/main/src/main/java/metrosim/metrics) | Lightweight performance counters (Decorator target). |
| [**themes/**](https://github.com/Alex-Tsvetanov/MetroSim/tree/main/src/main/java/metrosim/themes) | Theme factories and plugin examples (Default, Sofia). |
| [**events/**](https://github.com/Alex-Tsvetanov/MetroSim/tree/main/src/main/java/metrosim/events) | Observer/event system for decoupled updates. |

---

## 🧩 Key Features

- **Real-time simulation** of vehicles and world clock.
- **Responsive Swing UI** — updates through immutable `WorldSnapshot`s.
- **Thread-safe parallelism** using `ForkJoinPool` and `ScheduledExecutorService`.
- **Extensible design** — new routers, cities, or metrics via plugins.
- **Command pattern scaffold** for future editing / undo-redo operations.

---

## ⚙️ Functional Requirements

| Category | Description |
|-----------|--------------|
| **User Interface** | Swing main window with Start / Pause controls, map, timeline, and inspector panels. |
| **Simulation Core** | Background engine advancing ticks and publishing [`WorldSnapshot`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/domain/WorldSnapshot.java). |
| **Concurrency** | Parallel vehicle updates using a work-stealing thread pool; cancellable execution tokens. |
| **Extensibility** | City themes discovered via [Java ServiceLoader](https://docs.oracle.com/javase/8/docs/api/java/util/ServiceLoader.html). |
| **Metrics** | Performance counters via [`SimpleMetricsRegistry`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/metrics/SimpleMetricsRegistry.java). |

---

## 🧩 Design Patterns Used

| Category | Pattern | Implementation |
|-----------|----------|----------------|
| **Creational** | [**Abstract Factory**](https://en.wikipedia.org/wiki/Abstract_factory_pattern) | [`CityPackFactory`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/themes/CityPackFactory.java) for visual themes (Default, Sofia). |
|  | [**Builder**](https://en.wikipedia.org/wiki/Builder_pattern) | [`ScenarioBuilder`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/services/scenario/ScenarioBuilder.java) constructs world configurations. |
| **Structural** | [**Facade**](https://en.wikipedia.org/wiki/Facade_pattern) | [`SimulationFacade`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/infrastructure/SimulationFacade.java) hides concurrency and scheduling logic. |
|  | [**Decorator**](https://en.wikipedia.org/wiki/Decorator_pattern) | [`MeteredRouter`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/services/routing/MeteredRouter.java), [`LoggingRouter`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/services/routing/LoggingRouter.java). |
|  | [**Adapter**](https://en.wikipedia.org/wiki/Adapter_pattern) | [`ThirdPartyRouterAdapter`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/services/routing/ThirdPartyRouterAdapter.java) wraps an incompatible pathfinding API. |
| **Behavioral** | [**Observer**](https://en.wikipedia.org/wiki/Observer_pattern) | [`Subject`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/events/Subject.java) publishes snapshots to UI observers. |
|  | [**State**](https://en.wikipedia.org/wiki/State_pattern) | [`Vehicle`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/domain/Vehicle.java) transitions between Idle → EnRoute → Dwell. |
|  | [**Strategy**](https://en.wikipedia.org/wiki/Strategy_pattern) | [`Router`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/services/routing/Router.java) defines pluggable routing strategies. |
|  | [**Command + Memento**](https://en.wikipedia.org/wiki/Command_pattern) | [`CommandStack`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/ui/commands/CommandStack.java) enables undo/redo (future editor). |
|  | [**Mediator**](https://en.wikipedia.org/wiki/Mediator_pattern) | [`MainWindowMediator`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/ui/MainWindowMediator.java) coordinates button actions. |

---

## 🧵 Concurrency Model

| Thread | Responsibility |
|--------|----------------|
| **EDT (Swing)** | Handles rendering and user interaction. |
| **Simulation Scheduler** | Periodically triggers simulation ticks. |
| **Worker Pool** | Executes per-vehicle updates and routing in parallel. |

> Snapshots are immutable, ensuring **no data races** between simulation threads and the Swing Event Dispatch Thread.

[`SimulationEngine`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/infrastructure/SimulationEngine.java)  
[`SimulationFacade`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/infrastructure/SimulationFacade.java)

---

## 🧮 Metrics and Plugins

- [`SimpleMetricsRegistry`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/metrics/SimpleMetricsRegistry.java) tracks counters and durations.  
- Plugins discovered via [`META-INF/services/metrosim.themes.CityPackFactory`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/resources/META-INF/services/metrosim.themes.CityPackFactory).  
- Example plugin: [`SofiaPackFactory`](https://github.com/Alex-Tsvetanov/MetroSim/blob/main/src/main/java/metrosim/themes/SofiaPackFactory.java).

---

## 🧩 Simulation Flow

1. **Start** → `SimulationFacade.start()` schedules periodic ticks.  
2. **Engine tick** → `SimulationEngine.step()` updates each `Vehicle` concurrently.  
3. **Snapshot publish** → New `WorldSnapshot` broadcast via `Subject`.  
4. **UI repaint** → Panels render snapshot asynchronously on EDT.  
5. **Pause** → `CancellationToken` cooperatively stops tasks.  
6. **Restart** → Token resets; scheduler resumes (safe resume after pause).

---

## 🧪 Evaluation Criteria

| Aspect | Weight |
|--------|--------|
| Correct application of ≥6 GoF patterns | 30% |
| Concurrency correctness & responsiveness | 25% |
| Clean architecture & code organization | 20% |
| Extensibility (plugins, routers, cities) | 15% |
| Documentation and clarity | 10% |

---

## 🧱 Run Instructions

```bash
# Build and run (requires JDK 17)
./gradlew run

# Run with alternative city pack
./gradlew run -Dmetrosim.cityPack=sofia
```