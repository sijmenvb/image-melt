package imageMelt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONException;
import org.json.JSONObject;

public class MetaData {
	float depth;
	File image;// path to corresponding image file

	public MetaData(File jsonFile) throws Exception {
		try {
			JSONObject o = new JSONObject(Files.readString(jsonFile.toPath()));
			
			//load the image (path)
			this.image = new File(jsonFile.getPath().replace(".json", ".png"));// make sure the image is in the same folder and a	TODO: this rules out .json in the file name so make more elegant														// .png
			//check if the image exists.
			if (!image.exists()) {
				System.err.println("!WARNING! no accompanying image to " + jsonFile.toString());
				throw new Exception("no accompanying image");
			}
			
			
			//load the depth
			this.depth = o.getFloat("depth");
			
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
