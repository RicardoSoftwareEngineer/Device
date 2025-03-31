package com.example.device.service;

import com.example.device.dto.DeviceDTO;
import com.example.device.entity.DeviceEntity;
import com.example.device.entity.StateEnum;
import com.example.device.exception.DeviceNotFoundException;
import com.example.device.exception.DeviceUpdateForbiddenException;
import com.example.device.exception.ErrorMessage;
import com.example.device.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceDTO create(DeviceDTO deviceDTO){
        DeviceEntity deviceEntity = deviceRepository.save(new DeviceEntity(deviceDTO));
        return new DeviceDTO(deviceEntity);
    }

    public DeviceDTO retrieveById(String deviceId){
        DeviceEntity deviceEntity = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException(deviceId));
        return new DeviceDTO(deviceEntity);
    }

    public PageImpl<DeviceDTO> retrieveAll(Pageable pageable) {
        Page<DeviceEntity> page = deviceRepository.findAll(pageable);
        List<DeviceDTO> content = page.getContent()
                .stream()
                .map(DeviceDTO::new)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    public PageImpl<DeviceDTO> retrieveByBrand(String brand, Pageable pageable) {
        Page<DeviceEntity> page = deviceRepository.findByBrand(brand, pageable);
        List<DeviceDTO> content = page.getContent()
                .stream()
                .map(DeviceDTO::new)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    public PageImpl<DeviceDTO> retrieveByState(StateEnum state, Pageable pageable) {
        Page<DeviceEntity> page = deviceRepository.findByState(state, pageable);
        List<DeviceDTO> content = page.getContent()
                .stream()
                .map(DeviceDTO::new)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, page.getTotalElements());
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
