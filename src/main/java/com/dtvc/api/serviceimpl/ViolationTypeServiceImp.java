package com.dtvc.api.serviceimpl;

import com.dtvc.api.repository.ViolationTypeRepository;
import com.dtvc.api.service.ViolationTypeService;
import core.domain.ViolationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViolationTypeServiceImp implements ViolationTypeService {

    @Autowired
    private ViolationTypeRepository violationTypeRepository;

    @Override
    public List<ViolationType> getAll(Sort sort) {
        List<ViolationType> list = violationTypeRepository.findAll(sort);
        return list;
    }
}
