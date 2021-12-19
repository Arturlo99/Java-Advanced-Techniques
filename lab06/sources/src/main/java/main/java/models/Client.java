package main.java.models;


import main.java.bilboards.IClient;
import main.java.bilboards.IManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client implements IClient {

    private final int registryPort;
    private int clientPort;
    private Registry registry;
    private String managerName;
    private String clientName;
    private String registryHost;
    private int orderId;
    private IManager managerI;
    private IClient clientInterface;

    public int getRegistryPort() {
        return registryPort;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getRegistryHost() {
        return registryHost;
    }

    public void setRegistryHost(String registryHost) {
        this.registryHost = registryHost;
    }

    public void setClientInterface(IClient clientInterface) {
        this.clientInterface = clientInterface;
    }

    public Client(String clientName, int clientPort, String registryHost, String managerName, int registryPort) {
        this.registryPort = registryPort;
        this.clientPort = clientPort;
        this.clientName = clientName;
        this.managerName = managerName;
        this.registryHost = registryHost;
    }

    public IManager getManagerI() {
        return managerI;
    }

    public void setManagerI(IManager managerI) {
        this.managerI = managerI;
    }

    public IClient getClientInterface() {
        return clientInterface;
    }

    public boolean exportToRegistry() {
        try {
            registry = LocateRegistry.getRegistry(registryHost, registryPort);
            clientInterface = (IClient) UnicastRemoteObject.exportObject(this, clientPort);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Registry getRegistry() {
        return registry;
    }

    public String getManagerName() {
        return managerName;
    }

    public int getOrderId() {
        return orderId;
    }

    @Override
    public void setOrderId(int orderId) throws RemoteException {
        this.orderId = orderId;
    }
}
