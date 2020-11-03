package com.dtvc.api.controller;

import com.dtvc.api.service.CameraService;
import com.dtvc.api.service.GroupCameraService;
import core.constants.AppConstants;
import core.domain.Camera;
import core.domain.GroupCamera;
import core.dto.CameraDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/camera")
public class CameraController {

    @Autowired
    private CameraService cameraService;

    @Autowired
    private GroupCameraService groupCameraService;

    @GetMapping(value = "/searchLocation")
    public List<CameraDTO> searchLocation(@RequestParam(name = "value", defaultValue = "") String value) {
        Optional<List<String>> list = cameraService.searchLocation(value);
        List<CameraDTO> cameras = cameraService.convertToDTO(list);
        return cameras;
    }

    @GetMapping(value = "/filterByLocationAndStatus")
    public Optional<List<Camera>> filterByLocationAndStatus(@RequestParam(name = "location", defaultValue = "") String location,
                                                            @RequestParam(name = "status", defaultValue = AppConstants.DEFAULT_STATUS) String status,
                                                            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE + "") int page,
                                                            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE + "") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("group_id"));
        Optional<List<Camera>> list = cameraService.filterByLocationAndStatus(location, status, pageable);
        return list;
    }

    @GetMapping(value = "/getByGroup")
    public List<Camera> getByGroup(@RequestParam(name = "groupId") int groupId) {
        List<Camera> list = cameraService.getByGroup(groupId);
        return list;
    }

    @GetMapping(value = "/searchGroupByLocation")
    public List<GroupCamera> searchGroupByLocation(@RequestParam(name = "location") String location) {
        List<GroupCamera> list = groupCameraService.searchByLocation(location);
        return list;
    }

    @GetMapping(value = "/loadAvailableGroup")
    public Optional<List<GroupCamera>> loadAvailableGroup() {
        Optional<List<GroupCamera>> list = groupCameraService.loadAvailable(AppConstants.MAX_GROUP_SIZE);
        return list;
    }

    @PostMapping(value = "/update")
    public ResponseEntity update(@RequestBody Camera camera) {
        int groupId = camera.getGroupCamera().getGroupId();
        if (groupId == 0) {
            GroupCamera groupCamera = groupCameraService.create(camera.getGroupCamera());
            camera.setGroupCamera(groupCamera);
        }
        int row = cameraService.update(camera);
        if (row < 1) {
            return new ResponseEntity("400", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("200", HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity create(@RequestBody Camera camera) {
        int groupId = camera.getGroupCamera().getGroupId();
        if (groupId == 0) {
            GroupCamera groupCamera = groupCameraService.create(camera.getGroupCamera());
            camera.setGroupCamera(groupCamera);
        }
        int row = cameraService.create(camera);
        if (row < 1) {
            return new ResponseEntity("400", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("200", HttpStatus.OK);
    }

    @GetMapping(value = "/searchGroupByName")
    public Optional<List<GroupCamera>> searchGroupByName(@RequestParam(name = "value", defaultValue = "") String value) {
        Optional<List<GroupCamera>> list = groupCameraService.searchByName(AppConstants.MAX_GROUP_SIZE, value);
        return list;
    }

    @GetMapping(value = "/getAll")
    public Optional<List<Camera>> getAll(@RequestParam(name = "status", defaultValue = AppConstants.DEFAULT_STATUS) String status,
                                         @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE + "") int page,
                                         @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE + "") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("group_id"));
        Optional<List<Camera>> list = cameraService.getAll(pageable, status);
        return list;
    }

}
