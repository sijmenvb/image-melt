package imageMelt;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageData {
	private float depth;
	private File imagePath;// path to corresponding image file
	private float weight = 1.0f;
	private LinkedList<String> tags = new LinkedList<String>();
	private LinkedList<String> onlyWithTag = new LinkedList<String>();
	private LinkedList<String> exeptWithTag = new LinkedList<String>();
	private Image image;

	public ImageData(File jsonFile) throws Exception {
		try {
			JSONObject o = new JSONObject(new String (Files.readAllBytes(jsonFile.toPath())));
			
			//load the image (path)
			this.imagePath = new File(jsonFile.getPath().replace(".json", ".png"));// make sure the image is in the same folder and a	TODO: this rules out .json in the file name so make more elegant														// .png
			//check if the image exists.
			if (!imagePath.exists()) {
				System.err.println("!WARNING! no accompanying image to " + jsonFile.toString());
				throw new Exception("no accompanying image");
			}
			
			
			//load the depth
			this.depth = o.getFloat("depth");
			
			//load the weight if it exists
			if(o.has("weight")) {
				this.weight = o.getFloat("weight");
			}
			
			//load the tags array if it exists
			if(o.has("tags")) {
				JSONArray tags = o.getJSONArray("tags");
				for (Object object : tags) {
					if (object instanceof String) {
						this.tags.add((String)object);
					}
				}
			}
			
			//load the onlyWithTag array if it exists
			if(o.has("onlyWithTag")) {
				JSONArray onlyWithTag = o.getJSONArray("onlyWithTag");
				for (Object object : onlyWithTag) {
					if (object instanceof String) {
						this.onlyWithTag.add((String)object);
					}
				}
			}
			
			//load the exeptWithTag array if it exists
			if(o.has("exeptWithTag")) {
				JSONArray exeptWithTag = o.getJSONArray("exeptWithTag");
				for (Object object : exeptWithTag) {
					if (object instanceof String) {
						this.exeptWithTag.add((String)object);
					}
				}
			}
			
		} catch (JSONException e) {//TODO: make errors useful
			System.err.println("!!ISSUE LOADING :" + jsonFile.toString());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** get's the image. if it is not loaded yet it will load it in.
	 */
	public Image getImage() {
		if (image == null) {// if image is not loaded yet
			try {
				image = ImageIO.read(imagePath);//load the image.
			} catch (IOException e) {
				System.err.println("ERROR LOADING: " + imagePath);
				e.printStackTrace();
			}
		}
		return image;
	}
	
	

	public float getWeight() {
		return weight;
	}

	public LinkedList<String> getTags() {
		return tags;
	}

	public LinkedList<String> getOnlyWithTag() {
		return onlyWithTag;
	}

	public LinkedList<String> getExeptWithTag() {
		return exeptWithTag;
	}

	public File getImagePath() {
		return imagePath;
	}

	public float getDepth() {
		return depth;
	}	
	

}
