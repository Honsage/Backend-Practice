package server;

import common.SensorService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerApp {
    public static void main(String[] args) {
        try {
            SensorService stub = new SensorServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("SensorService", stub);
        } catch (RemoteException  e) {
            throw new RuntimeException(e);
        }
    }
}