package com.qansoft.example.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@PreAuthorize("isAuthenticated()")
public class Test {

    @GetMapping()
    public String getTest () {
        return "For all authenticated user";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('GROUP_admin')")
    public String getAdmin() {
        return "Just for admin";
    }

}
