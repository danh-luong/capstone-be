package com.dtvc.api.controller;

import com.dtvc.api.service.CameraService;
import com.dtvc.api.service.GroupCameraService;
import com.dtvc.api.service.LineService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.constants.AppConstants;
import core.domain.Camera;
import core.domain.GroupCamera;
import core.domain.Line;
import core.dto.CameraDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/camera")
public class CameraController {

    @Autowired
    private CameraService cameraService;

    @Autowired
    private GroupCameraService groupCameraService;

    @Autowired
    private LineService lineService;

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

    /**
     * param camera
     * param line
     * if group camera id(groupId) == 0 -> create new group camera
     * example json list:
     * {
     *     "camera": {
     *         "location": "asd",
     *         "connectionUrl": "asfsdfd",
     *         "position": "left",
     *         "groupCamera": {
     *             "groupId": 0,
     *             "groupName": "sdf"
     *         },
     *         "status": "active"
     *     },
     *     "line 1": {
     *         "lineType": "a",
     *         "top": 10,
     *         "left": 101,
     *         "right": 12,
     *         "bottom": 103
     *     },
     *     "line 2": {
     *         "lineType": "ab",
     *         "top": 11,
     *         "left": 102,
     *         "right": 13,
     *         "bottom": 104
     *     },
     *     "line 3": {
     *         "lineType": "abc",
     *         "top": 14,
     *         "left": 104,
     *         "right": 14,
     *         "bottom": 123
     *     }
     * }
     * @return
     */
    @PostMapping(value = "/create")
    public ResponseEntity create(@RequestBody String list) {
        try {
            Camera camera = null;
            List<Line> lines = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            Map<String, Object> map = objectMapper.readValue(list,
                    new TypeReference<Map<String, Object>>() {
                    });
            for (Map.Entry entry : map.entrySet()) {
                if (entry.getKey().equals("camera")) {
                    camera = objectMapper.convertValue(entry.getValue(), Camera.class);
                } else {
                    lines.add(objectMapper.convertValue(entry.getValue(), Line.class));
                }
            }
            int groupId = camera.getGroupCamera().getGroupId();
            if (groupId == 0) {
                GroupCamera groupCamera = groupCameraService.create(camera.getGroupCamera());
                camera.setGroupCamera(groupCamera);
            }
            Camera newCamera = cameraService.save(camera);
            lines = lineService.setCamera(lines, newCamera);
            lineService.create(lines);
        } catch (Exception ex) {
        }
        return new ResponseEntity("200", HttpStatus.OK);
    }

    @GetMapping(value = "/searchGroupByName")
    public Optional<List<GroupCamera>> searchGroupByName(@RequestParam(name = "value", defaultValue = "") String value) {
        Optional<List<GroupCamera>> list = groupCameraService.searchByName(AppConstants.MAX_GROUP_SIZE, value);
        return list;
    }

    @GetMapping(value = "/getAllByStatus")
    public Optional<List<Camera>> getAllByStatus(@RequestParam(name = "status", defaultValue = AppConstants.DEFAULT_STATUS) String status,
                                                 @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE + "") int page,
                                                 @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE + "") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("group_id"));
        Optional<List<Camera>> list = cameraService.getAllByStatus(pageable, status);
        return list;
    }

    @GetMapping(value = "/getAll")
    public Optional<List<Camera>> getAll(@RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE + "") int page,
                                         @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE + "") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("group_id"));
        Optional<List<Camera>> list = cameraService.getAll(pageable);
        return list;
    }

}
