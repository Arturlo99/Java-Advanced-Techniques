package main.java.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.bilboards.IBillboard;
import main.java.models.Manager;
import main.java.models.BillboardRow;

import java.rmi.RemoteException;

public class ManagerController {


    @FXML
    private TableView<BillboardRow> billboardsTableView;

    @FXML
    private TableColumn<BillboardRow, Integer> idCol;

    @FXML
    private TableColumn<BillboardRow, Integer> maxCapCol;

    @FXML
    private TableColumn<BillboardRow, Integer> availableSpotsCol;

    private ObservableList<BillboardRow> billboardObsList = FXCollections.observableArrayList();

    @FXML
    private Button unbindBtn;

    private Manager manager;

    @FXML
    public void initialize(){
        System.setProperty("java.security.policy", "./java.policy");
        System.setSecurityManager(new SecurityManager());
    }

    void initManagerData(Manager manager) {
        this.manager = manager;
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        maxCapCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        availableSpotsCol.setCellValueFactory(new PropertyValueFactory<>("freeSpots"));
        new Thread(() -> {
            while (true) {
                synchronized (manager.getBillboardsTableRows()) {
                    for (int i = 0; i < manager.getBillboardsTableRows().size(); i++) {
                        billboardObsList = FXCollections.observableArrayList(manager.getBillboardsTableRows());
                        billboardsTableView.getItems().setAll(billboardObsList);
                        try {
                            if (!billboardsTableView.getItems().isEmpty()){
                                int freeSpots = manager.getIbillboards().get(i).getCapacity()[1];
                                billboardsTableView.getItems().get(i).setFreeSpots(freeSpots);
                                billboardsTableView.refresh();
                            }

                        } catch (Exception e) {
                        }
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @FXML
    void unbindBtnHandler(ActionEvent event) {
        BillboardRow billboardToRemove = billboardsTableView.getSelectionModel().getSelectedItem();
        if(billboardToRemove != null) {
            IBillboard b = manager.getIbillboards().remove(billboardToRemove.getId());
            billboardsTableView.getItems().remove(billboardToRemove);
            billboardsTableView.refresh();
            try {
                b.stop();
            } catch (RemoteException e1) {
            }

        }
    }

}