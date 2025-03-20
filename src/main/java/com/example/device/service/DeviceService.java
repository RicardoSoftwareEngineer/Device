package com.example.device.service;

import com.example.device.dto.DeviceDTO;
import com.example.device.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    public DeviceDTO create(DeviceDTO deviceDTO){
        return null;
    }

    public DeviceDTO retrieve(String deviceId){
        return null;
    }

    public List<DeviceDTO> list(){
        return null;
    }

    public DeviceDTO update(DeviceDTO deviceDTO){
        return null;
    }

    public void delete(String deviceId){
    }
}
