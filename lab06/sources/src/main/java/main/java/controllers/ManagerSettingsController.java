package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.models.Manager;

import java.io.IOException;

public class ManagerSettingsController {

    @FXML
    private TextField manPortTxtField;

    @FXML
    private TextField regPortTxtField;

    @FXML
    private TextField manNameTxtField;

    @FXML
    private Button submitSettingsBtn;

    @FXML
    void submitSettingsHandler(ActionEvent event) throws IOException {
        Manager manager = new Manager(manNameTxtField.getText(),
                Integer.parseInt(regPortTxtField.getText()),
                Integer.parseInt(manPortTxtField.getText()));
        manager.createAndExportToRegistry();
        showManagerPanel(manager, event);
    }

    public void showManagerPanel(Manager manager, ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ManagerScene.fxml"));

        stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setResizable(false);
        stage.setTitle("Manager panel");

        ManagerController controller = loader.getController();
        controller.initManagerData(manager);

        stage.show();
    }

}
