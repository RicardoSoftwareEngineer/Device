package com.example.device.exception;

public class DeviceUpdateForbiddenException extends RuntimeException {
    public DeviceUpdateForbiddenException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
