package controllers;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import example.MyClassLoader;
import example.MyStatusListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import processing.StatusListener;

public class MainWindowController {
	@FXML
	private TextArea beforeTxtArea;

	@FXML
	private TextArea afterTxtArea;

	@FXML
	private Button startTaskBtn;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private ListView<String> classesListView;

	@FXML
	private Button refreshListBtn;

	@FXML
	private Button chooseDirectoryBtn;

	@FXML
	private TextField currentDirPathTxtField;

	HashMap<String, Path> descAndPathMap;
	Path classesPath;
	private String packageName;
	private ObservableList<String> classesDesc;
	static Boolean end = false;
	MyStatusListener myStatusListener;
	Method getResult;
	Object newObject;
	MyClassLoader myClassLoader;
	Class<?> loadedClass;
	Constructor<?> cp;
	Method submitTask;
	Class<?>[] parameterClasses;

	public void initialize() {
		classesPath = Paths.get(System.getProperty("user.dir"));
		currentDirPathTxtField.setText(classesPath.toString());
		loadClasses(new File(classesPath.toString()));
	}

	@FXML
	public void chooseDirectory() {

		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Choose directory with compiled classes");
		directoryChooser.setInitialDirectory(
				new File("C:\\Users\\Zdzis³aw\\eclipse-workspace\\lab04_JAVA\\bin\\textmodifiers"));
		File selectedDir = directoryChooser.showDialog(null);
		if (selectedDir != null) {
			loadClasses(selectedDir);
			currentDirPathTxtField.setText(selectedDir.getAbsolutePath());
		}
	}
	
	public void refreshListBtnHandler() {
		loadClasses(new File(currentDirPathTxtField.getText()));
	}

	public void loadClasses(File selectedDir) {
		classesListView.getItems().clear();
		File[] files = selectedDir.listFiles();
		packageName = selectedDir.getName();
		classesPath = Paths.get(selectedDir.getParent());
		classesDesc = FXCollections.observableArrayList();
		for (File file : files) {
			String fileExtension = getFileExtension(file);
			if (!file.isDirectory() && (fileExtension.equals("class"))) {
				MyClassLoader myClassLoader = new MyClassLoader(classesPath);
				Class<?> loadedClass;
				String fileNameWithoutExt = file.getName().replace("." + fileExtension, "");
				try {

					loadedClass = myClassLoader.loadClass(packageName + "." + fileNameWithoutExt);
					Constructor<?> cp = loadedClass.getConstructor();
					// tworzymy obiekt za³adowanej klasy
					Object o = cp.newInstance();
					// wiemy, ¿e o dostarcza implementacji interfejsu Processor, dlatego szukamy
					// metody submitTask
					Method method = loadedClass.getMethod("getInfo");
					// teraz chcemy wywo³aæ tê metodê z parametrem "Task" oraz instancj¹ znanego nam
					// s³uchacza
					// - ten s³uchacz ma na interfejsie pokazywaæ postêp przetwarzania
					// - znamy tego s³uchacza podczas budowy aplikacji (klasa s³uchacza nie jest
					// ³adowana dynamicznie)
					String classDesc = fileNameWithoutExt + " / " + method.invoke(o);
					classesDesc.add(classDesc);
					classesListView.setItems(classesDesc);
					classesListView.refresh();
					// Wy³adowywanie
					cp = null;
					loadedClass = null;
					myClassLoader = null;
					o = null;
					method = null;
					System.gc();

				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
					e.printStackTrace();
				}

			}

		}
	}

	@FXML
	public void startTaskBtnHandler() throws ClassNotFoundException {
		afterTxtArea.clear();
		progressBar.setProgress(0.0);
		startTaskBtn.setDisable(true);

		try {
			myClassLoader = new MyClassLoader(classesPath);
			String selectedFileDesc;
			if (classesListView.getSelectionModel().getSelectedItem() != null) {
				selectedFileDesc = classesListView.getSelectionModel().getSelectedItem();
				String selectedFileName = selectedFileDesc.substring(0, selectedFileDesc.indexOf('/')).trim();
				loadedClass = myClassLoader.loadClass(packageName + "." + selectedFileName);
				cp = loadedClass.getConstructor();
				newObject = cp.newInstance();
				myStatusListener = new MyStatusListener();
				parameterClasses = new Class<?>[] { String.class, StatusListener.class };
				submitTask = loadedClass.getMethod("submitTask", parameterClasses);
				getResult = loadedClass.getMethod("getResult");
				Object[] arguments = new Object[] { beforeTxtArea.getText(), myStatusListener };
				submitTask.invoke(newObject, arguments);
			}

		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {

				while (myStatusListener.getProgress() < 100) {
					updateProgress(myStatusListener.getProgress(), 100);
				}

				updateProgress(1, 1);

				afterTxtArea.setText((String) getResult.invoke(newObject));
				startTaskBtn.setDisable(false);
				progressBar.progressProperty().unbind();
				loadedClass = null;
				parameterClasses = null;
				newObject = null;
				submitTask = null;
				getResult = null;
				myClassLoader = null;
				System.gc();

				return null;
			}
		};

		progressBar.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
	}

	private String getFileExtension(File file) {
		String fileName = file.getName();
		String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
		return fileExtension;
	}

}
