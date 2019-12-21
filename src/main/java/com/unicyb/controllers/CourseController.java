package com.unicyb.controllers;

import com.unicyb.business_logic.CourseControllerLogic;
import com.unicyb.data.Bank;
import com.unicyb.data.Course;
import com.unicyb.data.MonthlyRating;
import com.unicyb.repositories.BankRepository;
import com.unicyb.repositories.CourseRepository;
import com.unicyb.business_logic.CurrentRate;
import com.unicyb.repositories.MonthlyRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/exchange_rates")
class CourseController {
    final CourseRepository courseRepository;
    final BankRepository bankRepository;
    final MonthlyRatingRepository monthlyRatingRepository;

    public CourseController(CourseRepository courseRepository, BankRepository bankRepository, MonthlyRatingRepository monthlyRatingRepository) {
        this.courseRepository = courseRepository;
        this.bankRepository = bankRepository;
        this.monthlyRatingRepository = monthlyRatingRepository;
    }

    @GetMapping
    public String getBankCourses(Model model) {
        CourseControllerLogic.addTable(model, bankRepository, courseRepository);
        CourseControllerLogic.addPlot(model, monthlyRatingRepository);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getName());
        return "courses";
    }

    @EventListener(ApplicationReadyEvent.class)
    public void updateCoursesAfterStartup() {
        CurrentRate.asyncChangeCurrentRate(monthlyRatingRepository, courseRepository);
    }

}
