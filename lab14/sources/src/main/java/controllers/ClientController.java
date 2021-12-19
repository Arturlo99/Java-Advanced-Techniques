package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import jmx.ManagerMXBean;
import queue.system.CaseCategory;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.List;

public class ClientController implements NotificationListener {

    @FXML
    private ListView<CaseCategory> categoriesListView;

    @FXML
    private TextField categoryNameTxtField;

    @FXML
    private TextField priorityTxtField;

    @FXML
    private Button deleteCategoryBtn;

    @FXML
    private Button createCategoryBtn;

    @FXML
    private TextField symbolTxtField;

    @FXML
    private TextArea notificationsTxtArea;

    private ObservableList<CaseCategory> caseCategoryObservableList;

    private ManagerMXBean proxy;

    @FXML
    void initialize() throws Exception {

        categoriesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(CaseCategory item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.toString() == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });

        connectWithManager();
        caseCategoryObservableList = FXCollections.observableList(getCategoryList());
        categoriesListView.setItems(caseCategoryObservableList);
    }

    @FXML
    void createCategoryHandler(ActionEvent event) throws Exception {
        CaseCategory caseCategory = new CaseCategory(categoryNameTxtField.getText(), symbolTxtField.getText(),
                Integer.parseInt(priorityTxtField.getText()), 0);
        List<CaseCategory> caseCategoryList = getCategoryList();
        caseCategoryList.add(caseCategory);
        setCategoryList(caseCategoryList);
        caseCategoryObservableList = FXCollections.observableList(getCategoryList());
        categoriesListView.setItems(caseCategoryObservableList);
        categoriesListView.refresh();
    }

    @FXML
    void deleteCategoryHandler(ActionEvent event) {
        int selectedIndex = categoriesListView.getSelectionModel().getSelectedIndex();
        List<CaseCategory> caseCategoryList = getCategoryList();
        caseCategoryList.remove(selectedIndex);
        setCategoryList(caseCategoryList);
        caseCategoryObservableList = FXCollections.observableList(getCategoryList());
        categoriesListView.setItems(caseCategoryObservableList);
        categoriesListView.refresh();
    }

    @FXML
    void updatePriorityHandler(ActionEvent event) {
        int selectedIndex = categoriesListView.getSelectionModel().getSelectedIndex();
        caseCategoryObservableList.get(selectedIndex).setPriority(Integer.parseInt(priorityTxtField.getText()));
        setCategoryList(caseCategoryObservableList);
        categoriesListView.refresh();
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        this.notificationsTxtArea.setText(notification.getMessage());
        synchronized (getCategoryList()){
            Platform.runLater(() -> {
                caseCategoryObservableList = FXCollections.observableList(getCategoryList());
                categoriesListView.setItems(caseCategoryObservableList);
                categoriesListView.refresh();
            });
        }
    }

    public void connectWithManager() throws Exception {
        int jmxPort = 8008;

        JMXServiceURL target = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + jmxPort + "/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(target);

        MBeanServerConnection mbs = connector.getMBeanServerConnection();
        ObjectName objectName = new ObjectName("pl.edu.pwr.asoltys:name=" + "Manager");
        mbs.addNotificationListener(objectName, this, null, null);
        proxy = JMX.newMXBeanProxy(mbs, objectName, ManagerMXBean.class);
    }

    public List<CaseCategory> getCategoryList() {
        return proxy.getCategoryList();
    }

    public void setCategoryList(List<CaseCategory> categoryList) {
        proxy.setCategoryList(categoryList);
    }

}

