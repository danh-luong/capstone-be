package com.dtvc.api.repository;

import core.domain.RejectedCase;
import core.domain.UnconfirmedCase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UnconfirmedCaseRepository extends JpaRepository<UnconfirmedCase, Integer>, PagingAndSortingRepository<UnconfirmedCase, Integer> {

    @Query(value = "select *" +
            " from unconfirmed_cases" +
            " where created_date between :fromDate and :toDate" +
            " and violation_id = :violationId",
            nativeQuery = true)
    Optional<List<UnconfirmedCase>> filter(@Param("fromDate") Date fromDate,
                                           @Param("toDate") Date toDate,
                                           @Param("violationId") int violationId,
                                           Pageable pageable);

    @Query(value = "select *" +
            " from unconfirmed_cases",
            nativeQuery = true)
    Optional<List<UnconfirmedCase>> getall(Pageable pageable);
}
