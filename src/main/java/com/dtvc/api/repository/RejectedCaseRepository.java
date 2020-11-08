package com.dtvc.api.repository;

import core.domain.Image;
import core.domain.RejectedCase;
import core.domain.ViolationType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface RejectedCaseRepository extends JpaRepository<RejectedCase, Integer>, PagingAndSortingRepository<RejectedCase, Integer> {

    @Query(value = "select *" +
            " from rejected_cases" +
            " where created_date between :fromDate and :toDate" +
            " and violation_id = :violationId",
            nativeQuery = true)
    Optional<List<RejectedCase>> filter(@Param("fromDate") Date fromDate,
                                        @Param("toDate") Date toDate,
                                        @Param("violationId") int violationId,
                                        Pageable pageable);

    @Query(value = "select *" +
            " from rejected_cases",
            nativeQuery = true)
    Optional<List<RejectedCase>> getAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "insert into rejected_cases" +
            "(case_id, trained_status, created_date, image_id, license_plate, location, violation_id)" +
            " values(:caseId, :trainedStatus, :createdDate, :imageId, :licensePlate, :location, :violationId)",
            nativeQuery = true)
    int create(@Param("caseId") int caseId,
               @Param("trainedStatus") String trainedStatus,
               @Param("createdDate") Date createdDate,
               @Param("imageId") int imageId,
               @Param("licensePlate") String licensePlate,
               @Param("location") String location,
               @Param("violationId") int violationId);

    @Query(value = "select count(trained_status)" +
            " from rejected_cases" +
            " where trained_status = :status and violation_id = :violationId",
            nativeQuery = true)
    int getCountOfStatus(@Param("status") String status,
                         @Param("violationId") int violationId);

    @Query(value = "select *" +
            " from rejected_cases" +
            " where created_date between :fromDate and :toDate",
            nativeQuery = true)
    Optional<List<RejectedCase>> filterByDate(@Param("fromDate") Date fromDate,
                                              @Param("toDate") Date toDate,
                                              Pageable pageable);
}
