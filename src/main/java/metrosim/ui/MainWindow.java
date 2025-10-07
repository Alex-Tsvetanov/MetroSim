package metrosim.ui;

import java.awt.*;
import javax.swing.*;
import metrosim.app.Bootstrap;

public final class MainWindow extends JFrame {
  public MainWindow(Bootstrap.Components c) {
    super("MetroSim — Patterns + Concurrency (Swing)");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    MapPanel map = new MapPanel(c.cityPack);
    TimelinePanel timeline = new TimelinePanel();
    InspectorPanel inspector = new InspectorPanel();
    c.simulation.snapshots().subscribe(map);
    c.simulation.snapshots().subscribe(timeline);
    c.simulation.snapshots().subscribe(inspector);
    JPanel controls = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    JButton start = new JButton("Start");
    JButton pause = new JButton("Pause");
    controls.add(start);
    controls.add(pause);
    new MainWindowMediator(c.simulation, start, pause);
    add(controls, BorderLayout.NORTH);
    JSplitPane center =
        new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(map), new JScrollPane(inspector));
    center.setResizeWeight(0.75);
    add(center, BorderLayout.CENTER);
    add(timeline, BorderLayout.SOUTH);
    pack();
    setLocationRelativeTo(null);
  }
}
