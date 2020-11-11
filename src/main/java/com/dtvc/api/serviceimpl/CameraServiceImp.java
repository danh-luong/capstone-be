package com.dtvc.api.serviceimpl;

import com.dtvc.api.repository.CameraRepository;
import com.dtvc.api.service.CameraService;
import core.constants.AppConstants;
import core.domain.Camera;
import core.dto.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CameraServiceImp implements CameraService {

    @Autowired
    private CameraRepository cameraRepository;

    @Override
    public Optional<List<String>> searchLocation(String value) {
        Optional<List<String>> list = cameraRepository.searchLocation(value);
        return list;
    }

    @Override
    public List<LocationDTO> convertToDTO(Optional<List<String>> list) {
        List<LocationDTO> cameras = new ArrayList<>();
        if (list.isPresent()) {
            for (String location : list.get()) {
                LocationDTO camera = new LocationDTO();
                camera.setLocation(location);
                cameras.add(camera);
            }
        }
        return cameras;
    }

    @Override
    public Optional<List<Camera>> filterByLocationAndStatus(String location, String status, Pageable pageable) {
        Optional<List<Camera>> list = null;
        if (status.equals(AppConstants.DEFAULT_STATUS)) {
            list = cameraRepository.filterByLocation(location, pageable);
        } else {
            list = cameraRepository.filterByLocationAndStatus(location, status, pageable);
        }
        return list;
    }

    @Override
    public List<Camera> getByGroup(int groupId) {
        List<Camera> list = cameraRepository.getByGroup(groupId);
        return list;
    }

    @Override
    public int update(Camera camera) {
        int row = cameraRepository.update(camera.getCameraId(), camera.getConnectionUrl(), camera.getGroupCamera().getGroupId(),
                camera.getLocation(), camera.getPosition(), camera.getStatus());
        return row;
    }

    @Override
    public int create(Camera camera) {
        int row = cameraRepository.create(camera.getStatus(), camera.getPosition(), camera.getLocation(),
                camera.getGroupCamera().getGroupId(), camera.getConnectionUrl());
        return row;
    }

    @Override
    public Camera save(Camera camera) {
        Camera newCamera = cameraRepository.saveAndFlush(camera);
        return newCamera;
    }

    @Override
    public Optional<List<Camera>> getAllByStatus(Pageable pageable, String status) {
        Optional<List<Camera>> list = null;
        if (status.equals(AppConstants.DEFAULT_STATUS)) {
            list = cameraRepository.getAll(pageable);
        } else {
            list = cameraRepository.getAllByStatus(pageable, status);
        }
        return list;
    }

    @Override
    public Optional<List<Camera>> getAll(Pageable pageable) {
        Optional<List<Camera>> list = cameraRepository.getAll(pageable);
        return list;
    }

    @Override
    public Camera getById(int id) {
        Camera camera = cameraRepository.getById(id);
        return camera;
    }

    @Override
    public int updateStatus(int cameraId, String status) {
        int row = cameraRepository.updateStatus(cameraId, status);
        return row;
    }

}
