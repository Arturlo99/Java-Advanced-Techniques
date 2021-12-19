package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import jmx.Manager;
import jmx.ManagerMXBean;
import queue.system.Case;
import queue.system.CaseCategory;
import run.ClientMain;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ApplicationController  {

    @FXML
    private ComboBox<String> categoriesComboBox;
    @FXML
    private ListView<Case> informationBoardListView;
    @FXML
    private Button getTicketBtn;
    @FXML
    private Button manageBtn;

    private Thread casesHandleThread;
    private Thread refreshComboBoxThread;
    private Manager manager;
    private List<String> categoryNameAndPriorityList = new ArrayList<>();
    private ObservableList<String> categoryObservableList;
    private List<Case> caseList = new ArrayList<>();
    private ObservableList<Case> caseObservableList;
    private PriorityQueue<Case> casePriorityQueue = new PriorityQueue<>(new Comparator<Case>() {
        @Override
        public int compare(Case o1, Case o2) {
            if (o1.getPriority() < o2.getPriority())
                return 1;
            else if (o1.getPriority() > o2.getPriority())
                return -1;
            else if (o1.getSymbol().length() < o2.getSymbol().length())
                return -1;
            else if (o1.getSymbol().length() > o2.getSymbol().length())
                return 1;
            else if (o1.getSymbol().compareTo(o2.getSymbol()) > 0)
                return 1;
            return -1;
        }
    });

    @FXML
    void initialize() {
        informationBoardListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Case item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getSymbol() == null) {
                    setText(null);
                } else {
                    setText(item.getSymbol());
                }
            }
        });

        manager = new Manager();
        try {
            ManagementFactory.getPlatformMBeanServer().registerMBean(manager,
                    new ObjectName("pl.edu.pwr.asoltys:name=" + "Manager"));
//            Thread.currentThread().join();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        manager.getCategoryList().add(new CaseCategory("Personal data", "P", 3));
        manager.getCategoryList().add(new CaseCategory("Testament", "T", 4));
        manager.getCategoryList().add(new CaseCategory("Car", "C", 5));
        manager.getCategoryList().forEach(e -> categoryNameAndPriorityList.add(e.toString()));
        categoryObservableList = FXCollections.observableList(categoryNameAndPriorityList);
        categoriesComboBox.setItems(categoryObservableList);
        categoriesComboBox.getSelectionModel().select(0);
        refreshComboBoxThread = new Thread(this::refreshComboBox);
        refreshComboBoxThread.start();
        casesHandleThread = new Thread(this::casesHandle);
        casesHandleThread.start();
    }

    @FXML
    void getTicketHandler(ActionEvent event) {
        synchronized (casePriorityQueue) {
            CaseCategory selectedCaseCategory = manager.getCategoryList().get(categoriesComboBox.getSelectionModel().getSelectedIndex());
            Case newCase = new Case(selectedCaseCategory.getSymbol() + selectedCaseCategory.getCounter(), selectedCaseCategory.getPriority());
            selectedCaseCategory.setCounter(selectedCaseCategory.getCounter() + 1);
            casePriorityQueue.add(newCase);
            updateInformationBoard();
        }
    }

    private void updateInformationBoard() {
        PriorityQueue<Case> priorityQueueCopy = new PriorityQueue<>(casePriorityQueue);
        caseList = new ArrayList<>();
        while (!priorityQueueCopy.isEmpty()) {
            caseList.add(priorityQueueCopy.poll());
        }
        caseObservableList = FXCollections.observableList(caseList);
        informationBoardListView.setItems(caseObservableList);
        informationBoardListView.refresh();
    }

    private void refreshComboBox() {
        while (true) {
            categoryNameAndPriorityList = new ArrayList<>();
            manager.getCategoryList().forEach(e -> categoryNameAndPriorityList.add(e.toString()));
            categoryObservableList = FXCollections.observableList(categoryNameAndPriorityList);
            Platform.runLater(() -> {
                categoriesComboBox.setItems(categoryObservableList);
            });
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void casesHandle() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (casePriorityQueue) {
                casePriorityQueue.poll();
                Platform.runLater(this::updateInformationBoard);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
