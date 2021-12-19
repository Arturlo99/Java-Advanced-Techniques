package main.java.models;

import main.java.bilboards.IBillboard;
import main.java.bilboards.IManager;
import main.java.bilboards.Order;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedList;

public class Billboard implements IBillboard {

    private String registryHost;
    private int registryPort;
    private String managerName;
    private String billboardName;
    private int billboardPort;
    private int billboardId;
    private Duration displayInterval;
    private int capacity;
    private Registry registry;
    private IBillboard billboardInterface;
    private HashMap<Integer, Order> adQueue = new HashMap<>();
    private boolean threadRunning = false;
    private LinkedList<Integer> orderIdQueue = new LinkedList<>();
    private String boardMsg = "Here u can place your own advert!!!";
    private IManager managerI;

    public Billboard(String registryHost, int registryPort, String managerName, String billboardName,
                     int billboardPort, Duration displayInterval, int capacity) {
        this.registryHost = registryHost;
        this.registryPort = registryPort;
        this.managerName = managerName;
        this.billboardName = billboardName;
        this.billboardPort = billboardPort;
        this.displayInterval = displayInterval;
        this.capacity = capacity;
    }

    public IManager getManagerI() {
        return managerI;
    }

    public void setManagerI(IManager managerI) {
        this.managerI = managerI;
    }

    public String getManagerName() {
        return managerName;
    }

    @Override
    public void setDisplayInterval(Duration displayInterval) throws RemoteException {
        this.displayInterval = displayInterval;
    }

    public Registry getRegistry() {
        return registry;
    }

    public IBillboard getBillboardInterface() {
        return billboardInterface;
    }

    @Override
    public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException {

        if (adQueue.size() < capacity) {
            Order order = new Order();
            order.displayPeriod = displayPeriod;
            order.advertText = advertText;
            synchronized (adQueue) {
                adQueue.put(orderId, order);
                orderIdQueue.add(orderId);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAdvertisement(int orderId) throws RemoteException {
        if (adQueue.containsKey(orderId)) {
            Order adToRemove = adQueue.get(orderId);
            synchronized (adQueue) {
                if (adToRemove != null) {
                    adQueue.remove(orderId);
                    orderIdQueue.remove((Integer) orderId);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int[] getCapacity() throws RemoteException {
        int freeSpots = this.capacity - adQueue.size();
        return new int[]{capacity, freeSpots};
    }

    @Override
    public boolean start() throws RemoteException {
        threadRunning = true;
        Thread displayThread = new Thread(() -> {
            Order order;
            while (threadRunning) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                if (orderIdQueue.size() > 0) {
                    int currentAdId = orderIdQueue.poll();
                    order = adQueue.get(currentAdId);
                    if (order.displayPeriod.getSeconds() > 0) {
                        order.displayPeriod = order.displayPeriod.minus(displayInterval);
                        boardMsg = order.advertText;

                        if (order.displayPeriod.getSeconds() > 0) {
                            orderIdQueue.add(currentAdId);
                        } else {
                            adQueue.remove(currentAdId);
                        }

                        try {
                            Thread.sleep(displayInterval.toMillis());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    boardMsg = ("Here u can place your own advert!!!");
                }
            }
        });
        displayThread.start();

        return true;
    }

    @Override
    public boolean stop() throws RemoteException {
        threadRunning = false;
        managerI.unbindBillboard(this.billboardId);
        UnicastRemoteObject.unexportObject(this, true);
        System.exit(0);
        return true;
    }

    public void exportToRegistry() {
        try {
            registry = LocateRegistry.getRegistry(registryHost, registryPort);
            billboardInterface = (IBillboard) UnicastRemoteObject.exportObject(this, billboardPort);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String getBoardMsg() {
        return boardMsg;
    }

    public void setBillboardId(int billboardId) {
        this.billboardId = billboardId;
    }

    public boolean isThreadRunning() {
        return threadRunning;
    }
}
