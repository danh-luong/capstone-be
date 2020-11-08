package com.dtvc.api.serviceimpl;

import com.dtvc.api.repository.LineRepository;
import com.dtvc.api.service.LineService;
import core.domain.Camera;
import core.domain.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineServiceImp implements LineService {

    @Autowired
    private LineRepository lineRepository;

    @Override
    public List<Line> setCamera(List<Line> lines, Camera camera) {
        for (Line line : lines) {
            line.setCamera(camera);
        }
        return lines;
    }

    @Override
    public void create(List<Line> lines) {
        for (Line line : lines) {
            lineRepository.save(line);
        }
    }
}
