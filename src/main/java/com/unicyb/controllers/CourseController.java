package com.unicyb.controllers;

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
        ///////////////////////////////////////////////////////////
        List<MonthlyRating> modelAttributes = monthlyRatingRepository.findAllByOrderById();
        List<Double> courseEUR = modelAttributes.stream().map(rating -> rating.getCourseEUR()).collect(Collectors.toList());
        List<Double> courseUSD = modelAttributes.stream().map(rating -> rating.getCourseUSD()).collect(Collectors.toList());
        List<Double> courseRUB = modelAttributes.stream().map(rating -> rating.getCourseRUB()).collect(Collectors.toList());
        List<String> courseDate = modelAttributes.stream().map(rating -> rating.getDate()).collect(Collectors.toList());
        model.addAttribute("courseEUR", courseEUR);
        model.addAttribute("courseUSD", courseUSD);
        model.addAttribute("courseRUB", courseRUB);
        model.addAttribute("courseDate", courseDate);
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
