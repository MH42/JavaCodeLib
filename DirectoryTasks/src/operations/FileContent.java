package operations;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileContent {
	public boolean search(Path filePath, String searchString, boolean ignoreCase) {
		Charset charset = StandardCharsets.UTF_8;
		try {
			String content = new String(Files.readAllBytes(filePath), charset);
			if(ignoreCase) {
				content = content.toLowerCase();
				searchString = searchString.toLowerCase();
			}
			if (content.contains(searchString)) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean replace(Path filePath, String searchString, String replacementString, boolean ignoreCase) {
		Charset charset = StandardCharsets.UTF_8;
		try {
			String content = new String(Files.readAllBytes(filePath), charset);
			if(ignoreCase) {
				content = content.toLowerCase();
				searchString = searchString.toLowerCase();
			}
			if (content.contains(searchString)) {
				content = content.replaceAll(searchString, replacementString);
				Files.write(filePath, content.getBytes(charset));
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		try {
			// Create dummy dirs and dummy files with text:
			String source = "path-to-source-dir";
			boolean found = new FileContent().search(Paths.get(source), "lebensraum", true);
			System.out.println("Found? " + found);
			boolean replaced = new FileContent().replace(Paths.get(source), "lebensraum", "world", true);
			System.out.println("Replaced? " + replaced);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
