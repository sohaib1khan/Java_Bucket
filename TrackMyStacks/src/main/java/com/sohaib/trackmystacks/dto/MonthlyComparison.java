package com.sohaib.trackmystacks.dto;

import java.math.BigDecimal;

/**
 * One row in the monthly Income vs Expenses comparison table / chart.
 */
public class MonthlyComparison {

    private String monthLabel;   // e.g. "Jan 2026"
    private BigDecimal income;
    private BigDecimal expenses;
    private BigDecimal balance;  // income - expenses (positive = surplus, negative = deficit)

    public MonthlyComparison(String monthLabel, BigDecimal income, BigDecimal expenses) {
        this.monthLabel = monthLabel;
        this.income = income;
        this.expenses = expenses;
        this.balance = income.subtract(expenses);
    }

    public String getMonthLabel() { return monthLabel; }
    public BigDecimal getIncome() { return income; }
    public BigDecimal getExpenses() { return expenses; }
    public BigDecimal getBalance() { return balance; }
}
