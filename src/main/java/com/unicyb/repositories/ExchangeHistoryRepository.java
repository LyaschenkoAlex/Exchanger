package com.unicyb.repositories;

import com.unicyb.data.ExchangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeHistoryRepository extends JpaRepository<ExchangeHistory, Integer> {
    List<ExchangeHistory> findAllByUserId(int id);
}
