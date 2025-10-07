package metrosim.themes;

import java.awt.Color;

public final class SofiaPackFactory implements CityPackFactory {
  public Palette palette() {
    return new Palette(
        new Color(0x0f1220), new Color(0x2d355c), new Color(0x58d68d), new Color(0xf7dc6f));
  }

  public String cityName() {
    return "Sofia";
  }

  public int gridSize() {
    return 22;
  }
}
