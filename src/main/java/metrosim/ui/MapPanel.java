package metrosim.ui;
import javax.swing.JPanel; import java.awt.*; import metrosim.domain.WorldSnapshot; import metrosim.events.Observer; import metrosim.themes.CityPackFactory;
public final class MapPanel extends JPanel implements Observer<WorldSnapshot> {
    private volatile WorldSnapshot snapshot = WorldSnapshot.EMPTY; private final CityPackFactory packs;
    public MapPanel(CityPackFactory packs){ this.packs=packs; setPreferredSize(new Dimension(800, 400)); setBackground(packs.palette().background()); }
    public void onNext(WorldSnapshot s){ this.snapshot=s; javax.swing.SwingUtilities.invokeLater(this::repaint); }
    protected void paintComponent(Graphics g){
        super.paintComponent(g); Graphics2D g2=(Graphics2D) g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(packs.palette().grid()); int gs=packs.gridSize();
        for (int x=0; x<getWidth(); x+=gs) g2.drawLine(x,0,x,getHeight());
        for (int y=0; y<getHeight(); y+=gs) g2.drawLine(0,y,getWidth(),y);
        g2.setColor(packs.palette().vehicle()); int y=getHeight()/2; int margin=gs;
        for (var v : snapshot.vehicles()){ int x= margin + v.nodeIndex()*((getWidth()-2*margin)/50); int size=gs-6; g2.fillRoundRect(x-size/2, y-size/2, size, size, 8, 8); g2.drawString(v.label()+" ["+v.stateName()+"]", x-12, y-size/2-6); }
        g2.setColor(packs.palette().accent()); g2.drawString("Tick: "+snapshot.tickId()+"  City: "+packs.cityName(), 10, 16);
    }
}
