package com.dtvc.api.repository;

import core.domain.User;
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
public interface UserRepository extends JpaRepository<User, String>, PagingAndSortingRepository<User, String> {

    @Transactional
    @Modifying
    @Query(value = "insert into users(username, fullname, role_id, status, token)" +
            " values (:username, :fullname, :role_id, :status, :token)",
            nativeQuery = true)
    int create(@Param("username") String username,
               @Param("fullname") String fullname,
               @Param("role_id") int role_id,
               @Param("status") String status,
               @Param("token") String token);

    @Query(value = "select *" +
            " from users" +
            " where username like %:value%" +
            " or fullname like %:value%",
            nativeQuery = true)
    Optional<List<User>> search(@Param("value") String value,
                                Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users" +
            " set status = :status" +
            " where username = :username",
            nativeQuery = true)
    int updateStatus(@Param("username") String username,
                     @Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "update users" +
            " set password = :newPassword" +
            " where username = :username" +
            " and password = :oldPassword",
            nativeQuery = true)
    int updatePassword(@Param("username") String username,
                       @Param("oldPassword") String oldPassword,
                       @Param("newPassword") String newPassword);

    @Transactional
    @Modifying
    @Query(value = "update users" +
            " set fullname = :fullname" +
            " where username = :username",
            nativeQuery = true)
    int updateProfile(@Param("username") String username,
                      @Param("fullname") String fullname);

    @Modifying
    @Transactional
    @Query(value = "update users" +
            " set password = :password, status = :status" +
            " where username = :username and token = :token",
            nativeQuery = true)
    int confirm(@Param("username") String username,
                @Param("token") String token,
                @Param("password") String password,
                @Param("status") String status);
}
