package fileComparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class FileComparator {
	// Calculates and returns MD5 Hash value
	public static String getMD5Hash(String dirPath, String fileName) throws NoSuchAlgorithmException, IOException {

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(Files.readAllBytes(Paths.get(dirPath + "/" + fileName)));
		byte[] digest = md.digest();
		StringBuilder result = new StringBuilder();
		for (byte b : digest) {
			result.append(String.format("%02X", b));
		}
		return result.toString();
	}

	// Returns Set of files in current directory
	public static Set<String> listFilesUsingFilesList(String dirPath) throws IOException {
		Set<String> fileNamesSet = Files.list(Paths.get(dirPath)).filter(file -> !Files.isDirectory(file))
				.map(Path::getFileName).map(Path::toString).collect(Collectors.toSet());
		return fileNamesSet;
	}

	// Saves MD5 Hash values into snapshot.txt file
	public static void writeToFile(HashMap<String, String> md5HashMap, String stringPath) {
		Path givenDirectoryPath = Paths.get(stringPath);
		if (Files.exists(givenDirectoryPath)) {
			String directoryNameString = givenDirectoryPath.getFileName().toString();
			Path savePath = Paths.get("");
			String savePathString = savePath.toAbsolutePath().toString();
			Path path = Paths.get(savePathString + "\\" + directoryNameString + "-snapshot.txt");
			try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
				for (Entry<String, String> entry : md5HashMap.entrySet()) {
					writer.write(entry.getKey() + "=" + entry.getValue());
					writer.newLine();
				}
				writer.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		else {
			System.out.println("There is no directory at given path");
		}

	}

	// Reads MD5 Hash values from snapshot.txt
	public static HashMap<String, String> readFromFile(String stringPath) throws IOException {
		HashMap<String, String> readMD5HashMap = new HashMap<String, String>();
		Path givenDirectoryPath = Paths.get(stringPath);
		if (Files.exists(givenDirectoryPath)) {
			String directoryNameString = givenDirectoryPath.getFileName().toString();
			Path savePath = Paths.get("");
			String savePathString = savePath.toAbsolutePath().toString();
			Path path = Paths.get(savePathString + "\\" + directoryNameString + "-snapshot.txt");
			try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
				String currentLine = null;
				while ((currentLine = reader.readLine()) != null) { // while there is content on the current line
					String[] currentLineSplited = currentLine.split("="); // Splits the current line
					readMD5HashMap.put(currentLineSplited[0], currentLineSplited[1]);
				}
			} catch (IOException ex) {
				ex.printStackTrace(); // handle an exception here
			}
			return readMD5HashMap;
		} else {
			System.out.println("There is no directory at given path");
		}
		return readMD5HashMap;

	}

	// Compares files MD5 Has values
	public static String compareMD5(HashMap<String, String> currentMD5HashMap, HashMap<String, String> savedMD5HashMap) {
		StringBuilder stringBuilder = new StringBuilder();
		if (currentMD5HashMap.size() > savedMD5HashMap.size()) {
			stringBuilder.append("A few files were added since last snapshot! \n\n");
			System.out.println("A few files were added since last snapshot! \n");
		} else if (currentMD5HashMap.size() < savedMD5HashMap.size()) {
			stringBuilder.append("A Few files were deleted since last snapshot \n\n");
			System.out.println("A Few files were deleted since last snapshot \n");
		}
		else {
			stringBuilder.append("There are the same amount of files since last snapshot! \n\n");
			System.out.println("There are the same amount of files since last snapshot! \n");
		}

		for (Entry<String, String> entry : currentMD5HashMap.entrySet()) {
			String currentValue = entry.getValue();
			String currentKey = entry.getKey();
			if (!currentValue.equals(savedMD5HashMap.get(currentKey))) {
				stringBuilder.append(currentKey + " was modified!\n");
				System.out.println(currentKey + " was modified!");
			} else {
				stringBuilder.append(currentKey + " was not modified!\n");
				System.out.println(currentKey + " was not modified!");
			}
		}
		return stringBuilder.toString();
	}

}
