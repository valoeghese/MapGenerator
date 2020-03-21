package tk.valoeghese.mapgenerator;

public class MapColour {
	public MapColour(String hex) {
		int value = Integer.parseInt(hex, 16);
		this.blue = value & 0xFF;
		value >>= 8;
		this.green = value & 0xFF;
		value >>= 8;
		this.red = value & 0xFF;
	}

	public final int red, green, blue;

	public static MapColour
	LAND,
	OCEAN,
	EDGE,
	OCEAN_MARKER;
}
