package com.dtvc.api.serviceimpl;

import com.dtvc.api.repository.CameraRepository;
import com.dtvc.api.service.CameraService;
import core.domain.Camera;
import core.dto.CameraDTO;
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
    public List<CameraDTO> convertToDTO(Optional<List<String>> list) {
        List<CameraDTO> cameras = new ArrayList<>();
        if (list.isPresent()) {
            for (String location : list.get()) {
                CameraDTO camera = new CameraDTO();
                camera.setLocation(location);
                cameras.add(camera);
            }
        }
        return cameras;
    }

    @Override
    public Optional<List<Camera>> filterByLocationAndStatus(String location, String status, Pageable pageable) {
        Optional<List<Camera>> list = cameraRepository.filterByLocationAndStatus(location, status, pageable);
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
    public Optional<List<Camera>> getAll(Pageable pageable, String status) {
        Optional<List<Camera>> list = cameraRepository.getAll(pageable, status);
        return list;
    }

}
