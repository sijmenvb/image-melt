package imageMelt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONException;
import org.json.JSONObject;
import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) {
		System.out.println("hello world");

		ImageGenerator gen = new ImageGenerator(".\\images");
		gen.PrintAllPaths();
		gen.generateImage(".\\mixedImage.png");
		System.out.println("Done! -- tool provided by sijmen_v_b");
	}
}
