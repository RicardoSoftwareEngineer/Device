package com.example.device.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceUpdateForbiddenException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(DeviceUpdateForbiddenException.class);

    public DeviceUpdateForbiddenException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        logger.info(errorMessage.getMessage());
    }
}
