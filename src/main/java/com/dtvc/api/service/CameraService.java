package com.dtvc.api.service;

import core.domain.Camera;
import core.dto.CameraDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CameraService {

    Optional<List<String>> searchLocation(String value);

    List<CameraDTO> convertToDTO(Optional<List<String>> list);

    Optional<List<Camera>> filterByLocationAndStatus(String location, String status, Pageable pageable);

    List<Camera> getByGroup(int groupId);

    int update(Camera camera);

    int create(Camera camera);

    Optional<List<Camera>> getAll(Pageable pageable, String status);

}
