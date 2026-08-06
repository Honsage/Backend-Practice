package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SensorService extends Remote {
    Temperature takeTemperatureReadings() throws RemoteException;
}