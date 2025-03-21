package com.example.device.repository;

import com.example.device.entity.DeviceEntity;
import com.example.device.entity.StateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository <DeviceEntity, String> {
    List<DeviceEntity> findByBrand(String brand);
    List<DeviceEntity> findByState(StateEnum state);
}
