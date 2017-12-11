package de.joschaburkholz.mqtt;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MqttServiceBuilder {

    private String server = "localhost";
    private int port = 1883;
    private String protocol = Protocol.TCP.getValue();
    private String clientId = UUID.randomUUID().toString();
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    public static MqttServiceBuilder newMqttService(){
        return new MqttServiceBuilder();
    }

    public MqttServiceBuilder withServer(String server){
        this.server = server;
        return this;
    }

    public MqttServiceBuilder withPort(int port){
        this.port = port;
        return this;
    }

    public MqttServiceBuilder withProtocol(Protocol protocol){
        this.protocol = protocol.getValue();
        return this;
    }

    public MqttServiceBuilder withProtocol(String protocol){
        this.protocol = protocol;
        return this;
    }

    public MqttServiceBuilder withClientId(String clientId){
        this.clientId = clientId;
        return this;
    }

    public MqttServiceBuilder withObjectMapper(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        return this;
    }


    public MqttService build() throws MqttException {
        MqttClient client = new MqttClient(this.getServerUri(), clientId);
        client.connect();
        return new MqttService(server, objectMapper, client);
    }

    private String getServerUri() {
        String uri = String.format("%s://%s:%d", protocol, server, port);
        log.info("MQTT server to connect to: {}", uri);
        return uri;
    }
}
