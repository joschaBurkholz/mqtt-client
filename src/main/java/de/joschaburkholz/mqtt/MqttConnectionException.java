package de.joschaburkholz.mqtt;

public class MqttConnectionException extends Exception {

    public MqttConnectionException(String message, Throwable t) {
        super(message, t);
    }
}
