package com.example.device.exception;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String deviceId) {
        super(ErrorMessage.DEVICE_NOT_FOUND.format(deviceId));
    }
}
