package com.sohaib.trackmystacks.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sohaib.trackmystacks.dto.MonthlyComparison;
import com.sohaib.trackmystacks.model.Expense;
import com.sohaib.trackmystacks.model.Paycheck;
import com.sohaib.trackmystacks.model.User;
import com.sohaib.trackmystacks.repository.ExpenseRepository;
import com.sohaib.trackmystacks.repository.PaycheckRepository;

@Service
public class PaycheckService {

    @Autowired
    private PaycheckRepository paycheckRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public Paycheck addPaycheck(User user, BigDecimal amount, LocalDate month, String description) {
        // Normalise to the 1st of the month
        LocalDate firstOfMonth = month.withDayOfMonth(1);
        Paycheck p = new Paycheck(user, amount, firstOfMonth, description);
        return paycheckRepository.save(p);
    }

    public List<Paycheck> getAllPaychecksByUser(User user) {
        return paycheckRepository.findByUserOrderByMonthDesc(user);
    }

    public void deletePaycheck(Long id) {
        paycheckRepository.deleteById(id);
    }

    /**
     * Builds a month-by-month comparison for the last {@code months} months
     * (most-recent last so the chart reads left → right chronologically).
     */
    @Transactional(readOnly = true)
    public List<MonthlyComparison> getMonthlyComparison(User user, int months) {
        LocalDate today = LocalDate.now();
        LocalDate from = today.minusMonths(months - 1).withDayOfMonth(1);
        LocalDate to   = today.withDayOfMonth(1);

        // Aggregate paychecks by month
        List<Paycheck> paychecks = paycheckRepository.findByUserAndMonthBetween(user, from, to);
        Map<LocalDate, BigDecimal> incomeByMonth = new HashMap<>();
        for (Paycheck p : paychecks) {
            LocalDate key = p.getMonth().withDayOfMonth(1);
            incomeByMonth.merge(key, p.getAmount(), BigDecimal::add);
        }

        // Aggregate expenses by month — use end-of-month so future-dated entries
        // (e.g. recurring expenses pre-dated to the 28th) are included.
        LocalDate expFrom = from;
        LocalDate expTo   = today.withDayOfMonth(today.lengthOfMonth());
        List<Expense> expenses = expenseRepository.findByUserAndDateBetweenOrderByDateDesc(user, expFrom, expTo);
        Map<LocalDate, BigDecimal> expensesByMonth = new HashMap<>();
        for (Expense e : expenses) {
            LocalDate key = e.getDate().withDayOfMonth(1);
            expensesByMonth.merge(key, e.getAmount(), BigDecimal::add);
        }

        // Build ordered list
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM yyyy");
        List<MonthlyComparison> result = new ArrayList<>();
        LocalDate cursor = from;
        while (!cursor.isAfter(to)) {
            BigDecimal income   = incomeByMonth.getOrDefault(cursor, BigDecimal.ZERO);
            BigDecimal expenses2 = expensesByMonth.getOrDefault(cursor, BigDecimal.ZERO);
            result.add(new MonthlyComparison(cursor.format(fmt), income, expenses2));
            cursor = cursor.plusMonths(1);
        }
        return result;
    }
}
