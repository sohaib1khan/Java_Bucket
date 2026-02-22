package com.sohaib.trackmystacks.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sohaib.trackmystacks.model.Paycheck;
import com.sohaib.trackmystacks.model.User;

@Repository
public interface PaycheckRepository extends JpaRepository<Paycheck, Long> {

    @Query("SELECT p FROM Paycheck p WHERE p.user = :user ORDER BY p.month DESC")
    List<Paycheck> findByUserOrderByMonthDesc(User user);

    @Query("SELECT p FROM Paycheck p WHERE p.user = :user AND p.month >= :from AND p.month <= :to ORDER BY p.month ASC")
    List<Paycheck> findByUserAndMonthBetween(User user, LocalDate from, LocalDate to);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Paycheck p WHERE p.user = :user AND p.month = :month")
    BigDecimal getTotalByUserAndMonth(User user, LocalDate month);
}
