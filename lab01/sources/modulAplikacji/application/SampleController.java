package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Set;



public class SampleController {
	@FXML
	public TextField dirPathTxtField;

	@FXML
	public Button saveSnapshotBtn;

	@FXML
	public Button checkFilesBtn;
	
    @FXML
    public TextArea filesTextArea;
    

	
	public void saveSnapshot() throws IOException, NoSuchAlgorithmException {
		if (!dirPathTxtField.getText().equals("")) {
			String dirPath = dirPathTxtField.getText();
			HashMap<String, String> md5HashMap = new HashMap<String, String>();

			Set<String> fileNamesSet = fileComparator.FileComparator.listFilesUsingFilesList(dirPath);
			for (String fileName : fileNamesSet) {
				md5HashMap.put(fileName, fileComparator.FileComparator.getMD5Hash(dirPath, fileName));
			}
			fileComparator.FileComparator.writeToFile(md5HashMap, dirPath);
		} else {
			System.out.println("Nie podano sciezki do folderu");
		}
	}
	public void checkFiles() throws IOException, NoSuchAlgorithmException {
		if (!dirPathTxtField.getText().equals("")) {
			String dirPath = dirPathTxtField.getText();
			HashMap<String, String> currentMD5HashMap = new HashMap<String, String>();

			Set<String> fileNamesSet = fileComparator.FileComparator.listFilesUsingFilesList(dirPath);
			for (String fileName : fileNamesSet) {
				currentMD5HashMap.put(fileName, fileComparator.FileComparator.getMD5Hash(dirPath, fileName));
			}
			HashMap<String, String> savedMD5HashMap = fileComparator.FileComparator.readFromFile(dirPath);
			filesTextArea.setText(fileComparator.FileComparator.compareMD5(currentMD5HashMap, savedMD5HashMap));
		} else {
			System.out.println("Nie podano sciezki do folderu");
		}
	}
}