package com.unicyb.controllers;

import com.unicyb.data.Bank;
import com.unicyb.data.Course;
import com.unicyb.repositories.BankRepository;
import com.unicyb.repositories.CourseRepository;
import com.unicyb.business_logic.CurrentRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    BankRepository bankRepository;

    @GetMapping
    public String getBankCourses(Model model) {
        List<Map<String, Course>> bankNamesAndCourses = new ArrayList<>(5);
        Course course;
        int maxId = courseRepository.maxId();
        for (int i = maxId; i > maxId - 5; i--) {
            Map<String, Course> bankNameAndCourse = new HashMap<>(1);
            course = courseRepository.findById(i).get();
            bankNameAndCourse.put(bankRepository.findById(course.getBankId()).get().getName(),course);
            bankNamesAndCourses.add(bankNameAndCourse);
        }
        model.addAttribute("coursesAndBanks", bankNamesAndCourses);
        return "courses";
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        asyncChangeCurrentRate();
    }

    private void asyncChangeCurrentRate() {
        CurrentRate currentRate = new CurrentRate();
        while (true) {
            currentRate.readFromFile().forEach(course -> courseRepository.save(course));
            try {
                Thread.sleep(60 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    private void showBankCourses(Model model, int maxId) {
//        List<Map<String, Course>> bankNamesAndCourses = new ArrayList<>(5);
//        Course course;
//        for (int i = maxId; i > maxId - 5; i--) {
//            Map<String, Course> bankNameAndCourse = new HashMap<>(1);
//            course = courseRepository.findById(i).get();
//            bankNameAndCourse.put(bankRepository.findById(course.getId()).get().getName(),course);
//            bankNamesAndCourses.add(bankNameAndCourse);
//        }
//        model.addAttribute("coursesAndBanks", bankNamesAndCourses);
//    }

}
