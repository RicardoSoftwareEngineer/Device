package com.example.device.repository;

import com.example.device.entity.DeviceEntity;
import com.example.device.entity.StateEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository <DeviceEntity, String> {
    Page<DeviceEntity> findByBrand(String brand, Pageable pageable);
    Page<DeviceEntity> findByState(StateEnum state, Pageable pageable);
}
