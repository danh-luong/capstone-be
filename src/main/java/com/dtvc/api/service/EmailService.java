package com.dtvc.api.service;

public interface EmailService {

    void sendEmail(String subject, String content, String email);
}
