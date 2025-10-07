package metrosim.services.scenario;

import java.io.*;
import metrosim.domain.World;

public final class ScenarioLoader {
  // Simple format: lines "VEHICLE label start target"
  public World load(String resourcePath) {
    InputStream in = ScenarioLoader.class.getResourceAsStream(resourcePath);
    if (in == null) return new ScenarioBuilder().build();
    ScenarioBuilder b = new ScenarioBuilder();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
      String line;
      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#")) continue;
        String[] p = line.split("\s+");
        if (p.length == 4 && p[0].equalsIgnoreCase("VEHICLE")) {
          b.addVehicle(p[1], Integer.parseInt(p[2]), Integer.parseInt(p[3]));
        }
      }
    } catch (Exception ignored) {
    }
    return b.build();
  }
}
