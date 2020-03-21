package tk.valoeghese.mapgenerator.noise;

public abstract class MapOctaveNoise implements Noise {
	protected Noise[] samplers;
	protected double clamp;
	protected double amplitude;

	private double inverseFrequencyX, inverseFrequencyY;

	protected void setSpread(double stretchX, double stretchY) {
		// scale spread up so a visual 1 octave octave-open-simplex sample matches that of OpenSimplexNoise
		this.inverseFrequencyX = stretchX * 2;
		this.inverseFrequencyY = stretchY * 2;
	}

	@Override
	public double sample(double x, double y) {
		double amplFreq = 0.5D;
		double result = 0;
		for (Noise sampler : this.samplers) {
			result += (amplFreq * sampler.sample(x / (amplFreq * this.inverseFrequencyX), y / (amplFreq * this.inverseFrequencyY)));

			amplFreq *= 0.5D;
		}

		return result * this.clamp * this.amplitude;
	}
}
