package com.dtvc.api.service;

import core.domain.Camera;
import core.dto.LocationDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CameraService {

    Optional<List<String>> searchLocation(String value);

    List<LocationDTO> convertToDTO(Optional<List<String>> list);

    Optional<List<Camera>> filterByLocationAndStatus(String location, String status, Pageable pageable);

    List<Camera> getByGroup(int groupId);

    int update(Camera camera);

    int create(Camera camera);

    Camera save(Camera camera);

    Optional<List<Camera>> getAllByStatus(Pageable pageable, String status);

    Optional<List<Camera>> getAll(Pageable pageable);

    Camera getById(int id);

    int updateStatus(int cameraId, String status);

}
