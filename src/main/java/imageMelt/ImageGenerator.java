package imageMelt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.imageio.ImageIO;

public class ImageGenerator {
	ArrayList<MetaData> pictureData = new ArrayList<MetaData>();
	HashSet<Float> depths = new HashSet<Float>();// keeps a list of all depths.

	public ImageGenerator(String path) {
		loadPictureData(path);
	}

	private void loadPictureData(String path) {
		File f = new File(path);

		if (!f.isDirectory()) {
			System.err.println("ERROR File not a Directory: " + f.toString());
			return;
		}

		loadPictureData1(f);
	}

	private void loadPictureData1(File folder) {
		File[] listOfFiles = folder.listFiles();// get all subfiles/folders
		for (File file : listOfFiles) {
			if (file.isFile()) {// if it is a file convert it
				if (getFileExtension(file.getPath()).equals("json")) {// check if the file is a .json
					MetaData data;

					try {
						data = new MetaData(file);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						continue;
					}

					pictureData.add(data);
					depths.add(data.getDepth());// update the list of all possible depths (HashSet ensures uniqueness)

				}
				// if it is not a json ignore it.
			} else {// if it is a directory convert those files as well
				loadPictureData1(file);
			}
		}
	}

	/**
	 * takes a file path and returns it extension without the .
	 */
	public String getFileExtension(String filename) {
		return filename.substring(filename.lastIndexOf('.') + 1);
	}

	public void generateImage(String savePath) {
		try {
			BufferedImage imageSize = ImageIO.read(new File(".\\images\\H1 Body.png"));//TODO infer biggest image size
			BufferedImage Mixedimage = new BufferedImage(imageSize.getWidth(), imageSize.getHeight(),
					BufferedImage.TYPE_INT_ARGB);//the image that will be saved
			Graphics2D buffer = Mixedimage.createGraphics();//the buffer will be used to add all the images on to.
			
			for (MetaData data : pictureData) {//loop over all the images TODO: make this loop over the depths instead.
				BufferedImage image = ImageIO.read(data.getImage());//load the image
				buffer.drawImage(image, 0, 0, null);//add it to the buffer
			}
			buffer.dispose();

			ImageIO.write(Mixedimage, "PNG", new File(savePath));//save the image
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void PrintAllPaths() {
		for (MetaData data : pictureData) {
			System.out.println(data.getImage().toString());
		}
	}
}
