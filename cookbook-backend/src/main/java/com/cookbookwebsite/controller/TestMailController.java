package com.cookbookwebsite.controller;

import com.cookbookwebsite.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test-mail")
public class TestMailController {

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ResponseEntity<String> testEmail(@RequestParam String to) {
        emailService.sendEmail(to, "Test Email", "This is a test message from Cookbook!");
        return ResponseEntity.ok("Email sent!");
    }
}