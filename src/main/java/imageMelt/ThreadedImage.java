package imageMelt;
/**class to facilitate multithreaded export.
 * 
 * @author sijmen_v_b
 */
public class ThreadedImage implements Runnable {
	
	private ImageSet image;
	private String message;
	

	/**
	 * 
	 * @param image to be generated
	 * @param message to be displayed for progress updates.
	 */
	public ThreadedImage(ImageSet image, String message) {
		super();
		this.image = image;
		this.message = message;
	}


	/**generate the image.
	 */
	@Override
	public void run() {
		System.out.println(message);
		image.generateImage();
	}
	
}
