//package com.unicyb.controllers;
//
//import com.unicyb.data.MonthlyRating;
//import com.unicyb.repositories.MonthlyRatingRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/chart")
//public class ChartController {
//    @Autowired
//    MonthlyRatingRepository monthlyRatingRepository;
//    @GetMapping
//    public String qq(Model model) {
//        List<MonthlyRating> modelAttributes = monthlyRatingRepository.findAllByOrderById();
//        List<Double> courseEUR = modelAttributes.stream().map(rating -> rating.getCourseEUR()).collect(Collectors.toList());
//        List<Double> courseUSD = modelAttributes.stream().map(rating -> rating.getCourseUSD()).collect(Collectors.toList());
//        List<Double> courseRUB = modelAttributes.stream().map(rating -> rating.getCourseRUB()).collect(Collectors.toList());
//        List<String> courseDate = modelAttributes.stream().map(rating -> rating.getDate()).collect(Collectors.toList());
//        model.addAttribute("courseEUR", courseEUR);
//        model.addAttribute("courseUSD", courseUSD);
//        model.addAttribute("courseRUB", courseRUB);
//        model.addAttribute("courseDate", courseDate);
//        return "index";
//    }
//}
