package com.dtvc.api.service;

import core.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void create(User user);

    Optional<List<User>> search(String value, Pageable pageable);

    int updateStatus(String username, String status);

    int updatePassword(String username, String oldPassword, String newPassword);

    int updateProfile(String username, String fullname);

    int confirm(String username, String token, String password, String status);
}
