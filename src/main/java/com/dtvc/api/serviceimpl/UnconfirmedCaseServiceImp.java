package com.dtvc.api.serviceimpl;

import com.dtvc.api.repository.UnconfirmedCaseRepository;
import com.dtvc.api.service.UnconfirmedCaseService;
import core.domain.UnconfirmedCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UnconfirmedCaseServiceImp implements UnconfirmedCaseService {

    @Autowired
    private UnconfirmedCaseRepository unconfirmedCaseRepository;

    @Override
    public Optional<List<UnconfirmedCase>> filter(Date fromDate, Date toDate, int violationId, Pageable pageable) {
        Optional<List<UnconfirmedCase>> list = unconfirmedCaseRepository.filter(fromDate, toDate, violationId, pageable);
        return list;
    }

    @Override
    public Optional<List<UnconfirmedCase>> getAll(Pageable pageable) {
        Optional<List<UnconfirmedCase>> list = unconfirmedCaseRepository.getall(pageable);
        return list;
    }

    @Override
    public void delete(int caseId) {
        unconfirmedCaseRepository.deleteById(caseId);
    }

    @Override
    public Optional<UnconfirmedCase> getById(int caseId) {
        Optional<UnconfirmedCase> unconfirmedCase = unconfirmedCaseRepository.findById(caseId);
        return unconfirmedCase;
    }
}
