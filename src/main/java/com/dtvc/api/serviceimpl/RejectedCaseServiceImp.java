package com.dtvc.api.serviceimpl;

import com.dtvc.api.repository.RejectedCaseRepository;
import com.dtvc.api.service.RejectedCaseService;
import core.domain.RejectedCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RejectedCaseServiceImp implements RejectedCaseService {

    @Autowired
    private RejectedCaseRepository rejectedCaseRepository;

    @Override
    public Optional<List<RejectedCase>> filter(Date fromDate, Date toDate, int violationId, Pageable pageable) {
        Optional<List<RejectedCase>> list = rejectedCaseRepository.filter(fromDate, toDate, violationId, pageable);
        return list;
    }

    @Override
    public Optional<List<RejectedCase>> getAll(Pageable pageable) {
        Optional<List<RejectedCase>> list = rejectedCaseRepository.getAll(pageable);
        return list;
    }

    @Override
    public int create(RejectedCase rejectedCase) {
        int row = rejectedCaseRepository.create(rejectedCase.getCaseId(), rejectedCase.getTrainedStatus(),
                rejectedCase.getCreatedDate(), rejectedCase.getImage().getImageId(), rejectedCase.getLicensePlate(),
                rejectedCase.getLocation(), rejectedCase.getViolationType().getViolationId());
        return row;
    }

    @Override
    public int getCountOfStatus(String status, int violationId) {
        int count = rejectedCaseRepository.getCountOfStatus(status, violationId);
        return count;
    }
}
