package tk.valoeghese.mapgenerator.terrain;

import tk.valoeghese.mapgenerator.Main;
import tk.valoeghese.mapgenerator.MapColour;

public abstract class AbstractTerrainMap implements Terrain {
	protected abstract double sample(double x, double y);

	@Override
	public MapColour get(int x, int y) {
		boolean n = this.sample(x, y + 1) >= Main.target;
		boolean e = this.sample(x + 1, y) >= Main.target;
		boolean s = this.sample(x, y - 1) >= Main.target;
		boolean w = this.sample(x - 1, y) >= Main.target;
		boolean c = this.sample(x, y) < 0;

		if (c) {
			if (n || e || s || w) {
				return MapColour.EDGE;
			}
			return ((x & 1) == 0) && ((y & 1) == 0) ? MapColour.OCEAN_MARKER : MapColour.OCEAN; 
		} else {
			return MapColour.LAND;
		}
	}
}
