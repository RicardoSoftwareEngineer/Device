package com.example.device.dto;

import com.example.device.entity.DeviceEntity;
import com.example.device.entity.StateEnum;

import java.time.LocalDateTime;

public class DeviceDTO {
    private String id;
    private String name;
    private String brand;
    private StateEnum state;
    private LocalDateTime creationTime;

    public DeviceDTO() {
    }

    public DeviceDTO(DeviceEntity deviceEntity) {
        this.id = deviceEntity.getId();
        this.name = deviceEntity.getName();
        this.brand = deviceEntity.getBrand();
        this.state = deviceEntity.getState();
        this.creationTime = deviceEntity.getCreationTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
