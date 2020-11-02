package com.dtvc.api.service;

import core.domain.Case;
import core.domain.PunishmentReport;
import core.domain.RejectedCase;
import core.domain.UnconfirmedCase;

import java.util.List;
import java.util.Optional;

public interface CaseService {

    List<Case> convertFromRejectedCase(Optional<List<RejectedCase>> rejectedCases);

    List<Case> convertFromUnconfirmedCase(Optional<List<UnconfirmedCase>> unconfirmedCases);

    List<Case> convertFromApprovedCase(Optional<List<PunishmentReport>> punishmentReports);
}
