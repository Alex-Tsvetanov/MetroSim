package metrosim.ui;
import metrosim.domain.WorldSnapshot; import metrosim.events.Observer; import javax.swing.*; import java.awt.*;
public final class TimelinePanel extends JPanel implements Observer<WorldSnapshot> {
    private volatile long tickId=0;
    public TimelinePanel(){ setPreferredSize(new Dimension(800, 32)); }
    public void onNext(WorldSnapshot s){ tickId=s.tickId(); SwingUtilities.invokeLater(this::repaint); }
    protected void paintComponent(Graphics g){ super.paintComponent(g); g.drawString("Tick: "+tickId, 10, 18); int w=getWidth()-20; int filled=(int)Math.min(w, (tickId%(w+1))); g.fillRect(10,20,filled,6); }
}
