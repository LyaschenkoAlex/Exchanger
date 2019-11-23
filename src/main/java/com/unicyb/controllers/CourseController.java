package com.unicyb.controllers;

import com.unicyb.data.Course;
import com.unicyb.repositories.CourseRepository;
import com.unicyb.business_logic.CurrentRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;

    @GetMapping
    public String getBankCourses(Model model) {

        int maxId = courseRepository.maxId();
        Course course = courseRepository.findById(maxId).get();

        model.addAttribute("course", course);
        return "hello_world";
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

}
