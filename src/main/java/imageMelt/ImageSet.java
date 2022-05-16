package imageMelt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import javax.imageio.ImageIO;

/**this class stores a set of imageData that 
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
			BufferedImage Mixedimage = new BufferedImage(4505, 4505, BufferedImage.TYPE_INT_ARGB);//the image that will be saved
			Graphics2D buffer = Mixedimage.createGraphics();//the buffer will be used to add all the images on to.
			
			for (ImageData data : images) {//loop over the depths.
				buffer.drawImage(data.getImage(), 0, 0, null);//add it to the buffer
			}
			buffer.dispose();

			ImageIO.write(Mixedimage, "PNG", new File(savePath));//save the image
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**adds the tags and the image to the image set
	 * 
	 * @param data
	 */
	public void addImage(ImageData data) {
		tags.addAll(data.getTags());//add the tags to the image set
		images.add(data);
	}



	public HashSet<String> getTags() {
		return tags;
	}

}
