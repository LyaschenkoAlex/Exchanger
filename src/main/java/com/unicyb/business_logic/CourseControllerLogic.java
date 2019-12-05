package com.unicyb.business_logic;

import com.unicyb.data.Course;
import com.unicyb.data.MonthlyRating;
import com.unicyb.repositories.BankRepository;
import com.unicyb.repositories.CourseRepository;
import com.unicyb.repositories.MonthlyRatingRepository;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CourseControllerLogic {
    public static void addTable(Model model, BankRepository bankRepository, CourseRepository courseRepository) {
        List<Map<String, Course>> bankNamesAndCourses = new ArrayList<>(5);
        Course course;
        int maxId = courseRepository.maxId();
        double minBuyUSD = Integer.MIN_VALUE;
        double minBuyEUR = Integer.MIN_VALUE;
        double minBuyRUB = Integer.MIN_VALUE;
        double maxSellUSD = Integer.MAX_VALUE;
        double maxSellEUR = Integer.MAX_VALUE;
        double maxSellRUB = Integer.MAX_VALUE;
        for (int i = maxId; i > maxId - 5; i--) {
            Map<String, Course> bankNameAndCourse = new HashMap<>(1);
            course = courseRepository.findById(i).get();
            if (course.getBankId() != 1 && course.getBuyEUR() > minBuyEUR) minBuyEUR = course.getBuyEUR();
            if (course.getBankId() != 1 && course.getSellEUR() < maxSellEUR) maxSellEUR = course.getSellEUR();
            if (course.getBankId() != 1 && course.getBuyUSD() > minBuyUSD) minBuyUSD = course.getBuyUSD();
            if (course.getBankId() != 1 && course.getSellUSD() < maxSellUSD) maxSellUSD = course.getSellUSD();
            if (course.getBankId() != 1 && course.getBuyRUB() > minBuyRUB) minBuyRUB = course.getBuyRUB();
            if (course.getBankId() != 1 && course.getSellRUB() < maxSellRUB) maxSellRUB = course.getSellRUB();
            bankNameAndCourse.put(bankRepository.findById(course.getBankId()).get().getName(), course);
            bankNamesAndCourses.add(bankNameAndCourse);
        }
        model.addAttribute("minBuyUSD", minBuyUSD);
        model.addAttribute("maxSellUSD", maxSellUSD);
        model.addAttribute("minBuyEUR", minBuyEUR);
        model.addAttribute("maxSellEUR", maxSellEUR);
        model.addAttribute("minBuyRUB", minBuyRUB);
        model.addAttribute("maxSellRUB", maxSellRUB);
        model.addAttribute("coursesAndBanks", bankNamesAndCourses);

    }

    public static void addPlot(Model model, MonthlyRatingRepository monthlyRatingRepository) {
        List<MonthlyRating> modelAttributes = monthlyRatingRepository.findAllByOrderById();
        List<Double> courseEUR = modelAttributes.stream().map(rating -> rating.getCourseEUR()).collect(Collectors.toList());
        List<Double> courseUSD = modelAttributes.stream().map(rating -> rating.getCourseUSD()).collect(Collectors.toList());
        List<Double> courseRUB = modelAttributes.stream().map(rating -> rating.getCourseRUB()).collect(Collectors.toList());
        List<String> courseDate = modelAttributes.stream().map(rating -> rating.getDate()).collect(Collectors.toList());
        model.addAttribute("courseEUR", courseEUR);
        model.addAttribute("courseUSD", courseUSD);
        model.addAttribute("courseRUB", courseRUB);
        model.addAttribute("courseDate", courseDate);
    }
}
