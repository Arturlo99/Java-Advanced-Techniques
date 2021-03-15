package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.WeakHashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;

public class FilePreviewController {

	@SuppressWarnings("exports")
	@FXML
	public Button dirChooserBtn;

	@SuppressWarnings("exports")
	@FXML
	public ImageView imageView;

	@FXML
	public ListView<File> filesListView;

	@SuppressWarnings("exports")
	@FXML
	public TextArea textArea;
	
	@SuppressWarnings("exports")
	@FXML
	public TextField notificationTxtField;

	private ObservableList<File> filesListData;

	File selectedFile;

	WeakHashMap<String, File> weakHashMap = new WeakHashMap<String, File>();

	public void dirChooserBtnAction() throws IOException {

		filesListData = FXCollections.observableArrayList();
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Choose directory to see it's content");
		File selectedDir = directoryChooser.showDialog(null);

		if (selectedDir != null) {
			File[] files = selectedDir.listFiles();

			for (File file : files) {
				String fileExtension = getFileExtension(file);
				if (!file.isDirectory() && (fileExtension.equals("txt") || fileExtension.equals("png"))) {
					filesListData.add(file);
					filesListView.setItems(filesListData);
				}
			}
		}
	}

	public void selectItem() throws IOException {

		selectedFile = filesListView.getSelectionModel().getSelectedItem();
		
		if (selectedFile!= null) {
			String selectedFileName = filesListView.getSelectionModel().getSelectedItem().getName();
			if (!weakHashMap.containsKey(selectedFileName)) {
				selectedFile = filesListView.getSelectionModel().getSelectedItem();
				weakHashMap.put(selectedFile.getName(), selectedFile);
				notificationTxtField.setText("Zawartoœæ pliku za³adowano ponownie!");
			} else {
				selectedFile = weakHashMap.get(selectedFile.getName());
				notificationTxtField.setText("Zawartoœæ pliku odzyskano z pamiêci!");
				
			}
			String fileExtension = getFileExtension(selectedFile);
			
			if (fileExtension.equals("png")) {
				displayPngFile(selectedFile);
			} else {
				displayTxtFile(selectedFile);
			}
			
		}
	}

	private void displayPngFile (File selectedFile) {
		imageView.setVisible(true);
		textArea.setVisible(false);
		Image image = new Image(selectedFile.toURI().toString(), true);
		imageView.setImage(image);
	}
	
	private void displayTxtFile(File selectedFile) {
		StringBuilder stringBuilder = new StringBuilder();
		textArea.setVisible(true);
		imageView.setVisible(false);
		Path path = Paths.get(selectedFile.getAbsolutePath());
		try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
			String currentLine = null;
			while ((currentLine = reader.readLine()) != null) {// while there is content on the current line
				stringBuilder.append(currentLine + "\n");
			}
		} catch (IOException ex) {
			ex.printStackTrace(); // handle an exception here
		}

		textArea.setText(stringBuilder.toString());
	}

	private String getFileExtension(File file) {
		String fileName = file.getName();
		String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
		return fileExtension;
	}

}
