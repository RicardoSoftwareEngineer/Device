package com.example.device.service;

import com.example.device.dto.DeviceDTO;
import com.example.device.entity.DeviceEntity;
import com.example.device.entity.StateEnum;
import com.example.device.exception.DeviceNotFoundException;
import com.example.device.exception.DeviceUpdateForbiddenException;
import com.example.device.exception.ErrorMessage;
import com.example.device.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    public DeviceDTO create(DeviceDTO deviceDTO){
        DeviceEntity deviceEntity = deviceRepository.save(new DeviceEntity(deviceDTO));
        return new DeviceDTO(deviceEntity);
    }

    public DeviceDTO retrieveById(String deviceId){
        DeviceEntity deviceEntity = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException(deviceId));
        return new DeviceDTO(deviceEntity);
    }

    public List<DeviceDTO> retrieveAll() {
        return deviceRepository.findAll()
                .stream()
                .map(DeviceDTO::new)
                .collect(Collectors.toList());
    }

    public List<DeviceDTO> retrieveByBrand(String brand) {
        return deviceRepository.findByBrand(brand)
                .stream()
                .map(DeviceDTO::new)
                .collect(Collectors.toList());
    }

    public List<DeviceDTO> retrieveByState(StateEnum state) {
        return deviceRepository.findByState(state)
                .stream()
                .map(DeviceDTO::new)
                .collect(Collectors.toList());
    }

    public DeviceDTO update(String deviceId, DeviceDTO deviceDTO){
        DeviceEntity deviceEntity = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException(deviceId));
        if(deviceEntity.getState().equals(StateEnum.IN_USE)){
            if(deviceDTO.getName() != null && !deviceEntity.getName().equals(deviceDTO.getName())){
                throw new DeviceUpdateForbiddenException(ErrorMessage.DEVICE_IN_USE_UPDATE_NAME);
            }
            if(deviceDTO.getBrand() != null && !deviceEntity.getBrand().equals(deviceDTO.getBrand())){
                throw new DeviceUpdateForbiddenException(ErrorMessage.DEVICE_IN_USE_UPDATE_NAME);
            }
        }
        if(deviceDTO.getName() != null){
            deviceEntity.setName(deviceDTO.getName());
        }
        if(deviceDTO.getBrand() != null){
            deviceEntity.setBrand(deviceDTO.getBrand());
        }
        if(deviceDTO.getState() != null){
            deviceEntity.setState(deviceDTO.getState());
        }
        return new DeviceDTO(deviceRepository.save(deviceEntity));
    }

    public void delete(String deviceId){
        DeviceEntity deviceEntity = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException(deviceId));
        if(deviceEntity.getState().equals(StateEnum.IN_USE)){
            throw new DeviceUpdateForbiddenException(ErrorMessage.DEVICE_IN_USE_DELETE);
        }
        deviceRepository.deleteById(deviceId);
    }
}
