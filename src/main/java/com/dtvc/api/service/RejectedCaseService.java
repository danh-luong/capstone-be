package com.dtvc.api.service;

import core.domain.RejectedCase;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RejectedCaseService {

    Optional<List<RejectedCase>> filter(Date fromDate, Date toDate, int violationId, Pageable pageable);

    Optional<List<RejectedCase>> getAll(Pageable pageable);

    int create(RejectedCase rejectedCase);

    int getCountOfStatus(String status, int violationId);

    Optional<RejectedCase> getById(int caseId);
}
