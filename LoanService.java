package com.library.service;

import com.library.dao.LoanDAO;
import com.library.model.Loan;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class LoanService {
    private static final double FINE_PER_DAY = 5.0;
    private final LoanDAO dao = new LoanDAO();

    public void issue(int bookId, int memberId, LocalDate issueDate, LocalDate dueDate)
            throws SQLException {
        Loan loan = new Loan();
        loan.setBookId(bookId);
        loan.setMemberId(memberId);
        loan.setIssueDate(issueDate);
        loan.setDueDate(dueDate);
        dao.issue(loan);
    }

    public double calculateFine(LocalDate dueDate, LocalDate returnDate) {
        if (!returnDate.isAfter(dueDate)) return 0;
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        return daysLate * FINE_PER_DAY;
    }

    public void returnBook(int loanId, LocalDate returnDate) throws SQLException {
        Loan loan = dao.findActive(loanId);
        if (loan == null) throw new SQLException("Active loan not found.");
        double fine = calculateFine(loan.getDueDate(), returnDate);
        dao.returnBook(loanId, returnDate, fine);
    }

    public List<Loan> findAll() throws SQLException { return dao.findAll(); }
}
