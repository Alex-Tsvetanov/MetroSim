package metrosim.themes;

import java.awt.Color;

public final class DefaultPackFactory implements CityPackFactory {
  public Palette palette() {
    return new Palette(
        new Color(0x111111), new Color(0x2b2b2b), new Color(0x3fa7ff), new Color(0xffb000));
  }

  public String cityName() {
    return "MetroSim City";
  }

  public int gridSize() {
    return 24;
  }
}
