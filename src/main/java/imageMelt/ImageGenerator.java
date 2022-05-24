package imageMelt;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ImageGenerator {

	private ArrayList<Float> depths;// keeps a list of all depths (sorted).
	private ArrayList<ArrayList<ImageData>> depthsData = new ArrayList<ArrayList<ImageData>>();// all the metadata
																								// grouped by their
																								// depth(sorted).
	private Random random = new Random(System.currentTimeMillis());
	private LinkedList<ImageSet> imageSets = new LinkedList<ImageSet>();

	public ImageGenerator(String path) {
		ArrayList<ImageData> pictureData = new ArrayList<ImageData>();
		HashSet<Float> depthsSet = new HashSet<Float>();
		loadPictureData(path, depthsSet, pictureData);// load all the
		depths = new ArrayList<Float>(depthsSet);
		Collections.sort(depths);

		// make an empty array for each depth
		for (int i = 0; i < depths.size(); i++) {
			depthsData.add(i, new ArrayList<ImageData>());
		}
		// group the data to their corresponding depths.
		for (ImageData data : pictureData) {
			depthsData.get(depths.indexOf(data.getDepth())).add(data);
		}

	}

	private void loadPictureData(String path, HashSet<Float> depthsSet, ArrayList<ImageData> pictureData) {
		File f = new File(path);

		if (!f.isDirectory()) {
			System.err.println("ERROR File not a Directory: " + f.toString());
			return;
		}
		loadPictureData1(f, depthsSet, pictureData);
	}

	private void loadPictureData1(File folder, HashSet<Float> depthsSet, ArrayList<ImageData> pictureData) {
		File[] listOfFiles = folder.listFiles();// get all subfiles/folders
		for (File file : listOfFiles) {
			if (file.isFile()) {// if it is a file convert it
				if (getFileExtension(file.getPath()).equals("json")) {// check if the file is a .json
					ImageData data;

					try {
						data = new ImageData(file);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						continue;
					}

					pictureData.add(data);
					depthsSet.add(data.getDepth());// update the list of all possible depths (HashSet ensures
													// uniqueness)

				}
				// if it is not a json ignore it.
			} else {// if it is a directory convert those files as well
				loadPictureData1(file, depthsSet, pictureData);
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
		//make output folder
		File f = new File(folderPath);
		f.mkdir();
		
		// activate opengl
		System.setProperty("sun.java2d.opengl", "True");
		generateImageSets(folderPath, name, ammount);
		
		//make a thread pool
		ExecutorService pool = Executors.newFixedThreadPool(16);//TODO: make no. threads configurable
		
		//add all the jobs to the thread pool.
		int i = 0;
		for (ImageSet set : imageSets) {
			pool.execute(new ThreadedImage(set,"generating " + (i + 1) + "/" + ammount));
			i++;
		}
		pool.shutdown();
		//wait for termination.
	    try {
	        if (!pool.awaitTermination(10L, TimeUnit.HOURS)) { //TODO: make timeout configurable
	        	pool.shutdownNow();
	        }
	    } catch (InterruptedException ex) {
	    	pool.shutdownNow();
	        Thread.currentThread().interrupt();
	    }
	}
	
	

	public void generateImageSets(String folderPath, String name, int ammount) {
		for (int i = 0; i < ammount; i++) {
			System.out.println("composing " + (i + 1) + "/" + ammount);
			generateImageSet(String.format("%s\\%s%04d.png", folderPath, name, i));
		}
	}

	public void generateImageSet(String savePath) {
		if (depths.isEmpty()) {
			System.err.println("no images provided! skipping.");
			return;// if no images where provided don't do anything.
		}

		ImageSet set = new ImageSet(savePath);

		for (ArrayList<ImageData> fullDepthList : depthsData) {// loop over the depths.
			ArrayList<ImageData> depthList = filterImageData(fullDepthList, set.getTags());//filter images.
			
			if (depthList.size() <= 0) {//TODO: regenerate image up to n times if it filters out a section.
				System.out.println("warning filtered out all images at depth: " + fullDepthList.get(0).getDepth());
				continue;
			}
			
			ImageData data = depthList.get(random.nextInt(depthList.size()));// get random picture from list TODO: add weights
 
			set.addImage(data);//add the image AND the tags to the imageSet.
		}
		imageSets.add(set);
	}
	
	/**
	 * looks at all the ImageData and filters out using the onlyWithTag and exeptWithTag lists using the provided list of tags.
	 */
	private ArrayList<ImageData> filterImageData(ArrayList<ImageData> list, HashSet<String> tags){
		ArrayList<ImageData> filteredList = new ArrayList<ImageData>();
		for (ImageData data : list) {
			if (!containsAll(data.getOnlyWithTag(),tags)) {//if not all of the "onlyWithTags" exist don't add the image.
				continue;
			}
			if (containsAny(data.getExeptWithTag(),tags)) {//if some of the "exeptWithTags" exist don't add the image.
				continue;
			}
			filteredList.add(data);
		}
		return filteredList;
	}
	
	/**
	 * checks if all the items in list are in the tags
	 */
	private boolean containsAll(LinkedList<String> list ,HashSet<String> tags) {
		for (String string : list) {
			if (!tags.contains(string)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * checks if any of the items in list are in the tags.
	 */
	private boolean containsAny(LinkedList<String> list ,HashSet<String> tags) {
		for (String string : list) {
			if (tags.contains(string)) {
				return true;
			}
		}
		return false;
	}
	
	
	

	public void PrintAllPaths() {
		for (ArrayList<ImageData> depthList : depthsData) {
			for (ImageData data : depthList) {
				System.out.println(data.getImagePath().toString());
			}
		}
	}
}
