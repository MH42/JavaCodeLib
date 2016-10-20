package operations;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthias
 */

public class Filter {
	private List<String> excludedFileExtensions = new ArrayList<String>();
	
	public Filter() {};
	
	/**
	 * Creates a filter with a list of excluded file extensions.
	 * @param fileExtensionList The list of file extensions that have to be excluded.
	 */
	public Filter(List<String> fileExtensionList) {
		excludedFileExtensions.addAll(fileExtensionList);
	}
	
	/**
	 * Adds a file extension to the list of file extensions that have to be excluded.
	 * @param fileExtension The file extension that has to be added. 
	 */
	public void addFileExtension(String fileExtension) {
		excludedFileExtensions.add(fileExtension);
	}
	
	/**
	 * Adds a list of file extensions to the list of file extensions that have to be excluded.
	 * @param fileExtensionList The file extensions that have to be added.
	 */
	public void addFileExtensions(List<String> fileExtensionList) {
		excludedFileExtensions.addAll(fileExtensionList);
	}
	
	public boolean applies(Path file) {
		for(String excludedFileExtension : excludedFileExtensions) {
			if(file.toFile().getAbsolutePath().endsWith(excludedFileExtension))
				return true;
		}
		return false;
	}
}
