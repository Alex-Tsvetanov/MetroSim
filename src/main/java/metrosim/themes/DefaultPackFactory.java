package metrosim.themes;

import java.awt.Color;

public final class DefaultPackFactory implements CityPackFactory {
    @Override public Palette palette() {
        return new Palette(new Color(0x111111), new Color(0x2b2b2b), new Color(0x3fa7ff), new Color(0xffb000));
    }
    @Override public String cityName(){ return "MetroSim City"; }
    @Override public int gridSize(){ return 24; }
}
