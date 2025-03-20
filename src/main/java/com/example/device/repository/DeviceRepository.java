package com.example.device.repository;

import com.example.device.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository <DeviceEntity, String> {
    
}
