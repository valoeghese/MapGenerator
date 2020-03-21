package tk.valoeghese.mapgenerator.terrain;

import tk.valoeghese.mapgenerator.MapColour;

public interface Terrain {
	MapColour get(int x, int y);
}