package com.example.device.controller;

import com.example.device.dto.DeviceDTO;
import com.example.device.entity.StateEnum;
import com.example.device.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Device API", description = "REST API capable of persisting and managing device resources")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PostMapping(value = "/devices/v1")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new device", description = "Adds a new device to the system based on the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceDTO.class))),
    })
    public DeviceDTO create(@RequestBody @Parameter(description = "Device data to be created") DeviceDTO deviceDTO){
        return deviceService.create(deviceDTO);
    }

    @GetMapping(value = "/devices/v1/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve a device by ID", description = "Returns the details of a specific device based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Device not found", content = @Content)
    })
    public DeviceDTO retrieveById(@PathVariable @Parameter(description = "Device ID") String id){
        return deviceService.retrieveById(id);
    }

    @GetMapping(value = "/devices/v1")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all devices", description = "Returns a list of all registered devices.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of devices retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceDTO.class)))
    })
    public PageImpl<DeviceDTO> retrieveAll(Pageable pageable){
        return deviceService.retrieveAll(pageable);
    }

    @GetMapping(value = "/devices/v1/brand/{brand}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List devices by brand", description = "Returns a list of devices filtered by the specified brand.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of devices retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceDTO.class)))
    })
    public PageImpl<DeviceDTO> retrieveByBrand(@PathVariable @Parameter(description = "Brand of the devices") String brand, Pageable pageable){
        return deviceService.retrieveByBrand(brand, pageable);
    }

    @GetMapping(value = "/devices/v1/state/{state}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List devices by state", description = "Returns a list of devices filtered by the specified state.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of devices retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceDTO.class)))
    })
    public PageImpl<DeviceDTO> retrieveByState(@PathVariable @Parameter(description = "State of the devices (e.g., AVAILABLE, IN_USE,  INACTIVE)") StateEnum state, Pageable pageable){
        return deviceService.retrieveByState(state, pageable);
    }

    @PutMapping(value = "/devices/v1/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a device", description = "Updates the data of an existing device based on the provided ID and new data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceDTO.class))),
            @ApiResponse(responseCode = "403", description = "Update forbidden (e.g., device in use)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Device not found", content = @Content)
    })
    public DeviceDTO update(
            @PathVariable @Parameter(description = "ID of the device to be updated") String id,
            @RequestBody @Parameter(description = "New device data") DeviceDTO deviceDTO) {
        return deviceService.update(id, deviceDTO);
    }

    @DeleteMapping(value = "/devices/v1/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a device", description = "Removes a device from the system based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Device deleted successfully", content = @Content),
            @ApiResponse(responseCode = "403", description = "Deletion forbidden (e.g., device in use)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Device not found", content = @Content)
    })
    public void delete(@PathVariable @Parameter(description = "ID of the device to be deleted") String id){
        deviceService.delete(id);
    }
}
