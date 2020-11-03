package com.dtvc.api.controller;

import com.dtvc.api.mapper.ObjectMapper;
import com.dtvc.api.service.*;
import core.constants.AppConstants;
import core.domain.UnconfirmedCase;
import core.domain.PunishmentReport;
import core.domain.RejectedCase;
import core.domain.ViolationType;
import core.domain.Case;
import core.dto.CaseDTO;
import core.dto.CountDTO;
import core.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/case")
public class CaseController {

    @Autowired
    private ViolationTypeService violationTypeService;

    @Autowired
    private RejectedCaseService rejectedCaseService;

    @Autowired
    private CaseService caseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UnconfirmedCaseService unconfirmedCaseService;

    @Autowired
    private PunishmentReportService punishmentReportService;

    @GetMapping(value = "/getViolationType")
    public List<ViolationType> getViolationType() {
        List<ViolationType> list = violationTypeService.getAll(Sort.by("name"));
        return list;
    }

    @GetMapping(value = "/filter")
    public List<CaseDTO> filter(@RequestParam(name = "fromDate", defaultValue = "") String fDate,
                                @RequestParam(name = "toDate", defaultValue = "") String tDate,
                                @RequestParam(name = "violationId", defaultValue = AppConstants.DEFAULT_VIOLATION_TYPE + "") int violationId,
                                @RequestParam(name = "caseType", defaultValue = AppConstants.DEFAULT_CASE) String caseType,
                                @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE + "") int page,
                                @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE + "") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("created_date").descending());
        List<CaseDTO> list = null;
        java.util.Date today = new java.util.Date(System.currentTimeMillis());
        Date fromDate = new Date(today.getTime());
        Date toDate = fromDate;
        try {
            if (!fDate.isEmpty()) {
                fromDate = DateTime.convertStringToDate(fDate);
            }
            if (!tDate.isEmpty()) {
                toDate = DateTime.convertStringToDate(tDate);
            }
            if (caseType.equalsIgnoreCase(AppConstants.REJECTED_CASE)) {
                Optional<List<RejectedCase>> rejectedCases = rejectedCaseService.filter(
                        fromDate, toDate, violationId, pageable);
//            list = caseService.convertFromRejectedCase(rejectedCases);
                if (rejectedCases.isPresent()) {
                    list = objectMapper.convertToListDTO(rejectedCases.get(), CaseDTO.class);
                }
            } else if (caseType.equalsIgnoreCase(AppConstants.DEFAULT_CASE)) {
                Optional<List<UnconfirmedCase>> unconfirmedCases = unconfirmedCaseService.filter(
                        fromDate, toDate, violationId, pageable);
//            list = caseService.convertFromUnconfirmedCase(unconfirmedCases);
                if (unconfirmedCases.isPresent()) {
                    list = objectMapper.convertToListDTO(unconfirmedCases.get(), CaseDTO.class);
                }
            } else {
                Optional<List<PunishmentReport>> punishmentReports = punishmentReportService.filter(
                        fromDate, toDate, violationId, pageable);
//            list = caseService.convertFromApprovedCase(punishmentReports);
                if (punishmentReports.isPresent()) {
                    list = objectMapper.convertToListDTO(punishmentReports.get(), CaseDTO.class);
                }
            }
        } catch (Exception ex) {

        }
        return list;
    }

    @GetMapping(value = "/getAll")
    public List<CaseDTO> getAll(@RequestParam(name = "caseType", defaultValue = AppConstants.DEFAULT_CASE) String caseType,
                                @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE + "") int page,
                                @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE + "") int pageSize) {
        List<CaseDTO> list = null;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("created_date").descending());
        if (caseType.equalsIgnoreCase(AppConstants.REJECTED_CASE)) {
            Optional<List<RejectedCase>> rejectedCases = rejectedCaseService.getAll(pageable);
            if (rejectedCases.isPresent()) {
                list = objectMapper.convertToListDTO(rejectedCases.get(), CaseDTO.class);
                list = caseService.setCaseType(list, caseType);
            }
        } else if (caseType.equalsIgnoreCase(AppConstants.DEFAULT_CASE)) {
            Optional<List<UnconfirmedCase>> unconfirmedCases = unconfirmedCaseService.getAll(pageable);
            if (unconfirmedCases.isPresent()) {
                list = objectMapper.convertToListDTO(unconfirmedCases.get(), CaseDTO.class);
                list = caseService.setCaseType(list, caseType);
            }
        } else {
            Optional<List<PunishmentReport>> punishmentReports = punishmentReportService.getAll(pageable);
            if (punishmentReports.isPresent()) {
                list = objectMapper.convertToListDTO(punishmentReports.get(), CaseDTO.class);
                list = caseService.setCaseType(list, caseType);
            }
        }
        return list;
    }

    @PostMapping(value = "/reject")
    public ResponseEntity reject(@RequestParam(name = "caseId") int caseId) {
        Optional<UnconfirmedCase> unconfirmedCase = unconfirmedCaseService.getById(caseId);
        CaseDTO caseDTO = (CaseDTO) objectMapper.convertToDTO(unconfirmedCase.get(), CaseDTO.class);
        RejectedCase rejectedCase = (RejectedCase) objectMapper.convertToEntity(caseDTO, RejectedCase.class);
        unconfirmedCaseService.delete(caseId);
        rejectedCase.setTrainedStatus(AppConstants.NOT_TRAINED_STATUS);
        int row = rejectedCaseService.create(rejectedCase);
        if (row < 1) {
            return new ResponseEntity("400", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("200", HttpStatus.OK);
    }

    @Autowired
    private PdfGenerator pdfGenerator;

    @PostMapping(value = "/approve")
    public ResponseEntity approve(@RequestParam(name = "caseId") int caseId) {
        Optional<UnconfirmedCase> unconfirmedCase = unconfirmedCaseService.getById(caseId);
        CaseDTO caseDTO = (CaseDTO) objectMapper.convertToDTO(unconfirmedCase.get(), CaseDTO.class);
        PunishmentReport punishmentReport = (PunishmentReport) objectMapper.convertToEntity(caseDTO, PunishmentReport.class);
        unconfirmedCaseService.delete(caseId);
        punishmentReport.setTrainedStatus(AppConstants.NOT_TRAINED_STATUS);
        String urlOfPdf = pdfGenerator.generatePdf(punishmentReport);
        punishmentReport.setReportUrl(urlOfPdf);
        int row = punishmentReportService.create(punishmentReport);

        if (row < 1) {
            return new ResponseEntity("400", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("200", HttpStatus.OK);
    }

    @GetMapping(value = "/getCountOfStatus")
    public CountDTO getCountOfStatus(@RequestParam(name = "status") String status,
                                     @RequestParam(name = "violationId", defaultValue = AppConstants.DEFAULT_VIOLATION_TYPE + "") int violationId,
                                     @RequestParam(name = "caseType", defaultValue = AppConstants.APPROVED_CASE) String caseType) {
        CountDTO countDTO = new CountDTO();
        if (caseType.equals(AppConstants.REJECTED_CASE)) {
            int count = rejectedCaseService.getCountOfStatus(status, violationId);
            countDTO.setCount(count);
        } else {
            int count = punishmentReportService.getCountOfStatus(status, violationId);
            countDTO.setCount(count);
        }
        return countDTO;
    }

    @GetMapping(value = "/getDetail")
    public CaseDTO geDetail(@RequestParam(name = "caseType", defaultValue = AppConstants.APPROVED_CASE) String caseType,
                            @RequestParam(name = "caseId") int caseId) {
        CaseDTO caseDTO = null;
        if (caseType.equals(AppConstants.REJECTED_CASE)) {
            Optional<RejectedCase> rejectedCase = rejectedCaseService.getById(caseId);
            if (rejectedCase.isPresent()) {
                caseDTO = (CaseDTO) objectMapper.convertToDTO(rejectedCase.get(), CaseDTO.class);
            }
        } else if (caseType.equals(AppConstants.DEFAULT_CASE)) {
            Optional<UnconfirmedCase> unconfirmedCase = unconfirmedCaseService.getById(caseId);
            if (unconfirmedCase.isPresent()) {
                caseDTO = (CaseDTO) objectMapper.convertToDTO(unconfirmedCase.get(), CaseDTO.class);
            }
        } else {
            Optional<PunishmentReport> punishmentReport = punishmentReportService.getById(caseId);
            if (punishmentReport.isPresent()) {
                caseDTO = (CaseDTO) objectMapper.convertToDTO(punishmentReport.get(), CaseDTO.class);
            }
        }
        return caseDTO;
    }
}
