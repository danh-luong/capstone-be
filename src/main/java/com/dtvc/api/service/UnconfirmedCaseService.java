package com.dtvc.api.service;

import core.domain.UnconfirmedCase;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UnconfirmedCaseService {

    Optional<List<UnconfirmedCase>> filter(Date fromDate, Date toDate, int violationId, Pageable pageable);

    Optional<List<UnconfirmedCase>> getAll(Pageable pageable);

    void delete(int caseId);

    Optional<UnconfirmedCase> getById(int caseId);

    int update(int caseId, String licensePlate);
}
