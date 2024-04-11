package com.example.learningcourseapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.learningcourseapp.services.Administrator.iAdministratorService;
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdministratorController {
    private final iAdministratorService AdministratorService;
}
