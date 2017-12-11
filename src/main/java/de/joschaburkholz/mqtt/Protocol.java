package de.joschaburkholz.mqtt;

public enum Protocol {
    TCP("tcp"), WEBSOCKET("ws");

    Protocol(String value){
        this.value = value;
    }

    private String value;

    String getValue(){
        return this.value;
    }

}
