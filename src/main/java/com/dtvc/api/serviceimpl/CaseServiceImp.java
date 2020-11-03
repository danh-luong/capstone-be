package com.dtvc.api.serviceimpl;

import com.dtvc.api.service.CaseService;
import core.domain.Case;
import core.domain.PunishmentReport;
import core.domain.RejectedCase;
import core.domain.UnconfirmedCase;
import core.dto.CaseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CaseServiceImp implements CaseService {

    @Override
    public List<CaseDTO> setCaseType(List<CaseDTO> list, String caseType) {
        for(CaseDTO caseDTO: list){
            caseDTO.setCaseType(caseType);
        }
        return list;
    }

//    @Override
//    public List<Case> convertFromRejectedCase(Optional<List<RejectedCase>> rejectedCases) {
//        List<Case> list = new ArrayList<>();
//        if (rejectedCases.isPresent()) {
//            for (RejectedCase rejectedCase : rejectedCases.get()) {
//                Case newCase = rejectedCase;
//                list.add(newCase);
//            }
//        }
//        return list;
//    }
//
//    @Override
//    public List<Case> convertFromUnconfirmedCase(Optional<List<UnconfirmedCase>> unconfirmedCases) {
//        List<Case> list = new ArrayList<>();
//        if (unconfirmedCases.isPresent()) {
//            for (UnconfirmedCase unconfirmedCase : unconfirmedCases.get()) {
//                Case newCase = unconfirmedCase;
//                list.add(newCase);
//            }
//        }
//        return list;
//    }
//
//    @Override
//    public List<Case> convertFromApprovedCase(Optional<List<PunishmentReport>> punishmentReports) {
//        List<Case> list = new ArrayList<>();
//        if (punishmentReports.isPresent()) {
//            for (PunishmentReport punishmentReport : punishmentReports.get()) {
//                Case newCase = punishmentReport;
//                list.add(newCase);
//            }
//        }
//        return list;
//    }
}
