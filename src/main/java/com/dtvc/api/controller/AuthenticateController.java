package com.dtvc.api.controller;

import com.dtvc.api.service.UserService;
import core.constants.AppConstants;
import core.domain.User;
import core.util.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticateController {

    @Autowired
    private UserService userService;

    /**
     * @param user (just need username and password in body)
     * Requirement: The password has been encrypted in database, you can run main method in this path to get it
     *             ('/core/util/SHA256' right click and select "Run SHA256.main()")
     *             Please change your input password before running it
     * @return
     */
    @PostMapping(value = "/signin")
    public Optional<User> signin(@RequestBody User user) {
        try {
            user.setPassword(SHA256.encrypt(user.getPassword()));
        } catch (NoSuchAlgorithmException e) {
        }
        Optional<User> entity = userService.checkLogin(user, AppConstants.ACTIVE_STATUS);
        return entity;
    }
}
