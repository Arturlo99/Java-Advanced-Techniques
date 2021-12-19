package controllers;

import chat.Host;
import chat.Cryptography;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HostController {

    @FXML
    private TextArea inMsgTextArea;

    @FXML
    private Button sendMsgBtn;

    @FXML
    private TextArea outMsgTextArea;

    @FXML
    private TextField portTxtField;

    @FXML
    private Button runServerBtn;

    private Host host;
    private String incomingMsg = "";
    private Thread msgThread;

    @FXML
    void initialize(){
        System.setProperty("java.security.policy", "./java.policy");
        System.setSecurityManager(new SecurityManager());
    }

    @FXML
    void runServerBtnHandler(ActionEvent event) throws Exception {
        host = new Host();
        host.start(Integer.parseInt(portTxtField.getText()));
        initializeMsgThread();
        msgThread.start();
    }

    private void initializeMsgThread() {
        msgThread = new Thread(() -> {
            try {
                host.getGetClientThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if (host.getIn() != null && host.getIn().ready()) {
                        incomingMsg = host.getIn().readLine();
                        if (!incomingMsg.isEmpty()) {
                            String decryptedMsg = Cryptography.decryptBase64(incomingMsg.getBytes(),
                                    host.getKeypair().getPrivate());
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
    void sendMsgBtnHandler(ActionEvent event) throws Exception {

        host.sendMessage(outMsgTextArea.getText());
        inMsgTextArea.appendText("You: " + outMsgTextArea.getText() + "\n");
        outMsgTextArea.clear();
    }
}
