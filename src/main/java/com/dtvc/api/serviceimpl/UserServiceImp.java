package com.dtvc.api.serviceimpl;

import com.dtvc.api.repository.UserRepository;
import com.dtvc.api.service.UserService;
import core.constants.AppConstants;
import core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(User user) {
        userRepository.create(user.getUsername(), user.getFullname(),
                user.getRole().getRoleId(), user.getStatus(), user.getToken());
    }

    @Override
    public Optional<List<User>> search(String value, Pageable pageable) {
        Optional<List<User>> list = userRepository.search(value, pageable);
        return list;
    }

    @Override
    public int updateStatus(String username, String status) {
        int row = userRepository.updateStatus(username, status);
        return row;
    }

    @Override
    public int updatePassword(String username, String oldPassword, String newPassword) {
        int row = userRepository.updatePassword(username, oldPassword, newPassword);
        return row;
    }

    @Override
    public int updateProfile(String username, String fullname) {
        int row = userRepository.updateProfile(username, fullname);
        return row;
    }

    @Override
    public int confirm(String username, String token, String password, String status) {
        int row = userRepository.confirm(username, token, password, status);
        return row;
    }

    @Override
    public Optional<List<User>> filterByStatus(String status, Pageable pageable) {
        Optional<List<User>> list = null;
        if (status.equals(AppConstants.DEFAULT_STATUS)) {
            list = userRepository.searchByName("", pageable);
        } else {
            list = userRepository.filterByStatus(status, pageable);
        }
        return list;
    }

    @Override
    public Optional<List<User>> filterByStatusAndName(String status, String name, Pageable pageable) {
        Optional<List<User>> list = null;
        if (status.equals(AppConstants.DEFAULT_STATUS)) {
            list = userRepository.searchByName(name, pageable);
        } else {
            list = userRepository.filterByStatusAndName(status, name, pageable);
        }
        return list;
    }

    @Override
    public Optional<User> checkLogin(User user, String status) {
        Optional<User> entity = userRepository.checkLogin(user.getUsername(), user.getPassword(), status);
        return entity;
    }

}
