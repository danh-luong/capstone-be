package com.dtvc.api.repository;

import core.domain.PunishmentReport;
import core.domain.RejectedCase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PunishmentReportRepository extends JpaRepository<PunishmentReport, Integer>, PagingAndSortingRepository<PunishmentReport, Integer> {

    @Query(value = "select *" +
            " from punishment_reports" +
            " where created_date between :fromDate and :toDate" +
            " and violation_id = :violationId",
            nativeQuery = true)
    Optional<List<PunishmentReport>> filter(@Param("fromDate") Date fromDate,
                                            @Param("toDate") Date toDate,
                                            @Param("violationId") int violationId,
                                            Pageable pageable);

    @Query(value = "select *" +
            " from punishment_reports",
            nativeQuery = true)
    Optional<List<PunishmentReport>> getAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "insert into punishment_reports" +
            "(case_id, trained_status, created_date, image_id, license_plate, location, violation_id, report_url)" +
            " values(:caseId, :trainedStatus, :createdDate, :imageId, :licensePlate, :location, :violationId, :reportUrl)",
            nativeQuery = true)
    int create(@Param("caseId") int caseId,
               @Param("trainedStatus") String trainedStatus,
               @Param("createdDate") Date createdDate,
               @Param("imageId") int imageId,
               @Param("licensePlate") String licensePlate,
               @Param("location") String location,
               @Param("violationId") int violationId,
               @Param("reportUrl") String reportUrl);

    @Query(value = "select count(trained_status)" +
            " from punishment_reports" +
            " where trained_status = :status and violation_id = :violationId",
            nativeQuery = true)
    int getCountOfStatus(@Param("status") String status,
                         @Param("violationId") int violationId);
}
