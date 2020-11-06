package com.dtvc.api.controller;

import com.dtvc.api.service.EmailService;
import com.dtvc.api.service.UserService;
import core.constants.AppConstants;
import core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/account")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/create")
    public ResponseEntity create(@RequestBody User user) {
        String token = UUID.randomUUID().toString();
        try {
            user.setToken(token);
//            Role role = new Role();
//            role.setRoleId(AppConstants.DEFAULT_ROLE_ID);
//            user.setRole(role);
            userService.create(user);
            emailService.sendEmail(AppConstants.SUBJECT, "Click here to confirm your account: " +
                    AppConstants.HOST + "?username=" + user.getUsername() + "&token=" + token, user.getUsername());
        } catch (Exception ex) {
            return new ResponseEntity("400", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("200", HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public Optional<List<User>> search(@RequestParam(value = "value", defaultValue = "") String value,
                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE + "") int page,
                                       @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE + "") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("fullname"));
        Optional<List<User>> list = userService.search(value, pageable);
        return list;
    }

    @PostMapping(value = "/updateStatus")
    public ResponseEntity updateStatus(@RequestParam(value = "username") String username,
                                       @RequestParam(value = "status") String status) {
        int row = userService.updateStatus(username, status);
        if (row < 1) {
            return new ResponseEntity("400", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("200", HttpStatus.OK);
    }

    @PostMapping(value = "/updatePassword")
    public ResponseEntity updatePassword(@RequestParam(value = "username") String username,
                                         @RequestParam(value = "oldPassword") String oldPassword,
                                         @RequestParam(value = "newPassword") String newPassword) {
        int row = userService.updatePassword(username, oldPassword, newPassword);
        if (row < 1) {
            return new ResponseEntity("400", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("200", HttpStatus.OK);
    }

    @PostMapping(value = "/updateProfile")
    public ResponseEntity updateProfile(@RequestParam(value = "username") String username,
                                        @RequestParam(value = "fullname") String fullname) {
        int row = userService.updateProfile(username, fullname);
        if (row < 1) {
            return new ResponseEntity("400", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("200", HttpStatus.OK);
    }

    @PostMapping(value = "/confirm")
    public ResponseEntity confirm(@RequestParam(value = "username") String username,
                                  @RequestParam(value = "password") String password,
                                  @RequestParam(value = "token") String token,
                                  @RequestParam(value = "status") String status) {
        int row = userService.confirm(username, token, password, status);
        if (row < 1) {
            return new ResponseEntity("400", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("200", HttpStatus.OK);
    }

    @GetMapping(value = "/filterByStatus")
    public Optional<List<User>> filterByStatus(@RequestParam(value = "status", defaultValue = AppConstants.DEFAULT_STATUS) String status,
                                               @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE + "") int page,
                                               @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE + "") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("fullname"));
        Optional<List<User>> list = userService.filterByStatus(status, pageable);
        return list;
    }
}
