package com.dtvc.api.serviceimpl;

import com.dtvc.api.repository.GroupCameraRepository;
import com.dtvc.api.service.GroupCameraService;
import core.domain.GroupCamera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupCameraServiceImp implements GroupCameraService {

    @Autowired
    private GroupCameraRepository groupCameraRepository;

    @Override
    public List<GroupCamera> searchByLocation(String location) {
        List<GroupCamera> list = groupCameraRepository.searchByLocation(location);
        return list;
    }

    @Override
    public Optional<List<GroupCamera>> loadAvailable(int count) {
        Optional<List<GroupCamera>> list = groupCameraRepository.loadAvailable(count);
        return list;
    }

    @Override
    public GroupCamera create(GroupCamera groupCamera) {
        GroupCamera result = groupCameraRepository.saveAndFlush(new GroupCamera(groupCamera.getGroupName()));
        return result;
    }

    @Override
    public Optional<List<GroupCamera>> searchByName(int count, String name) {
        Optional<List<GroupCamera>> list = groupCameraRepository.searchByName(count, name);
        return list;
    }
}
