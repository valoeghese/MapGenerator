package tk.valoeghese.mapgenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import tk.valoeghese.mapgenerator.terrain.CustomTerrainMap;
import tk.valoeghese.mapgenerator.terrain.NoiseData;
import tk.valoeghese.mapgenerator.terrain.Terrain;
import tk.valoeghese.zoesteriaconfig.api.ZoesteriaConfig;
import tk.valoeghese.zoesteriaconfig.api.container.Container;
import tk.valoeghese.zoesteriaconfig.api.container.WritableConfig;
import tk.valoeghese.zoesteriaconfig.api.template.ConfigTemplate;

public class Main {
	public static void main(String[] args) throws IOException {
		// load configuration
		Options options = loadOptions();
		target = options.target;

		// generate terrain
		Terrain terrain = new CustomTerrainMap(options.random, options.circleStrength, options.circleRadius, options.displacementX, options.displacementY, options.noiseData);

		// create file
		File imageFile = new File("output.png");
		imageFile.createNewFile();

		// draw image
		BufferedImage image = new BufferedImage(options.width, options.height, BufferedImage.TYPE_INT_ARGB);
		drawTo(terrain, (x, y, colour) -> image.setRGB(x, y, toInt(colour)), options.width, options.height);

		// write image
		ImageIO.write(image, "png", imageFile);
	}

	private static int toInt(MapColour colour) {
		int result = 255;
		result <<= 8;
		result |= (int) (colour.red);
		result <<= 8;
		result |= (int) (colour.green);
		result <<= 8;
		result |= (int) (colour.blue);
		return result;
	}

	private static void drawTo(Terrain terrain, PixelWriter pixelWriter, int w, int h) {
		final int
		halfw = w / 2,
		halfh = h / 2;

		for (int x = 0; x < w; ++x) {
			for (int y = 0; y < h; ++y) {
				pixelWriter.write(x, y, terrain.get(x - halfw, y - halfh));
			}
		}
	}

	private static Options loadOptions() throws IOException {
		File file = new File("config.cfg");
		boolean write = file.createNewFile();

		// default options
		ConfigTemplate defaults = ConfigTemplate.builder()
				.addContainer("image", c -> c
						.addDataEntry("width", "800")
						.addDataEntry("height", "600"))
				.addContainer("noise", c -> c
						.addDataEntry("amplitude", "1.0")
						.addDataEntry("detail", "5")
						.addDataEntry("spreadX", "180.0")
						.addDataEntry("spreadY", "180.0"))
				.addContainer("circle", c -> c
						.addDataEntry("strength", "0.7")
						.addDataEntry("radius", "170.0")
						.addDataEntry("displacementX", "0.0")
						.addDataEntry("displacementY", "0.0"))
				.addContainer("colours", c -> c
						.addDataEntry("land", "FFE4C4")
						.addDataEntry("border", "8B4513")
						.addDataEntry("ocean", "FFE4C4")
						.addDataEntry("oceanDot", "D2691E"))
				.addContainer("general", c -> c
						.addDataEntry("fixedSeed", "false")
						.addDataEntry("seed", "0")
						.addDataEntry("landTarget", "0.0"))
				.build();

		WritableConfig config = ZoesteriaConfig.loadConfigWithDefaults(file, defaults);

		if (write) {
			config.writeToFile(file);
		}

		Options options = new Options();

		// set up terrain options
		Container image = config.getContainer("image");
		Container noise = config.getContainer("noise");
		Container circle = config.getContainer("circle");
		Container general = config.getContainer("general");

		options.width = image.getIntegerValue("width");
		options.height = image.getIntegerValue("height");

		options.noiseData = new NoiseData(noise.getIntegerValue("detail"), noise.getDoubleValue("amplitude"), noise.getDoubleValue("spreadX"), noise.getDoubleValue("spreadY"));

		if (general.getBooleanValue("fixedSeed")) {
			options.random = new Random(general.getIntegerValue("seed"));
		} else {
			options.random = new Random();
		}

		options.target = general.getDoubleValue("landTarget");

		options.displacementX = circle.getDoubleValue("displacementX");
		options.displacementY = circle.getDoubleValue("displacementY");
		options.circleRadius = circle.getDoubleValue("radius");
		options.circleStrength = circle.getDoubleValue("strength");

		// set up colours
		Container colour = config.getContainer("colours");

		MapColour.LAND = new MapColour(colour.getStringValue("land"));
		MapColour.EDGE = new MapColour(colour.getStringValue("border"));
		MapColour.OCEAN = new MapColour(colour.getStringValue("ocean"));
		MapColour.OCEAN_MARKER = new MapColour(colour.getStringValue("oceanDot"));

		return options;
	}

	public static double target;
}

class Options {
	int width, height, octaves;
	double circleStrength, circleRadius, displacementX, displacementY, target;
	NoiseData noiseData;
	Random random;
}