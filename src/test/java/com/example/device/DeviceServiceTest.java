package com.example.device;

import com.example.device.dto.DeviceDTO;
import com.example.device.entity.DeviceEntity;
import com.example.device.entity.StateEnum;
import com.example.device.repository.DeviceRepository;
import com.example.device.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    private DeviceEntity sampleEntity;
    private DeviceDTO sampleDTO;

    @BeforeEach
    void setUp() {
        sampleEntity = new DeviceEntity();
        sampleEntity.setId("1");
        sampleEntity.setName("Device1");
        sampleEntity.setBrand("Brand1");
        sampleEntity.setState(StateEnum.AVAILABLE);
        sampleEntity.setCreationTime(LocalDateTime.now());

        sampleDTO = new DeviceDTO(sampleEntity);
    }

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

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> deviceService.retrieveById("999"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Device not found", exception.getReason());
        verify(deviceRepository, times(1)).findById("999");
    }

    @Test
    void retrieveByBrand_ShouldReturnListOfDeviceDTOs_WhenBrandExists() {
        when(deviceRepository.findByBrand("Brand1")).thenReturn(List.of(sampleEntity));

        List<DeviceDTO> result = deviceService.retrieveByBrand("Brand1");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(sampleEntity.getBrand(), result.get(0).getBrand());
    }

    @Test
    void retrieveByState_ShouldReturnListOfDeviceDTOs_WhenStateExists() {
        when(deviceRepository.findByState(StateEnum.AVAILABLE)).thenReturn(List.of(sampleEntity));

        List<DeviceDTO> result = deviceService.retrieveByState(StateEnum.AVAILABLE);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(sampleEntity.getState(), result.get(0).getState());
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

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> deviceService.update("1", updateDTO));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("Name and brand properties cannot be updated if the device is in use", exception.getReason());
    }

    @Test
    void update_ShouldThrowNotFound_WhenDeviceDoesNotExist() {
        DeviceDTO updateDTO = new DeviceDTO();
        when(deviceRepository.findById("999")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> deviceService.update("999", updateDTO));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Device not found", exception.getReason());
    }

    @Test
    void delete_ShouldThrowForbidden_WhenDeviceInUse() {
        sampleEntity.setState(StateEnum.IN_USE);
        when(deviceRepository.findById("1")).thenReturn(Optional.of(sampleEntity));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> deviceService.delete("1"));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("In use devices cannot be deleted", exception.getReason());
    }

    @Test
    void delete_ShouldThrowNotFound_WhenDeviceDoesNotExist() {
        when(deviceRepository.findById("999")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> deviceService.delete("999"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Device not found", exception.getReason());
    }
}
