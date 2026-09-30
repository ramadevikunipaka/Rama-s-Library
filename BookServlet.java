package com.library.controller;

import com.library.model.Book;
import com.library.service.BookService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private final BookService service = new BookService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, jakarta.servlet.ServletException {
        try {
            req.setAttribute("books", service.findAll());
            req.getRequestDispatcher("/books.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new jakarta.servlet.ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        try {
            Book b = new Book(
                    req.getParameter("title"),
                    req.getParameter("author"),
                    req.getParameter("isbn"),
                    req.getParameter("category"),
                    Integer.parseInt(req.getParameter("quantity")),
                    0);
            service.add(b);
            resp.sendRedirect(req.getContextPath() + "/books");
        } catch (Exception e) {
            throw new jakarta.servlet.ServletException(e);
        }
    }
}
