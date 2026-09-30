package com.library.controller;

import com.library.service.LoanService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/loans")
public class LoanServlet extends HttpServlet {
    private final LoanService service = new LoanService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, jakarta.servlet.ServletException {
        try {
            req.setAttribute("loans", service.findAll());
            req.getRequestDispatcher("/loans.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new jakarta.servlet.ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        try {
            String action = req.getParameter("action");
            if ("issue".equals(action)) {
                service.issue(
                        Integer.parseInt(req.getParameter("bookId")),
                        Integer.parseInt(req.getParameter("memberId")),
                        LocalDate.parse(req.getParameter("issueDate")),
                        LocalDate.parse(req.getParameter("dueDate")));
            } else if ("return".equals(action)) {
                service.returnBook(
                        Integer.parseInt(req.getParameter("loanId")),
                        LocalDate.parse(req.getParameter("returnDate")));
            }
            resp.sendRedirect(req.getContextPath() + "/loans");
        } catch (Exception e) {
            throw new jakarta.servlet.ServletException(e);
        }
    }
}
