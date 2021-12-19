package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import main.java.bilboards.IManager;
import main.java.models.Billboard;

public class BillboardController {

    private Billboard billboard;

    @FXML
    public void initialize(){
        System.setProperty("java.security.policy", "./java.policy");
        System.setSecurityManager(new SecurityManager());
    }

    void initBillboardData(Billboard billboard) {
        this.billboard = billboard;
        try {
            billboard.setManagerI((IManager) this.billboard.getRegistry().lookup(this.billboard.getManagerName()));
            billboard.setBillboardId(billboard.getManagerI().bindBillboard(this.billboard.getBillboardInterface()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread displayMsgThread = new Thread(() -> {
            while (billboard.isThreadRunning()){
                try {
                    Thread.sleep(100);
                    advertTextField.setText(billboard.getBoardMsg());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        displayMsgThread.start();
    }

    @FXML
    private TextArea advertTextField;

}
