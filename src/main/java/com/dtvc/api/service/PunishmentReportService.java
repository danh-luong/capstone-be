package com.dtvc.api.service;

import core.domain.PunishmentReport;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PunishmentReportService {

    Optional<List<PunishmentReport>> filter(Date fromDate, Date toDate, int violationId, Pageable pageable);

    Optional<List<PunishmentReport>> getAll(Pageable pageable);

    int create(PunishmentReport punishmentReport);

    int getCountOfStatus(String status, int violationId);

    Optional<PunishmentReport> getById(int caseId);
}
