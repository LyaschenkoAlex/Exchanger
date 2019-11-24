package com.unicyb.repositories;

import com.unicyb.data.MonthlyRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthlyRatingRepository extends JpaRepository<MonthlyRating, Integer> {
    List<MonthlyRating> findAllByOrderById();
}
