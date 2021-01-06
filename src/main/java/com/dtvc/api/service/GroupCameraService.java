package com.dtvc.api.service;

import core.domain.GroupCamera;

import java.util.List;
import java.util.Optional;

public interface GroupCameraService {

    List<GroupCamera> searchByLocation(String location);

    Optional<List<GroupCamera>> loadAvailable(int count);

    GroupCamera create(GroupCamera groupCamera);

    Optional<List<GroupCamera>> searchByName(int count, String name);

    boolean checkGroup(String name);
}
