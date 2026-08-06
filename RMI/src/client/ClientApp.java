package client;

import common.SensorService;
import common.Temperature;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientApp {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            SensorService service = (SensorService) registry.lookup("SensorService");

            Temperature temperature = service.takeTemperatureReadings();
            System.out.println(temperature.getDegreesInKelvin());
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}