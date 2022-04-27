package imageMelt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import javax.imageio.ImageIO;

public class ImageGenerator {
	
	ArrayList<Float> depths;// keeps a list of all depths (sorted).
	ArrayList<ArrayList<MetaData>> depthsData = new ArrayList<ArrayList<MetaData>>();//all the metadata grouped by their depth(sorted).
	Random random = new Random(System.currentTimeMillis());
	
	public ImageGenerator(String path) {
		ArrayList<MetaData> pictureData = new ArrayList<MetaData>();		
		HashSet<Float> depthsSet = new HashSet<Float>();
		loadPictureData(path,depthsSet,pictureData);//load all the 
		depths = new ArrayList<Float>(depthsSet);
		Collections.sort(depths);
		
		//make an empty array for each depth
		for (int i = 0; i < depths.size(); i++) {
			depthsData.add(i, new ArrayList<MetaData>());
		}
		//group the data to their corresponding depths. 
		for (MetaData data : pictureData) {
			depthsData.get(depths.indexOf(data.getDepth())).add(data);
		}
		
	}

	private void loadPictureData(String path, HashSet<Float> depthsSet, ArrayList<MetaData> pictureData) {
		File f = new File(path);

		if (!f.isDirectory()) {
			System.err.println("ERROR File not a Directory: " + f.toString());
			return;
		}
		loadPictureData1(f,depthsSet,pictureData);
	}

	private void loadPictureData1(File folder, HashSet<Float> depthsSet,ArrayList<MetaData> pictureData) {
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
					depthsSet.add(data.getDepth());// update the list of all possible depths (HashSet ensures uniqueness)

				}
				// if it is not a json ignore it.
			} else {// if it is a directory convert those files as well
				loadPictureData1(file,depthsSet,pictureData);
			}
		}
	}

	/**
	 * takes a file path and returns it extension without the .
	 */
	public String getFileExtension(String filename) {
		return filename.substring(filename.lastIndexOf('.') + 1);
	}
	
	public void generateImages(String folderPath, String name, int ammount) {
		File f = new File(folderPath);
		f.mkdir();
		
		// activate opengl
		System.setProperty("sun.java2d.opengl", "True");
		
		for (int i = 0; i < ammount; i++) {
			System.out.println("generating " + (i+1) + "/" + ammount);
			generateImage(String.format("%s\\%s%04d.png",folderPath, name, i));
		}
	}

	public void generateImage(String savePath) {
		if(depths.isEmpty()) {
			System.err.println("no images provided! skipping.");
			return;//if no images where provided don't do anything.
		}
		try {
			BufferedImage Mixedimage = new BufferedImage(4505, 4505, BufferedImage.TYPE_INT_ARGB);//the image that will be saved
			Graphics2D buffer = Mixedimage.createGraphics();//the buffer will be used to add all the images on to.
			
			for (ArrayList<MetaData> depthList : depthsData) {//loop over the depths.
				MetaData data = depthList.get(random.nextInt(depthList.size()));//get random picture from list TODO: add weights
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
		for (ArrayList<MetaData> depthList : depthsData) {
			for (MetaData data : depthList) {
				System.out.println(data.getImage().toString());
			}
		}
	}
}
