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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    MonthlyRatingRepository monthlyRatingRepository;

    @GetMapping
    public String getBankCourses(Model model) {
        CourseControllerLogic.addTable(model, bankRepository, courseRepository);
        CourseControllerLogic.addPlot(model, monthlyRatingRepository);
        return "courses";
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        CurrentRate.asyncChangeCurrentRate(monthlyRatingRepository, courseRepository);
    }

}
