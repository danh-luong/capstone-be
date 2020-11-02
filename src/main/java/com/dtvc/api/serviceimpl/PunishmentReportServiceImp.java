package com.dtvc.api.serviceimpl;

import com.dtvc.api.repository.PunishmentReportRepository;
import com.dtvc.api.service.PunishmentReportService;
import core.domain.PunishmentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PunishmentReportServiceImp implements PunishmentReportService {

    @Autowired
    private PunishmentReportRepository punishmentReportRepository;

    @Override
    public Optional<List<PunishmentReport>> filter(Date fromDate, Date toDate, int violationId, Pageable pageable) {
        Optional<List<PunishmentReport>> list = punishmentReportRepository.filter(fromDate, toDate, violationId, pageable);
        return list;
    }

    @Override
    public Optional<List<PunishmentReport>> getAll(Pageable pageable) {
        Optional<List<PunishmentReport>> list = punishmentReportRepository.getAll(pageable);
        return list;
    }

    @Override
    public int create(PunishmentReport punishmentReport) {
        int row = punishmentReportRepository.create(punishmentReport.getCaseId(), punishmentReport.getTrainedStatus(),
                punishmentReport.getCreatedDate(), punishmentReport.getImage().getImageId(), punishmentReport.getLicensePlate(),
                punishmentReport.getLocation(), punishmentReport.getViolationType().getViolationId(), punishmentReport.getReportUrl());
        return row;
    }

    @Override
    public int getCountOfStatus(String status, int violationId) {
        int count = punishmentReportRepository.getCountOfStatus(status, violationId);
        return count;
    }
}
