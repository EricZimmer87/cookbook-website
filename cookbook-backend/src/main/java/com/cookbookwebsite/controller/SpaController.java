package com.cookbookwebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SpaController {

    @RequestMapping(
            method = RequestMethod.GET,
            value = {
                    "/",
                    "/recipes/**",
                    "/users/**",
                    "/tags/**",
                    "/categories/**",
                    "/ingredients/**",
                    "/difficulty-levels/**",
                    "/reviews/**",
                    "/login",
                    "/register",
                    "/forgot-password",
                    "/reset-password",
                    "/auth/**",
                    "/forbidden",
                    "/not-found"
            }
    )
    public String forward() {
        return "forward:/index.html";
    }
}
