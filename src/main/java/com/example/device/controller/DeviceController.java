package com.example.device.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class DeviceController {

    @PostMapping(value = "/device/v1")
    public Object create(@RequestBody Object deviceDTO){
        return null;
    }

    @GetMapping(value = "/device/v1/{id}")
    public Object retrieve(@RequestBody Object deviceDTO){
        return null;
    }

    @GetMapping(value = "/device/v1/list")
    public Object list(){
        return null;
    }

    @PutMapping(value = "/device/v1/{id}")
    public Object update(){
        return null;
    }

    @DeleteMapping(value = "/device/v1/{id}")
    public Object delete(){
        return null;
    }
}
