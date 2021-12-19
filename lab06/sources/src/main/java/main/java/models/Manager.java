package main.java.models;

import main.java.bilboards.IBillboard;
import main.java.bilboards.IManager;
import main.java.bilboards.Order;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class Manager implements IManager {
    private int registryPort;
    Registry registry;
    private IManager managerInterface;
    private String name;
    private int managerPort;
    private int billboardCounter = -1;
    private int adIdCounter = 0;
    private ArrayList<BillboardRow> billboardsTableRows = new ArrayList<>();
    private HashMap<Integer, IBillboard> Ibillboards = new HashMap<>();

    public Manager(String name, int registryPort, int managerPort) {
        this.name = name;
        this.registryPort = registryPort;
        this.managerPort = managerPort;
    }

    public int getRegistryPort() {
        return registryPort;
    }

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public IManager getManagerInterface() {
        return managerInterface;
    }

    public void setManagerInterface(IManager managerInterface) {
        this.managerInterface = managerInterface;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManagerPort() {
        return managerPort;
    }

    public void setManagerPort(int managerPort) {
        this.managerPort = managerPort;
    }

    public int getBillboardCounter() {
        return billboardCounter;
    }

    public void setBillboardCounter(int billboardCounter) {
        this.billboardCounter = billboardCounter;
    }

    public int getAdIdCounter() {
        return adIdCounter;
    }

    public void setAdIdCounter(int adIdCounter) {
        this.adIdCounter = adIdCounter;
    }

    public void setBillboardsTableRows(ArrayList<BillboardRow> billboardsTableRows) {
        this.billboardsTableRows = billboardsTableRows;
    }

    public void setIbillboards(HashMap<Integer, IBillboard> ibillboards) {
        Ibillboards = ibillboards;
    }



    public ArrayList<BillboardRow> getBillboardsTableRows() {
        return billboardsTableRows;
    }

    public HashMap<Integer, IBillboard> getIbillboards() {
        return Ibillboards;
    }

    @Override
    public int bindBillboard(IBillboard billboard) throws RemoteException {

        synchronized (Ibillboards) {
            billboardCounter++;
            Ibillboards.put(billboardCounter, billboard);
            BillboardRow billboardRow = new BillboardRow(billboardCounter,
                    billboard.getCapacity()[0], billboard.getCapacity()[1]);
            billboardsTableRows.add(billboardRow);
        }
        billboard.start();
        return billboardCounter;
    }

    @Override
    public boolean unbindBillboard(int billboardId) throws RemoteException {
        for (BillboardRow billboardRow : billboardsTableRows) {
            if (billboardRow.getId() == billboardId) {
                billboardsTableRows.remove(billboardRow);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        order.client.setOrderId(adIdCounter);
        boolean wasAdded = false;
        for (BillboardRow billboardrow : billboardsTableRows) {
            if (billboardrow.getFreeSpots() > 0) {
                IBillboard ibillboard = Ibillboards.get(billboardrow.getId());
                ibillboard.addAdvertisement(order.advertText, order.displayPeriod, adIdCounter);
                billboardrow.setFreeSpots(ibillboard.getCapacity()[1]);
                wasAdded = true;
            }
        }
        adIdCounter++;
        return wasAdded;
    }

    @Override
    public boolean withdrawOrder(int orderId) throws RemoteException {
        boolean wasWithdrawn = false;
        for (BillboardRow billboardrow : billboardsTableRows) {
            IBillboard ibillboard = Ibillboards.get(billboardrow.getId());
            if (ibillboard.removeAdvertisement(orderId)) {
                billboardrow.setFreeSpots(ibillboard.getCapacity()[1]);
                wasWithdrawn = true;
            }
        }
        return wasWithdrawn;
    }

    public void createAndExportToRegistry() {
        try {
            registry = LocateRegistry.createRegistry(registryPort);
            managerInterface = (IManager) UnicastRemoteObject.exportObject(this, managerPort);
            registry.bind(name, managerInterface);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}