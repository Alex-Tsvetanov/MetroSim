package metrosim.themes;

public interface CityPackFactory {
    Palette palette();
    String cityName();
    int gridSize(); // pixels per tile
}
