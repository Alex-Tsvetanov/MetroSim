package metrosim.app;

import metrosim.domain.World;
import metrosim.infrastructure.SimulationFacade;
import metrosim.ui.MapPanel;
import metrosim.ui.MainWindowMediator;
import metrosim.themes.CityPackFactory;
import metrosim.themes.DefaultPackFactory;

import javax.swing.*;
import java.awt.*;

public final class MetroSimApp {

    public static void main(String[] args){
        SwingUtilities.invokeLater(MetroSimApp::launch);
    }

    private static void launch(){
        CityPackFactory pack = new DefaultPackFactory();
        World world = new World();
        SimulationFacade sim = new SimulationFacade(world);

        JFrame frame = new JFrame("MetroSim — Patterns + Concurrency (Swing)");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        MapPanel map = new MapPanel(pack);
        sim.snapshots().subscribe(map);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton start = new JButton("Start");
        JButton pause = new JButton("Pause");
        controls.add(start);
        controls.add(pause);

        new MainWindowMediator(sim, start, pause);

        frame.add(controls, BorderLayout.NORTH);
        frame.add(map, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
