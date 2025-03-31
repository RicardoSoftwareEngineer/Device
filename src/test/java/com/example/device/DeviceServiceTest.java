package com.example.device;

import com.example.device.dto.DeviceDTO;
import com.example.device.entity.DeviceEntity;
import com.example.device.entity.StateEnum;
import com.example.device.exception.DeviceNotFoundException;
import com.example.device.exception.DeviceUpdateForbiddenException;
import com.example.device.exception.ErrorMessage;
import com.example.device.repository.DeviceRepository;
import com.example.device.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {
    private final DeviceRepository deviceRepository = mock(DeviceRepository.class);
    private final DeviceService deviceService = new DeviceService(deviceRepository);
    private final DeviceEntity sampleEntity = new DeviceEntity("1", "Device1", "Brand1", StateEnum.AVAILABLE, null);
    private final DeviceDTO sampleDTO = new DeviceDTO(sampleEntity);

    @Test
    void create_ShouldReturnDeviceDTO_WhenSuccessful() {
        when(deviceRepository.save(any(DeviceEntity.class))).thenReturn(sampleEntity);

        DeviceDTO result = deviceService.create(sampleDTO);

        assertNotNull(result);
        assertEquals(sampleDTO.getId(), result.getId());
        assertEquals(sampleDTO.getName(), result.getName());
    }

    @Test
    void retrieveById_ShouldReturnDeviceDTO_WhenDeviceExists() {
        when(deviceRepository.findById("1")).thenReturn(Optional.of(sampleEntity));

        DeviceDTO result = deviceService.retrieveById("1");

        assertNotNull(result);
        assertEquals(sampleEntity.getId(), result.getId());
        verify(deviceRepository, times(1)).findById("1");
    }

    @Test
    void retrieveById_ShouldThrowNotFound_WhenDeviceDoesNotExist() {
        when(deviceRepository.findById("999")).thenReturn(Optional.empty());

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class,
                () -> deviceService.retrieveById("999"));

        assertEquals(ErrorMessage.DEVICE_NOT_FOUND.getMessage().formatted("999"), exception.getMessage());
        verify(deviceRepository, times(1)).findById("999");
    }

    @Test
    void retrieveByBrand_ShouldReturnPageOfDeviceDTOs_WhenBrandExists() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<DeviceEntity> page = new PageImpl<>(List.of(sampleEntity), pageable, 1);
        when(deviceRepository.findByBrand("Brand1", pageable)).thenReturn(page);

        Page<DeviceDTO> result = deviceService.retrieveByBrand("Brand1", pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(sampleEntity.getBrand(), result.getContent().get(0).getBrand());
    }

    @Test
    void retrieveByState_ShouldReturnPageOfDeviceDTOs_WhenStateExists() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<DeviceEntity> page = new PageImpl<>(List.of(sampleEntity), pageable, 1);
        when(deviceRepository.findByState(StateEnum.AVAILABLE, pageable)).thenReturn(page);

        Page<DeviceDTO> result = deviceService.retrieveByState(StateEnum.AVAILABLE, pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(sampleEntity.getState(), result.getContent().get(0).getState());
    }

    @Test
    void update_ShouldUpdateDevice_WhenDeviceExistsAndNotInUse() {
        DeviceDTO updateDTO = new DeviceDTO();
        updateDTO.setName("UpdatedDevice");
        updateDTO.setBrand("UpdatedBrand");
        updateDTO.setState(StateEnum.AVAILABLE);

        when(deviceRepository.findById("1")).thenReturn(Optional.of(sampleEntity));
        when(deviceRepository.save(any(DeviceEntity.class))).thenReturn(sampleEntity);

        DeviceDTO result = deviceService.update("1", updateDTO);

        assertEquals("UpdatedDevice", result.getName());
        assertEquals("UpdatedBrand", result.getBrand());
    }

    @Test
    void update_ShouldThrowForbidden_WhenDeviceInUseAndNameChanged() {
        sampleEntity.setState(StateEnum.IN_USE);
        DeviceDTO updateDTO = new DeviceDTO();
        updateDTO.setName("NewName");

        when(deviceRepository.findById("1")).thenReturn(Optional.of(sampleEntity));

        DeviceUpdateForbiddenException exception = assertThrows(DeviceUpdateForbiddenException.class,
                () -> deviceService.update("1", updateDTO));

        assertEquals(ErrorMessage.DEVICE_IN_USE_UPDATE_NAME.getMessage(), exception.getMessage());
    }

    @Test
    void update_ShouldThrowNotFound_WhenDeviceDoesNotExist() {
        DeviceDTO updateDTO = new DeviceDTO();
        when(deviceRepository.findById("999")).thenReturn(Optional.empty());

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class,
                () -> deviceService.update("999", updateDTO));

        assertEquals(ErrorMessage.DEVICE_NOT_FOUND.getMessage().formatted("999"), exception.getMessage());
    }

    @Test
    void delete_ShouldThrowForbidden_WhenDeviceInUse() {
        sampleEntity.setState(StateEnum.IN_USE);
        when(deviceRepository.findById("1")).thenReturn(Optional.of(sampleEntity));

        DeviceUpdateForbiddenException exception = assertThrows(DeviceUpdateForbiddenException.class,
                () -> deviceService.delete("1"));

        assertEquals(ErrorMessage.DEVICE_IN_USE_DELETE.getMessage(), exception.getMessage());
    }

    @Test
    void delete_ShouldThrowNotFound_WhenDeviceDoesNotExist() {
        when(deviceRepository.findById("999")).thenReturn(Optional.empty());

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class,
                () -> deviceService.delete("999"));

        assertEquals(ErrorMessage.DEVICE_NOT_FOUND.getMessage().formatted("999"), exception.getMessage());
    }
}
