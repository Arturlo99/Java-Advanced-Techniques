package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.models.Billboard;

import java.io.IOException;
import java.time.Duration;

public class BillboardSettingsController {
    @FXML
    private TextField rmiHostTxtField;

    @FXML
    private TextField rmiRegistryPortTxtField;

    @FXML
    private TextField manNameTxtField;

    @FXML
    private TextField billboardNameTxtField;

    @FXML
    private TextField billboardPortTxtField;

    @FXML
    private TextField displayIntervalTxtField;

    @FXML
    private TextField displayBufferSizeTxtField;

    @FXML
    private Button submitBtn;

    @FXML
    void submitBtnHandler(ActionEvent event) throws IOException {
        Billboard billboard = new Billboard(rmiHostTxtField.getText(),
                Integer.parseInt(rmiRegistryPortTxtField.getText()),
                manNameTxtField.getText(), billboardNameTxtField.getText(),
                Integer.parseInt(billboardPortTxtField.getText()),
                Duration.ofSeconds(Integer.parseInt(displayIntervalTxtField.getText())),
                Integer.parseInt(displayBufferSizeTxtField.getText()));
        billboard.exportToRegistry();
        showBillboardPanel(billboard, event);
    }

    public void showBillboardPanel(Billboard billboard, ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BillboardScene.fxml"));

        stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setResizable(false);
        stage.setTitle("Billboard panel");
        BillboardController controller = loader.getController();
        controller.initBillboardData(billboard);

        stage.show();
    }
}
