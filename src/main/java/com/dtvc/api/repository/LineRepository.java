package com.dtvc.api.repository;

import core.domain.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LineRepository extends JpaRepository<Line, Integer> {

    @Query(value = "Select *" +
            " from line" +
            " where camera_id = :cameraId",
            nativeQuery = true)
    List<Line> getByCameraId(@Param("cameraId") int cameraId);

    @Transactional
    @Modifying
    @Query(value = "update line" +
            " set line_type = :lineType, lefts = :left, top = :top, rights = :right, bottom = :bottom" +
            " where line_id = :lineId",
            nativeQuery = true)
    void update(@Param("lineId") int lineId,
                @Param("lineType") String lineType,
                @Param("left") int left,
                @Param("top") int top,
                @Param("right") int right,
                @Param("bottom") int bottom);
}
