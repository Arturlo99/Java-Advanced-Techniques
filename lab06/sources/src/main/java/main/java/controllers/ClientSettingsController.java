package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.models.Client;

import java.io.IOException;

public class ClientSettingsController {
    @FXML
    private TextField manNameTxtField;

    @FXML
    private TextField rmiHostTxtField;

    @FXML
    private TextField clientNameTxtField;

    @FXML
    private TextField rmiPortTxtField;

    @FXML
    private Button submitBtn;

    @FXML
    private TextField clientPortTxtField;


    @FXML
    void submitBtnHandler(ActionEvent event) throws IOException {

        Client client = new Client(clientNameTxtField.getText(),
                Integer.parseInt(clientPortTxtField.getText()),
                rmiHostTxtField.getText(), manNameTxtField.getText(),
                Integer.parseInt(rmiPortTxtField.getText()));
        client.exportToRegistry();
        showClientPanel(client, event);
    }

    public void showClientPanel(Client client, ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientScene.fxml"));

        stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setResizable(false);
        stage.setTitle("Client panel");
        ClientController controller = loader.getController();
        controller.initClientData(client);

        stage.show();
    }

}
