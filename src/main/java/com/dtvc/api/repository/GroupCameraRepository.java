package com.dtvc.api.repository;

import core.domain.GroupCamera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupCameraRepository extends JpaRepository<GroupCamera, Integer> {

    @Query(value = "SELECT g.group_id, g.group_name" +
            " FROM dtv.cameras c right join dtv.group_cameras g on c.group_id = g.group_id" +
            " where c.location = :location",
            nativeQuery = true)
    List<GroupCamera> searchByLocation(@Param("location") String location);

    @Query(value = "SELECT g.group_id, g.group_name" +
            " FROM dtv.cameras c right join dtv.group_cameras g on c.group_id = g.group_id" +
            " group by g.group_id having count(c.group_id) < :count",
            nativeQuery = true)
    Optional<List<GroupCamera>> loadAvailable(@Param("count") int count);

    @Query(value = "SELECT g.group_id, g.group_name" +
            " FROM dtv.cameras c right join dtv.group_cameras g on c.group_id = g.group_id" +
            " where g.group_name like %:name%" +
            " group by g.group_id having count(c.group_id) < :count",
            nativeQuery = true)
    Optional<List<GroupCamera>> searchByName(@Param("count") int count,
                                             @Param("name") String name);
}
