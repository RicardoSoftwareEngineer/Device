package com.example.device.exception;

import com.example.device.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceNotFoundException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(DeviceNotFoundException.class);

    public DeviceNotFoundException(String deviceId) {
        super(ErrorMessage.DEVICE_NOT_FOUND.format(deviceId));
        logger.info(ErrorMessage.DEVICE_NOT_FOUND.format(deviceId));
    }
}
