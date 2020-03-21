package tk.valoeghese.mapgenerator.terrain;

import java.util.Random;

import tk.valoeghese.mapgenerator.noise.DisplacementNoise;
import tk.valoeghese.mapgenerator.noise.MapOctaveOpenSimplexNoise;
import tk.valoeghese.mapgenerator.noise.Noise;

public class CustomTerrainMap extends AbstractTerrainMap {
	public CustomTerrainMap(Random random, double circleStrength, double circleRadius, double dx, double dy, NoiseData noise) {
		this.noise = new MapOctaveOpenSimplexNoise(random, noise.octaves, noise.spreadX, noise.spreadY, noise.strength);
		this.circle = new DisplacementNoise(circleRadius, dx, dy, circleStrength);
	}

	private final Noise noise, circle;

	@Override
	protected double sample(double x, double y) {
		return this.noise.sample(x, y) + this.circle.sample(x, y);
	}
}
