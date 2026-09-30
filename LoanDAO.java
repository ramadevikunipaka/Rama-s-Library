package com.library.dao;

import com.library.model.Loan;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    public void issue(Loan loan) throws SQLException {
        String insert = "INSERT INTO loans(book_id,member_id,issue_date,due_date,status,fine) VALUES(?,?,?,?,?,0)";
        String decrease = "UPDATE books SET available_quantity=available_quantity-1 WHERE id=? AND available_quantity>0";

        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement p1 = c.prepareStatement(decrease);
                 PreparedStatement p2 = c.prepareStatement(insert)) {

                p1.setInt(1, loan.getBookId());
                if (p1.executeUpdate() != 1) {
                    throw new SQLException("Book is unavailable.");
                }

                p2.setInt(1, loan.getBookId());
                p2.setInt(2, loan.getMemberId());
                p2.setDate(3, Date.valueOf(loan.getIssueDate()));
                p2.setDate(4, Date.valueOf(loan.getDueDate()));
                p2.setString(5, "ISSUED");
                p2.executeUpdate();

                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    public Loan findActive(int id) throws SQLException {
        String sql = "SELECT * FROM loans WHERE id=? AND status='ISSUED'";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Loan l = new Loan();
                    l.setId(rs.getInt("id"));
                    l.setBookId(rs.getInt("book_id"));
                    l.setMemberId(rs.getInt("member_id"));
                    l.setIssueDate(rs.getDate("issue_date").toLocalDate());
                    l.setDueDate(rs.getDate("due_date").toLocalDate());
                    l.setStatus(rs.getString("status"));
                    return l;
                }
            }
        }
        return null;
    }

    public void returnBook(int loanId, java.time.LocalDate returnDate, double fine) throws SQLException {
        String updateLoan = "UPDATE loans SET return_date=?, status='RETURNED', fine=? WHERE id=? AND status='ISSUED'";
        String increaseBook = "UPDATE books SET available_quantity=available_quantity+1 WHERE id=?";

        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try {
                Loan loan = findActive(loanId);
                if (loan == null) throw new SQLException("Active loan not found.");

                try (PreparedStatement p1 = c.prepareStatement(updateLoan);
                     PreparedStatement p2 = c.prepareStatement(increaseBook)) {
                    p1.setDate(1, Date.valueOf(returnDate));
                    p1.setDouble(2, fine);
                    p1.setInt(3, loanId);
                    p1.executeUpdate();

                    p2.setInt(1, loan.getBookId());
                    p2.executeUpdate();
                    c.commit();
                }
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    public List<Loan> findAll() throws SQLException {
        List<Loan> list = new ArrayList<>();
        String sql = "SELECT * FROM loans ORDER BY id DESC";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Loan l = new Loan();
                l.setId(rs.getInt("id"));
                l.setBookId(rs.getInt("book_id"));
                l.setMemberId(rs.getInt("member_id"));
                l.setIssueDate(rs.getDate("issue_date").toLocalDate());
                l.setDueDate(rs.getDate("due_date").toLocalDate());
                Date returned = rs.getDate("return_date");
                if (returned != null) l.setReturnDate(returned.toLocalDate());
                l.setStatus(rs.getString("status"));
                l.setFine(rs.getDouble("fine"));
                list.add(l);
            }
        }
        return list;
    }
}
