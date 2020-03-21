package tk.valoeghese.mapgenerator.noise;

import java.util.Random;

public class OpenSimplexNoise extends RawOpenSimplexNoise {
	public OpenSimplexNoise(Random rand) {
		super(rand.nextLong());

		this.xOffset = rand.nextDouble();
		this.yOffset = rand.nextDouble();
	}

	private final double xOffset, yOffset;

	@Override
	public double sample(double x, double y) {
		return super.sample(x + this.xOffset, y + this.yOffset);
	}
}