package com.dtvc.api.service;

import core.domain.Camera;
import core.domain.Line;

import java.util.List;

public interface LineService {

    List<Line> setCamera(List<Line> lines, Camera camera);

    void create(List<Line> lines);

    List<Line> getListByCameraId(int cameraId);

    void update(List<Line> lines);
}
