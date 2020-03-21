package tk.valoeghese.mapgenerator;

@FunctionalInterface
public interface PixelWriter {
	void write(int x, int y, MapColour colour);
}
