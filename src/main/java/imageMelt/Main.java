package imageMelt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Main {

	public static void main(String[] args) {
		System.out.println("running..");

		LoadInputFolder();
		createFiles();
		ImageGenerator gen = new ImageGenerator(".\\images");
		gen.PrintAllPaths();
		gen.generateImages(".\\Exported Images","mixedImage",10);
		System.out.println("Done! -- tool provided by sijmen_v_b");
	}

	private static File LoadInputFolder() {
		File f = new File(".\\images");
		f.mkdir();
		return f;
	}

	private static void createFiles() {
		// check if the file already exist

		loadFileFromResources("README.txt");
		loadFileFromResources("template.json");
		loadFileFromResources("run.bat");
	}

	private static void loadFileFromResources(String name) {
		File tmpDir = new File(".\\" + name);
		if (!tmpDir.exists()) {
			try {
				InputStream source;
				source = Main.class.getClassLoader().getResourceAsStream(name);
				File target = new File(".\\" + name);

				Files.copy(source, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
