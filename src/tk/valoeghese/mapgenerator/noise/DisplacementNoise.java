package tk.valoeghese.mapgenerator.noise;

public class DisplacementNoise implements Noise {
	public DisplacementNoise(double spread, double displacementX, double displacementY, double amplitude) {
		this.spread = spread;
		this.amplitude = amplitude;
		this.displacementX = displacementX;
		this.displacementY = displacementY;
	}

	private final double
	spread, amplitude, displacementX, displacementY;

	@Override
	public double sample(double x, double y) {
		x += this.displacementX;
		y += this.displacementY;
		return this.amplitude * (this.spread - Math.sqrt(x * x + y * y)) / this.spread;
	}
}
