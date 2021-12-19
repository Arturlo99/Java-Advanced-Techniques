package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController {
    private final FileChooser imageChooser = new FileChooser();
    private final FileChooser scriptChooser = new FileChooser();
    @FXML
    private Button loadScriptBtn;
    @FXML
    private ImageView beforeImageView;
    @FXML
    private ImageView afterImageView;
    @FXML
    private ListView<File> scriptsListView;
    @FXML
    private Button loadImageBtn;
    @FXML
    private Button unloadScriptBtn;
    @FXML
    private Button processBtn;

    ArrayList<File> scripts = new ArrayList<>();
    ScriptEngineManager sem = new ScriptEngineManager();
    ScriptEngine jsEngine = sem.getEngineByName("nashorn");
    BufferedImage image = null;
    BufferedImage outputImage = null;

    public MainWindowController() {
    }

    @FXML
    void initialize() {
        scriptsListView.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
            public ListCell<File> call(ListView<File> param) {
                return new ListCell<File>() {
                    @Override
                    protected void updateItem(File item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item == null || empty ? null : item.getName());
                    }
                };
            }
        });
        configureImageChooser(imageChooser);
        configureScriptChooser(scriptChooser);
    }

    @FXML
    void loadImageBtnActionHandler(ActionEvent event) {
        File image = imageChooser.showOpenDialog(null);
        if (image != null) {
            displayImage(image);
        }
    }

    @FXML
    void loadScriptBtnActionHandler(ActionEvent event) {
        File selectedScript = scriptChooser.showOpenDialog(null);
        if (selectedScript != null) {
            String fileName = selectedScript.toString();
            int index = fileName.lastIndexOf('.');
            if (index > 0) {
                String extension = fileName.substring(index + 1);
                if (extension.equals("js")) {
                    scripts.add(selectedScript);
                    setScriptsListView(scripts);
                }
            }
        }
    }

    @FXML
    void unloadScriptBtnActionHandler(ActionEvent event) {
        File scriptToBeUnloaded = scriptsListView.getSelectionModel().getSelectedItem();
        scripts.remove(scriptToBeUnloaded);
        setScriptsListView(scripts);
    }

    @FXML
    void processBtnActionHandler(ActionEvent event) {
        Invocable invocableEngine = (Invocable)jsEngine;
        try {
            InputStream inputStream = new FileInputStream(scriptsListView.getSelectionModel().getSelectedItem());
            Reader reader = new InputStreamReader(inputStream);
            jsEngine.eval(reader);
            Object object = invocableEngine.invokeFunction("process", image);
            outputImage = (BufferedImage) object;
        }
        catch (ScriptException | NoSuchMethodException | FileNotFoundException e) {
            e.printStackTrace();
        }
        afterImageView.setVisible(true);
        afterImageView.setImage(SwingFXUtils.toFXImage(outputImage, null));
    }

    void setScriptsListView(List<File> scripts) {
        ObservableList<File> observableScriptNames = FXCollections.observableList(scripts);
        scriptsListView.setItems(observableScriptNames);
        scriptsListView.refresh();
    }

    private void displayImage(File selectedFile) {
        beforeImageView.setVisible(true);
        try {
            image = ImageIO.read(selectedFile);
            beforeImageView.setImage(SwingFXUtils.toFXImage(image, null));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void configureImageChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Selection of Picture");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.bmp", "*.png", "*.jpg", "*.jpeg")
        );
    }

    private void configureScriptChooser(final FileChooser scriptChooser) {
        scriptChooser.setTitle("Selection of Script");
        scriptChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JS files", "*.js")
        );
    }
}
