package com.unicyb.controllers;

import com.unicyb.data.ExchangeHistory;
import com.unicyb.data.User;
import com.unicyb.repositories.ExchangeHistoryRepository;
import com.unicyb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserPageController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ExchangeHistoryRepository exchangeHistoryRepository;
    @GetMapping("user_page")
    public String showUserPage(Model model, Principal principal) {
        int userId = userRepository.findByName(principal.getName()).getId();
        model.addAttribute("exchangers", exchangeHistoryRepository.findAllByUserId(userId));
        return "userPage";
    }
    @PostMapping("/user_page")
    public String updateExchangeHistory(ExchangeHistory exchangeHistory, Principal principal, Model model) {
        String username = principal.getName();
        User user = userRepository.findByName(username);
        exchangeHistory.setUserId(user.getId());
        exchangeHistoryRepository.save(exchangeHistory);
        int userId = userRepository.findByName(principal.getName()).getId();
        model.addAttribute("exchangers", exchangeHistoryRepository.findAllByUserId(userId));
        return "userPage";
    }
}
