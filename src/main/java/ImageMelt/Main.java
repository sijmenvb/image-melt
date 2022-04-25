package ImageMelt;

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
		
		try {
			BufferedImage image1 = ImageIO.read(new File(".\\images\\H1 Body.png"));
			BufferedImage image2 = ImageIO.read(new File(".\\images\\H1 Ear.png"));
			
			//BufferedImage
			Graphics2D buffer = image1.createGraphics();
			buffer.drawImage(image2, 0, 0, null);
			buffer.dispose();
			
			ImageIO.write(image1, "PNG", new File(".\\mixedImage.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		File f = new File(".\\images\\H1 Body.json");
		try {
			JSONObject o = new JSONObject(Files.readString(f.toPath()));
			System.out.println(o.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
