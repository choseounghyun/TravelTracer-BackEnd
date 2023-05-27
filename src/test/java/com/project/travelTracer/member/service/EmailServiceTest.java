package com.project.travelTracer.member.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @Test
    public void getTempPassword() throws Exception {
        String tempPassword = emailService.getTempPassword();
        log.info("tempPassword = {}", tempPassword);
    }

}