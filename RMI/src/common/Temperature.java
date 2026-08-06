package common;

import java.io.Serializable;

public class Temperature implements Serializable {

    private final float degreesInKelvin;

    public Temperature(float degrees, TemperatureScale scale) {
        this.degreesInKelvin = switch (scale) {
            case KELVIN -> degrees;
            case CELSIUS -> celsiusToKelvin(degrees);
            case FAHRENHEIT -> fahrenheitToKelvin(degrees);
            case null, default -> throw new IllegalArgumentException();
        };
    }

    public float getDegreesInKelvin() {
        return degreesInKelvin;
    }

    public float getDegreesInCelsius() {
        return kelvinToCelsius(degreesInKelvin);
    }

    public float getDegreesInFahrenheit() {
        return kelvinToFahrenheit(degreesInKelvin);
    }

    public enum TemperatureScale {
        KELVIN, CELSIUS, FAHRENHEIT;
    }

    private static float celsiusToKelvin(float degrees) {
        return degrees + 273.15f;
    }

    private static float kelvinToCelsius(float degrees) {
        return degrees - 273.15f;
    }

    private static float fahrenheitToKelvin(float degrees) {
        return (degrees + 459.67f) * 5 / 9;
    }

    private static float kelvinToFahrenheit(float degrees) {
        return kelvinToCelsius(degrees) * 1.8f + 32;
    }
}