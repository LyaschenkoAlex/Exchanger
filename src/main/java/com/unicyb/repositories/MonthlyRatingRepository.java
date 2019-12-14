package com.unicyb.repositories;

import com.unicyb.data.MonthlyRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MonthlyRatingRepository extends JpaRepository<MonthlyRating, Integer> {
    List<MonthlyRating> findAllByOrderById();
    @Query(value = "SELECT max(id) FROM exchanger.monthly_rating", nativeQuery = true)
    int maxId();
    MonthlyRating getById(int id);
}
