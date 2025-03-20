package com.example.device.controller;

import com.example.device.dto.DeviceDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeviceController {

    @PostMapping(value = "/device/v1")
    public DeviceDTO create(@RequestBody DeviceDTO deviceDTO){
        return null;
    }

    @GetMapping(value = "/device/v1/{id}")
    public DeviceDTO retrieve(@RequestBody DeviceDTO deviceDTO){
        return null;
    }

    @GetMapping(value = "/device/v1/list")
    public List<DeviceDTO> list(){
        return null;
    }

    @PutMapping(value = "/device/v1/{id}")
    public DeviceDTO update(@RequestBody DeviceDTO deviceDTO){
        return null;
    }

    @DeleteMapping(value = "/device/v1/{id}")
    public DeviceDTO delete(){
        return null;
    }
}
