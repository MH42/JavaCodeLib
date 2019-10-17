package operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * @author Matthias
 * 10.08.2016
 */

public class Dir {
	private List<Path> successes = new ArrayList<Path>();
	private List<Path> failures = new ArrayList<Path>();
	private Filter filter;
	
	public Dir() {
		this.filter = new Filter();
	}
	/**
	 * Creates a dir-object with a filter that is applied to all operations that are offered by Dir unless it is overwritten by {@link #setNewExcludedExtensions(List)}.
	 * @param excludedExtenions The file extensions that have to be excluded.
	 */
	public Dir(List<String> excludedExtenions) {
		this.filter = new Filter(excludedExtenions);
	}
	
	
	/**
	 * Creates a new filter with the specified file extensions that have to be excluded.
	 * @param excludedExtenions The file extensions that have to be excluded.
	 */
	public void setNewExcludedExtensions(List<String> excludedExtenions) {
		this.filter = new Filter(excludedExtenions);
	}
	
	/**
	 * Provided for easy access, catching IOException. See {@link #copyFileTree(String, String)} for the functionality.
	 * @see #copyFileTree(String, String) 
	 */
	public boolean copy(String source, String target) {
		try {
			return copyFileTree(source, target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Provided for easy access, catching IOException. See {@link #deleteFileTree(String)} for the functionality.
	 * @see #deleteFileTree(String)
	 */
	public boolean delete(String target) {
		try {
			return deleteFileTree(target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Provided for easy access, catching IOException. See {@link #moveFileTree(String, String)} for the functionality.
	 * @see #moveFileTree(String, String)
	 */
	public boolean move(String source, String target) {
		try {
			return moveFileTree(source, target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Copies a directory source and all its files and directories into a target directory (unless the filter applies). 
	 * If the target directory does not exist, it will be created with all the possibly necessary parent directories.
	 * @param source the directory to copy
	 * @param target the target directory
	 * @see FileVisitor-API: {@link http://docs.oracle.com/javase/7/docs/api/java/nio/file/FileVisitor.html}
	 * @return true, if copying of the directory with all the containing directories and files was successful, false otherwise.
	 * @throws IOException 
	 */
	public boolean copyFileTree(String source, final String target) throws IOException {
		successes.clear();
		failures.clear();
		final Path sourcePath = Paths.get(source);
		final Path targetPath = Paths.get(target);
		try {
			if(!Files.exists(targetPath))
				Files.createDirectories(targetPath);
			Files.walkFileTree(sourcePath, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#preVisitDirectory(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
				 */
				@Override
				public FileVisitResult preVisitDirectory(Path dir,
						BasicFileAttributes attrs) throws IOException {
					String delta = (sourcePath.relativize(dir)).toString();
					Path currentTargetPath = Paths.get(target + File.separator + delta);
					if(!Files.exists(currentTargetPath)) {
						Files.copy(dir, currentTargetPath);
						successes.add(dir);
					}
					return FileVisitResult.CONTINUE	;
				}

				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
				 */
				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {
					if(!filter.applies(file)) {
						String delta = (sourcePath.relativize(file)).toString();
						Path currentTargetPath = Paths.get(target + File.separator + delta);
						Files.copy(file, currentTargetPath);
						successes.add(file);
					}
					return FileVisitResult.CONTINUE;
				}
				
				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#visitFileFailed(java.lang.Object, java.io.IOException)
				 */
				@Override
				public FileVisitResult visitFileFailed(Path file,
						IOException exc) throws IOException {
					failures.add(file);
					// return super.visitFileFailed(file, exc);
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw e;
		}
		finally {
			if(!successes.isEmpty()) {
				System.out.println("Succeeded to copy the following paths into the target path " + target + ":");
				for(Path p : successes)
					System.out.println(p.toString());
			}
			if(!failures.isEmpty()) {
				System.out.println("Failed to copy the following paths into the target path " + target + ":");
				for(Path p : failures)
					System.out.println(p.toString());
			}
		}
		if(failures.isEmpty())
			return true;
		else
			return false;
	}
	
	
	/**
	 * Deletes a directory and all the possibly containing files and directories. 
	 * @param target the directory to delete
	 * @return true, if the deletion of the directory and all the possibly containing files and directories was successful, false otherwise.
	 * @throws IOException 
	 */
	public boolean deleteFileTree(String target) throws IOException {
		successes.clear();
		failures.clear();
		final Path targetPath = Paths.get(target);
		try {
			if(!Files.exists(targetPath))
				return false;
			Files.walkFileTree(targetPath, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {

				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
				 */
				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					successes.add(file);
					return FileVisitResult.CONTINUE;
				}

				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#postVisitDirectory(java.lang.Object, java.io.IOException)
				 */
				@Override
				public FileVisitResult postVisitDirectory(Path dir,
						IOException exc) throws IOException {
					Files.delete(dir);
					successes.add(dir);
					return FileVisitResult.CONTINUE;
				}

				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#visitFileFailed(java.lang.Object, java.io.IOException)
				 */
				@Override
				public FileVisitResult visitFileFailed(Path file,
						IOException exc) throws IOException {
					failures.add(file);
//					return super.visitFileFailed(file, exc);
					return FileVisitResult.CONTINUE;
				}
				
			});
		} catch (IOException e) {
			throw e;
		}
		finally {
			if(!successes.isEmpty()) {
				System.out.println("Succeeded	 to delete the following paths:");
				for(Path p : successes)
					System.out.println(p.toString());
			}
			if(!failures.isEmpty()) {
				System.out.println("Failed to delete the following paths:");
				for(Path p : failures)
					System.out.println(p.toString());
			}
		}
		if(failures.isEmpty())
			return true;
		else
			return false;
	}
	
	/**
	 * Moves a directory source and all its files and directories into a target directory (unless the filter applies). 
	 * If the target directory does not exist, it will be created with all the possibly necessary parent directories.
	 * @param source the directory to move
	 * @param target the target directory
	 * @see Inspired by the copy-method (see above which is based on the FileVisitor-API: {@link http://docs.oracle.com/javase/7/docs/api/java/nio/file/FileVisitor.html})
	 * @return true, if moving of the directory with all the containing directories and files was successful, false otherwise.
	 * @throws IOException 
	 */
	public boolean moveFileTree(String source, final String target) throws IOException {
		successes.clear();
		failures.clear();
		final Path sourcePath = Paths.get(source);
		final Path targetPath = Paths.get(target);
		try {
			if(!Files.exists(targetPath))
				Files.createDirectories(targetPath);
			Files.walkFileTree(sourcePath, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#preVisitDirectory(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
				 */
				@Override
				public FileVisitResult preVisitDirectory(Path dir,
						BasicFileAttributes attrs) throws IOException {
					String delta = (sourcePath.relativize(dir)).toString();
					Path currentTargetPath = Paths.get(target + File.separator + delta);
					if(!filter.applies(dir) && !Files.exists(currentTargetPath)) {
						Files.createDirectories(currentTargetPath);
						successes.add(dir);
					}
					return FileVisitResult.CONTINUE	;
				}

				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
				 */
				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {
					if(!filter.applies(file)) {
						String delta = (sourcePath.relativize(file)).toString();
						Path currentTargetPath = Paths.get(target + File.separator + delta);
						Files.move(file, currentTargetPath);
						successes.add(file);
					}
//					else 
//						Files.delete(file);
					return FileVisitResult.CONTINUE;
				}
				
				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#postVisitDirectory(java.lang.Object, java.io.IOException)
				 */
				@Override
				public FileVisitResult postVisitDirectory(Path dir,
						IOException exc) throws IOException {
					Files.delete(dir);
					successes.add(dir);
					return FileVisitResult.CONTINUE;
				}

				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#visitFileFailed(java.lang.Object, java.io.IOException)
				 */
				@Override
				public FileVisitResult visitFileFailed(Path file,
						IOException exc) throws IOException {
					failures.add(file);
					// return super.visitFileFailed(file, exc);
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw e;
		}
		finally {
			if(!successes.isEmpty()) {
				System.out.println("Succeeded to move the following paths into the target path " + target + ":");
				for(Path p : successes)
					System.out.println(p.toString());
			}
			if(!failures.isEmpty()) {
				System.out.println("Failed to move the following paths into the target path " + target + ":");
				for(Path p : failures)
					System.out.println(p.toString());
			}
		}
		if(failures.isEmpty())
			return true;
		else
			return false;
	}
	
	/**
	 * Searches for a search string in the given path and optionally in all its subdirectories. Reports on the number of matches.
	 * @param path The path where the search starts
	 * @param searchString The string to search for
	 * @param ignoreCase <tt>true</tt> if the search is case sensitive and considers exact matches only; <tt>false</tt> otherwise
	 * @param skipSubDirectories <tt>true</tt> if the search must consider only the given path; <tt>false</tt> otherwise
	 * @return The number of matches
	 */
	public int searchFileContent(String path, String searchString, boolean ignoreCase, boolean skipSubDirectories) {
		successes.clear();
		failures.clear();
		final Path sourcePath = Paths.get(path);
		try {
			Files.walkFileTree(sourcePath, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
				
				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
				 */
				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) {
					FileContent fileContent = new FileContent();
					if(!file.toFile().isDirectory() && !filter.applies(file)) {
						if(fileContent.search(file, searchString, ignoreCase)) {
							successes.add(file);
						}
					}
					if(!skipSubDirectories)
						return FileVisitResult.CONTINUE;
					else 
						return FileVisitResult.SKIP_SUBTREE;
				}
				
				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#visitFileFailed(java.lang.Object, java.io.IOException)
				 */
				@Override
				public FileVisitResult visitFileFailed(Path file,
						IOException exc) {
					failures.add(file);
					// return super.visitFileFailed(file, exc);
					if(!skipSubDirectories)
						return FileVisitResult.CONTINUE;
					else 
						return FileVisitResult.SKIP_SUBTREE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(!successes.isEmpty()) {
				System.out.println("Succeeded to search in the following paths:");
				for(Path p : successes)
					System.out.println(p.toString());
				System.out.println("Matches in total: " + successes.size());
			} 
			if(!failures.isEmpty()) {
				System.out.println("Failed to search in the following paths:");
				for(Path p : failures)
					System.out.println(p.toString());
			} 
		}
		return successes.size();
	}
	
	/**
	 * Replaces a search string in the given path by another string. Optionally considers all its subdirectories.
	 * @param path The path where the search starts
	 * @param searchString The string to search for
	 * @param replacementString The replacement string
	 * @param ignoreCase <tt>true</tt> if the search is case sensitive and considers exact matches only; <tt>false</tt> otherwise
	 * @param skipSubDirectories <tt>true</tt> if the search must consider only the given path; <tt>false</tt> otherwise
	 * @return The number of replacements
	 */
	public int replaceFileContent(String path, String searchString, String replacementString, boolean ignoreCase, boolean skipSubDirectories) {
		successes.clear();
		failures.clear();
		final Path sourcePath = Paths.get(path);
		try {
			Files.walkFileTree(sourcePath, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {

				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
				 */
				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) {
					FileContent fileContent = new FileContent();
					if(!file.toFile().isDirectory() && !filter.applies(file)) {
						fileContent.replace(file, searchString, replacementString, ignoreCase);
						successes.add(file);
					}
					if(!skipSubDirectories)
						return FileVisitResult.CONTINUE;
					else 
						return FileVisitResult.SKIP_SUBTREE;
				}
				
				/* (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#visitFileFailed(java.lang.Object, java.io.IOException)
				 */
				@Override
				public FileVisitResult visitFileFailed(Path file,
						IOException exc) {
					failures.add(file);
					// return super.visitFileFailed(file, exc);
					if(!skipSubDirectories)
						return FileVisitResult.CONTINUE;
					else 
						return FileVisitResult.SKIP_SUBTREE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(!successes.isEmpty()) {
				System.out.println("Succeeded to replace " + searchString + " by " + replacementString + " in the following paths:");
				for(Path p : successes)
					System.out.println(p.toString());
				System.out.println("Replacements in total: " + successes.size());
			} 
			if(!failures.isEmpty()) {
				System.out.println("Failed to replace " + searchString + " by " + replacementString + " in the following paths:");
				for(Path p : failures)
					System.out.println(p.toString());
			} 
		}
		return successes.size();
	}
	
	
	/**
	 * Reads a file with its content.
	 * @param path The path to the file
	 * @return The file content as {@link String}
	 * @throws IOException In case of errors during reading the given file.
	 */
	public String readFileIntoString(String path) {
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));) {
			StringBuffer fileContent = new StringBuffer();
			String line = "";
			while((line = bufferedReader.readLine()) != null) {
				fileContent.append(line + "\n");
			}
			return fileContent.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * Reads a file with its content.
	 * @param path The path to the file
	 * @return The file content as {@link List}
	 * @throws IOException In case of errors during reading the given file.
	 */
	public List<String> readFileIntoList(String path) {
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));) {
			List<String> fileContentList = new ArrayList<>();
			String line = "";
			while((line = bufferedReader.readLine()) != null) {
				fileContentList.add(line);
			}
			return fileContentList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		try {
			// Create dummy dirs and dummy files with text:
			String source = "path-to-source-dir";
			// Copy dirs:
			String target = "path-to-target-dir";
			Dir dir = new Dir();
			dir.copy(source, target);
			// Move dirs:
			String targetMoved = "path-to-target-dir";
			dir.move(target, targetMoved);
			// Delete dirs:
			dir.delete(source);
			// Copy dirs:
//			String target = "path-to-target-dir";
			dir.copy(targetMoved, source);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		String source2 = "path-to-source-dir";
		Dir dir2 = new Dir(Arrays.asList(".txt"));
		int matches = dir2.searchFileContent(source2, "lebensraum", true, false);
		System.out.println("#Matches? " + matches);
		int replacements = dir2.replaceFileContent(source2, "lebensraum", "world", true, false);
		System.out.println("#Replacements? " + replacements);
		
		dir.copy(source2, (source2 + "abc"));
		
		List<String> fileList = dir2.readFileIntoList("path-to-source-dir");
		for(String f: fileList) {
			dir2.copy(f, (f + "def"));
		}
	}

}
