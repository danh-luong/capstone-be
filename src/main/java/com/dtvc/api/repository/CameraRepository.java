package com.dtvc.api.repository;

import core.domain.Camera;
import core.domain.GroupCamera;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Integer>, PagingAndSortingRepository<Camera, Integer> {

    @Query(value = "SELECT DISTINCT location" +
            " from cameras where location like %:value%" +
            " order by location",
            nativeQuery = true)
    Optional<List<String>> searchLocation(@Param(value = "value") String value);

    @Query(value = "select *" +
            " from cameras where location like %:location% and status = :status",
            nativeQuery = true)
    Optional<List<Camera>> filterByLocationAndStatus(@Param(value = "location") String location,
                                                     @Param(value = "status") String status,
                                                     Pageable pageable);

    @Query(value = "select *" +
            " from cameras where group_id = :groupId",
            nativeQuery = true)
    List<Camera> getByGroup(@Param(value = "groupId") int groupId);


    @Transactional
    @Modifying
    @Query(value = "update cameras" +
            " set connection_url = :connectionUrl, group_id = :groupId," +
            " location = :location, position = :position, status = :status" +
            " where camera_id = :cameraId",
            nativeQuery = true)
    int update(@Param("cameraId") int cameraId,
               @Param("connectionUrl") String connectionUrl,
               @Param("groupId") int groupId,
               @Param("location") String location,
               @Param("position") String position,
               @Param("status") String status);

    @Transactional
    @Modifying
    @Query(value = "insert into cameras(status, position, location, group_id, connection_url)" +
            " values(:status, :position, :location, :groupId, :connectionUrl)",
            nativeQuery = true)
    int create(@Param("status") String status,
               @Param("position") String position,
               @Param("location") String location,
               @Param("groupId") int groupId,
               @Param("connectionUrl") String connectionUrl);

    @Query(value = "select *" +
            " from cameras" +
            " where status = :status",
            nativeQuery = true)
    Optional<List<Camera>> getAllByStatus(Pageable pageable,
                                          @Param("status") String status);

    @Query(value = "select *" +
            " from cameras",
            nativeQuery = true)
    Optional<List<Camera>> getAll(Pageable pageable);

    @Query(value = "select *" +
            " from cameras where location like %:location%",
            nativeQuery = true)
    Optional<List<Camera>> filterByLocation(@Param("location") String location,
                                            Pageable pageable);

    @Query(value = "select *" +
            " from cameras" +
            " where camera_id = :id",
            nativeQuery = true)
    Camera getById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query(value = "update cameras" +
            " set status = :status" +
            " where camera_id = :cameraId",
            nativeQuery = true)
    int updateStatus(@Param("cameraId") int cameraId,
                     @Param("status") String status);
}
