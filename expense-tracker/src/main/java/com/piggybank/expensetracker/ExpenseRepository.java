package com.piggybank.expensetracker;

// Import Spring Data JPA repository
// This is the MAGIC that gives us database methods for FREE!
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// Import for date range queries
import java.time.LocalDate;
import java.util.List;

// ============================================
// REPOSITORY INTERFACE (Database Access)
// ============================================
// @Repository = This is a database access component
// JpaRepository<Expense, Long> = Repository for Expense entity with Long ID type
//
// Python SQLAlchemy equivalent:
//   This is like having all these functions automatically created:
//   - Expense.query.all()                    → findAll()
//   - Expense.query.get(id)                  → findById(id)
//   - db.session.add(expense)                → save(expense)
//   - db.session.delete(expense)             → delete(expense)
//   - Expense.query.filter_by(category=x)    → findByCategory(x)
//
// Spring Boot AUTOMATICALLY implements all these methods!
// You don't write ANY code - just declare the interface!
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    // ============================================
    // CUSTOM QUERY METHODS
    // ============================================
    // Spring Data JPA can generate queries from method names!
    // Just name your method correctly and it works!
    
    // Find all expenses in a specific category
    // Python SQLAlchemy equivalent:
    //   Expense.query.filter_by(category=category).all()
    // Spring Boot reads "findByCategory" and automatically creates:
    //   SELECT * FROM expenses WHERE category = ?
    List<Expense> findByCategory(String category);
    
    // Find all expenses between two dates (for monthly reports)
    // Python SQLAlchemy equivalent:
    //   Expense.query.filter(Expense.date >= start, Expense.date <= end).all()
    // Spring Boot reads "findByDateBetween" and creates:
    //   SELECT * FROM expenses WHERE date BETWEEN ? AND ?
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Find all expenses ordered by date (newest first)
    // Python SQLAlchemy equivalent:
    //   Expense.query.order_by(Expense.date.desc()).all()
    // Spring Boot reads "findAllByOrderByDateDesc" and creates:
    //   SELECT * FROM expenses ORDER BY date DESC
    List<Expense> findAllByOrderByDateDesc();
    
    // Custom query using @Query annotation (for complex queries)
    // Calculate total spending by category
    // Python SQLAlchemy equivalent:
    //   db.session.query(Expense.category, db.func.sum(Expense.amount))
    //             .group_by(Expense.category).all()
    @Query("SELECT e.category, SUM(e.amount) FROM Expense e GROUP BY e.category")
    List<Object[]> getTotalByCategory();
    
    // Calculate total spending in a date range
    // Python SQLAlchemy equivalent:
    //   db.session.query(db.func.sum(Expense.amount))
    //             .filter(Expense.date >= start, Expense.date <= end).scalar()
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.date BETWEEN ?1 AND ?2")
    Double getTotalSpendingBetweenDates(LocalDate startDate, LocalDate endDate);
}