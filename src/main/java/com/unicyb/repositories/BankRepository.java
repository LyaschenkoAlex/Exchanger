package com.unicyb.repositories;

import com.unicyb.data.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Integer> {
}
