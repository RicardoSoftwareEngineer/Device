package com.example.device.entity;

import com.example.device.dto.DeviceDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "Device")
public class DeviceEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String name;
    private String brand;
    private StateEnum state;
    @Column(updatable = false)
    private LocalDateTime creationTime;

    public DeviceEntity() {
    }

    public DeviceEntity(String id, String name, String brand, StateEnum state, LocalDateTime creationTime) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.state = state;
        this.creationTime = creationTime;
    }

    public DeviceEntity(DeviceDTO deviceDTO) {
        this.id = deviceDTO.getId();
        this.name = deviceDTO.getName();
        this.brand = deviceDTO.getBrand();
        this.state = deviceDTO.getState() == null ? StateEnum.AVAILABLE : deviceDTO.getState();
        this.creationTime = LocalDateTime.now();
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
