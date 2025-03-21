package com.example.device.controller;

import com.example.device.dto.DeviceDTO;
import com.example.device.entity.StateEnum;
import com.example.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PostMapping(value = "/device/v1")
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceDTO create(@RequestBody DeviceDTO deviceDTO){
        return deviceService.create(deviceDTO);
    }

    @GetMapping(value = "/device/v1/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceDTO retrieveById(@PathVariable String id){
        return deviceService.retrieveById(id);
    }

    @GetMapping(value = "/device/v1/retrieveAll")
    @ResponseStatus(HttpStatus.OK)
    public List<DeviceDTO> retrieveAll(){
        return deviceService.retrieveAll();
    }

    @GetMapping(value = "/device/v1/retrieveByBrand/{brand}")
    @ResponseStatus(HttpStatus.OK)
    public List<DeviceDTO> retrieveByBrand(@PathVariable String brand){
        return deviceService.retrieveByBrand(brand);
    }

    @GetMapping(value = "/device/v1/retrieveByState/{state}")
    @ResponseStatus(HttpStatus.OK)
    public List<DeviceDTO> retrieveByState(@PathVariable StateEnum state){
        return deviceService.retrieveByState(state);
    }

    @PutMapping(value = "/device/v1/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceDTO update(@PathVariable String id, @RequestBody DeviceDTO deviceDTO){
        return deviceService.update(id, deviceDTO);
    }

    @DeleteMapping(value = "/device/v1/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id){
        deviceService.delete(id);
    }
}
