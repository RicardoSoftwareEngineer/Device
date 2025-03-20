package com.example.device.controller;

import com.example.device.dto.DeviceDTO;
import com.example.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PostMapping(value = "/device/v1")
    public DeviceDTO create(@RequestBody DeviceDTO deviceDTO){
        return deviceService.create(deviceDTO);
    }

    @GetMapping(value = "/device/v1/{id}")
    public DeviceDTO retrieve(@PathVariable String id){
        return deviceService.retrieve(id);
    }

    @GetMapping(value = "/device/v1/list")
    public List<DeviceDTO> list(){
        return deviceService.list();
    }

    @PutMapping(value = "/device/v1/{id}")
    public DeviceDTO update(@PathVariable String id, @RequestBody DeviceDTO deviceDTO){
        return deviceService.update(deviceDTO);
    }

    @DeleteMapping(value = "/device/v1/{id}")
    public void delete(@PathVariable String id){
        deviceService.delete(id);
    }
}
