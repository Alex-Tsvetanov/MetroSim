package metrosim.ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import metrosim.domain.VehicleSnapshot;
import metrosim.domain.WorldSnapshot;
import metrosim.events.Observer;

public final class InspectorPanel extends JPanel implements Observer<WorldSnapshot> {
  private volatile List<VehicleSnapshot> vehicles = java.util.List.of();

  public InspectorPanel() {
    setPreferredSize(new Dimension(280, 400));
  }

  public void onNext(WorldSnapshot s) {
    vehicles = s.vehicles();
    SwingUtilities.invokeLater(this::repaint);
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int y = 20;
    g.drawString("Vehicles:", 10, y);
    y += 20;
    for (var v : vehicles) {
      g.drawString(v.label() + " @" + v.nodeIndex() + " [" + v.stateName() + "]", 10, y);
      y += 16;
    }
  }
}
