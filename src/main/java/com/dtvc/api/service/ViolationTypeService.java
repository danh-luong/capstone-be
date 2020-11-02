package com.dtvc.api.service;

import core.domain.ViolationType;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ViolationTypeService {

    List<ViolationType> getAll(Sort sort);
}
