package com.unicyb.controllers;

import com.unicyb.data.ExchangeHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainPageController {

    @GetMapping("/main_page")
    public String showMainPage(Model model) {
        model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return "mainPage";
    }
}
