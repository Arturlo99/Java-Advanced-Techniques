package controllers;

import chat.Client;
import chat.Cryptography;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;


public class ClientController {

    @FXML
    private TextArea inMsgTextArea;

    @FXML
    private Button sendMsgBtn;

    @FXML
    private TextArea outMsgTextArea;

    @FXML
    private TextField hostTextField;

    @FXML
    private TextField portTextField;

    @FXML
    private Button connectBtn;

    @FXML
    private Button disconnectBtn;

    private Client client;
    private String incomingMsg = "";
    private Thread msgThread;

    @FXML
    void initialize() throws Exception {
        System.setProperty("java.security.policy", "./java.policy");
        System.setSecurityManager(new SecurityManager());
        client = new Client();
        msgThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if (client.getIn() != null && client.getIn().ready()) {
                        incomingMsg = client.getIn().readLine();
                        if (!incomingMsg.isEmpty()) {
                            String decryptedMsg = Cryptography.decryptBase64(incomingMsg.getBytes(),
                                    client.getKeypair().getPrivate());
                            inMsgTextArea.appendText("Stranger: " + decryptedMsg + "\n");
                            incomingMsg = "";
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void connectBtnHandler(ActionEvent event) throws Exception {
        if (!hostTextField.getText().isEmpty() && !portTextField.getText().isEmpty()) {
            client.startConnection(hostTextField.getText(), Integer.parseInt(portTextField.getText()));
        }
        msgThread.start();
    }

    @FXML
    void disconnectBtnHandler(ActionEvent event) throws IOException {
        client.stopConnection();
    }

    @FXML
    public void sendMsgBtnHandler(ActionEvent event) throws Exception {
        client.sendMessage(outMsgTextArea.getText());
        inMsgTextArea.appendText("You: " + outMsgTextArea.getText() + "\n");
        outMsgTextArea.clear();
    }

}
