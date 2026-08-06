package server;

import common.SensorService;
import common.Temperature;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SensorServiceImpl extends UnicastRemoteObject implements SensorService {
    protected SensorServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Temperature takeTemperatureReadings() throws RemoteException {
        return new Temperature(readValue(), Temperature.TemperatureScale.KELVIN);
    }

    private float readValue() {
        return 283.45f;
    }
}