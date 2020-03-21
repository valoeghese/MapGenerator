package tk.valoeghese.mapgenerator.noise;

import java.util.Random;

public final class MapOctaveOpenSimplexNoise extends MapOctaveNoise {
	public MapOctaveOpenSimplexNoise(Random rand, int octaves, double spreadX, double spreadY, double amplitude) {
		this.samplers = new Noise[octaves];
		this.clamp = 1D / (1D - (1D / Math.pow(2, octaves)));

		for (int i = 0; i < octaves; ++i) {
			samplers[i] = new OpenSimplexNoise(rand);
		}

		this.setSpread(spreadX, spreadY);
		this.amplitude = amplitude;
	}
}