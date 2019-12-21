package com.unicyb.business_logic;

import com.unicyb.data.Course;
import com.unicyb.data.MonthlyRating;
import com.unicyb.repositories.CourseRepository;
import com.unicyb.repositories.MonthlyRatingRepository;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CurrentRate {

    public static final int maxId = -1;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");


    public List<Course> readFromFile() {
        File file = new File("C:/Users/lyasc/IdeaProjects/Exchanger/output.txt");
        List<Course> listOfCourses = new ArrayList<>(5);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                String[] currentRateOfBank = str.split(" ");
                switch (currentRateOfBank[0]) {
                    case "private":
                        listOfCourses.add(saveCurrentRate(currentRateOfBank, 2));
                        break;
                    case "agricole":
                        listOfCourses.add(saveCurrentRate(currentRateOfBank, 3));
                        break;
                    case "ukrsibbank":
                        listOfCourses.add(saveCurrentRate(currentRateOfBank, 4));
                        break;
                    case "oschadbank":
                        listOfCourses.add(saveCurrentRate(currentRateOfBank, 5));
                        break;
                    case "gov":
                        listOfCourses.add(saveCurrentRate(currentRateOfBank[1], currentRateOfBank[2], currentRateOfBank[3], 1));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfCourses;
    }

    private Course saveCurrentRate(String[] currentRateOfBank, int bankId) {
        LocalDateTime now = LocalDateTime.now();

        return new Course(bankId, Double.parseDouble(currentRateOfBank[1]),
                Double.parseDouble(currentRateOfBank[2]), Double.parseDouble(currentRateOfBank[3]),
                Double.parseDouble(currentRateOfBank[4]), Double.parseDouble(currentRateOfBank[5]),
                Double.parseDouble(currentRateOfBank[6]), dtf.format(now));
    }

    private Course saveCurrentRate(String buyUSD, String buyEUR, String buyRUB, int bankId) {
        LocalDateTime now = LocalDateTime.now();

        return new Course(bankId, Double.parseDouble(buyUSD), Double.parseDouble(buyUSD),
                Double.parseDouble(buyEUR), Double.parseDouble(buyEUR), Double.parseDouble(buyRUB),
                Double.parseDouble(buyRUB), dtf.format(now));
    }


    public static void asyncChangeCurrentRate(MonthlyRatingRepository monthlyRatingRepository, CourseRepository courseRepository) {
        CurrentRate currentRate = new CurrentRate();
        while (true) {

            currentRate.readFromFile().forEach(course -> courseRepository.save(course));
            Course course = currentRate.readFromFile().get(0);
            String date = course.getDate().split(" ")[0];
            date = date.substring(5);
            String month = date.split("/")[0];
            String day = date.split("/")[1];
            String formattedDate = day + "." + month;
            if (!monthlyRatingRepository.getById(monthlyRatingRepository.maxId()).getDate().equals(formattedDate)) {
                monthlyRatingRepository.save(new MonthlyRating(course.getBuyUSD(), course.getBuyEUR(), course.getBuyRUB(), formattedDate));
            }
            try {
                Thread.sleep(60 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
