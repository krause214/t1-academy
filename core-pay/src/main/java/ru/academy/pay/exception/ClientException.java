package ru.academy.pay.exception;

public class ClientException extends RuntimeException  {

    private final String externalMessage;

    public ClientException(String message, String externalMessage) {
        super(message);
        this.externalMessage = externalMessage;
    }

    public String getExternalMessage() {
        return externalMessage;
    }
}
