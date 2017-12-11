package de.joschaburkholz.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MqttService {

    private MqttClient mqttClient;

    private String server;
    private ObjectMapper objectMapper;

    protected MqttService(String server, ObjectMapper objectMapper, MqttClient mqttClient){
        this.server = server;
        this.objectMapper = objectMapper;
        this.mqttClient = mqttClient;
    }

    public void sendMessage(String topic, Object payload) throws MqttConnectionException, JsonProcessingException {
        this.connect();
        MqttMessage message = new MqttMessage(objectMapper.writeValueAsString(payload).getBytes());
        try {
            this.mqttClient.publish(topic, message);
        } catch (MqttException e) {
            throw new MqttConnectionException("Sending mqtt message to " + server + " failed.", e);
        }
    }

    private void connect() throws MqttConnectionException {
        if (!mqttClient.isConnected()) {
            try {
                log.info("Connecting to {}", mqttClient.getCurrentServerURI());
                mqttClient.connect();
            } catch (MqttException e) {
                throw new MqttConnectionException("Connection to message broker at " + server + " failed.", e);
            }
        }
    }
}
