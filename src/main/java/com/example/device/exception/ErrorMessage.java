package com.example.device.exception;

public enum ErrorMessage {
    DEVICE_NOT_FOUND("Device not found with ID: %s"),
    DEVICE_IN_USE_UPDATE_NAME("Name cannot be updated if the device is in use"),
    DEVICE_IN_USE_UPDATE_BRAND("Brand cannot be updated if the device is in use"),
    DEVICE_IN_USE_DELETE("In use devices cannot be deleted");

    private final String messageTemplate;

    ErrorMessage(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessage() {
        return messageTemplate;
    }

    public String format(Object... args) {
        return String.format(messageTemplate, args);
    }
}
