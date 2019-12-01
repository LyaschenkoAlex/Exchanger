package com.unicyb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {
    @GetMapping("user_page")
    public String showUserPage() {
        return "userPage";
    }
}
