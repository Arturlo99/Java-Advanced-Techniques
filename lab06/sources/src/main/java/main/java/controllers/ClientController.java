package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.bilboards.IManager;
import main.java.bilboards.Order;
import main.java.models.Client;
import main.java.models.OrderWithId;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.Duration;

public class ClientController {

    @FXML
    private TextArea advertMessageTxtArea;

    @FXML
    private Button orderBtn;

    @FXML
    private TextField advertDurationTxtField;

    @FXML
    private Button withdrawAdBtn;

    @FXML
    private TableView<OrderWithId> ordersTableView;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> msgCol;

    @FXML
    private TableColumn<?, ?> durationCol;

    private Client client;


    @FXML
    public void initialize() {
        System.setProperty("java.security.policy", "./java.policy");
        System.setSecurityManager(new SecurityManager());
        idCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        msgCol.setCellValueFactory(new PropertyValueFactory<>("advertText"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("displayPeriod"));
    }

    void initClientData(Client client) {
        this.client = client;
    }

    @FXML
    void orderBtnHandler(ActionEvent event) {
        if (advertDurationTxtField.getText().equals("") || advertMessageTxtArea.getText().equals(""))
            return;
        Order order = new Order();
        order.advertText = advertMessageTxtArea.getText();
        order.client = client.getClientInterface();
        IManager mi;
        try {
            order.displayPeriod = Duration.ofSeconds(Integer.parseInt(advertDurationTxtField.getText()));
            mi = (IManager) client.getRegistry().lookup(client.getManagerName());
            if (mi.placeOrder(order)) {
                OrderWithId orderWithId = new OrderWithId(client.getOrderId(), order);
                ordersTableView.getItems().add(orderWithId);
                ordersTableView.refresh();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Something went wrong.");
                a.show();
            }
        } catch (RemoteException | NotBoundException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    void withdrawBtnHandler(ActionEvent event) {

        OrderWithId orderToWithdraw = ordersTableView.getSelectionModel().getSelectedItem();
        if (orderToWithdraw != null) {
            try {
                IManager mi = (IManager) client.getRegistry().lookup(client.getManagerName());
                mi.withdrawOrder(orderToWithdraw.getOrderId());
                ordersTableView.getItems().remove(orderToWithdraw);
                ordersTableView.refresh();
            } catch (RemoteException | NotBoundException e1) {
                e1.printStackTrace();
            }
        }
    }

}
