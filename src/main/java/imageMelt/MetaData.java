package imageMelt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MetaData {
	float depth;
	File image;// path to corresponding image file
	float weight = 1.0f;
	LinkedList<String> tags = new LinkedList<String>();
	LinkedList<String> onlyWithTag = new LinkedList<String>();
	LinkedList<String> exeptWithTag = new LinkedList<String>();

	public MetaData(File jsonFile) throws Exception {
		try {
			JSONObject o = new JSONObject(new String (Files.readAllBytes(jsonFile.toPath())));
			
			//load the image (path)
			this.image = new File(jsonFile.getPath().replace(".json", ".png"));// make sure the image is in the same folder and a	TODO: this rules out .json in the file name so make more elegant														// .png
			//check if the image exists.
			if (!image.exists()) {
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

	public float getDepth() {
		return depth;
	}

	public File getImage() {
		return image;
	}
	
	

}
