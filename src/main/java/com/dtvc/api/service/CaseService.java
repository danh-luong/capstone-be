package com.dtvc.api.service;

import core.domain.Case;
import core.domain.PunishmentReport;
import core.domain.RejectedCase;
import core.domain.UnconfirmedCase;
import core.dto.CameraDTO;
import core.dto.CaseDTO;

import java.util.List;
import java.util.Optional;

public interface CaseService {

//    List<Case> convertFromRejectedCase(Optional<List<RejectedCase>> rejectedCases);
//
//    List<Case> convertFromUnconfirmedCase(Optional<List<UnconfirmedCase>> unconfirmedCases);
//
//    List<Case> convertFromApprovedCase(Optional<List<PunishmentReport>> punishmentReports);

    List<CaseDTO> setCaseType(List<CaseDTO> caseDTOS, String caseType);
}
