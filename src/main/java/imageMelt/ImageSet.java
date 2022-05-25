package imageMelt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * this class stores a set of imageData that
 * 
 * @author sijmen_v_b
 */
public class ImageSet {
	private LinkedList<ImageData> images = new LinkedList<ImageData>();
	private HashSet<String> tags = new HashSet<String>();
	private String savePath;

	public ImageSet(String savePath) {
		super();
		this.savePath = savePath;
	}

	public void generateImage() {
		try {
			BufferedImage Mixedimage = new BufferedImage(4505, 4505, BufferedImage.TYPE_INT_ARGB);// the image that will
																									// be saved
			Graphics2D buffer = Mixedimage.createGraphics();// the buffer will be used to add all the images on to.

			for (ImageData data : images) {// loop over the depths.
				buffer.drawImage(data.getImage(), 0, 0, null);// add it to the buffer
			}
			buffer.dispose();

			ImageIO.write(Mixedimage, "PNG", new File(savePath));// save the image
			generateReport();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateReport() {
		JSONObject obj = new JSONObject();
		JSONArray tagsList = new JSONArray(tags);
		JSONArray layers = new JSONArray();
		
		for (ImageData data : images) {
			JSONObject layer = new JSONObject();
			layer.put("filePath", data.getImagePath());
			layer.put("weight", data.getWeight());
			layer.put("depth", data.getDepth());
			layers.put(layer);
		}
		
		
		
		obj.put("tags",tagsList);
		obj.put("layers",layers);
		
		saveAsfile(obj.toString(4),savePath.replace(".png", ".json"));
	}

	private void saveAsfile(String s, String filepath) {
		try {
			File file = new File(filepath);
			// creates the file
			file.createNewFile();
			// creates a FileWriter Object
			FileWriter writer = new FileWriter(file);
			// Writes the content to the file
			writer.write(s);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println("error writing log file: " + filepath);
			e.printStackTrace();
		}
	}

	/**
	 * adds the tags and the image to the image set
	 * 
	 * @param data
	 */
	public void addImage(ImageData data) {
		tags.addAll(data.getTags());// add the tags to the image set
		images.add(data);
	}

	public HashSet<String> getTags() {
		return tags;
	}

}
