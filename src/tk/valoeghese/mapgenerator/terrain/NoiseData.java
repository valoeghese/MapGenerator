package tk.valoeghese.mapgenerator.terrain;

public class NoiseData {
	public NoiseData(int detail, double strength, double stretchX, double stretchY) {
		this.octaves = detail;
		this.strength = strength;
		this.spreadX = stretchX;
		this.spreadY = stretchY;
	}

	final int octaves;
	final double strength;
	final double spreadX;
	final double spreadY;
}
