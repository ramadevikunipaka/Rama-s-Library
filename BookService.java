package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.Book;
import java.sql.SQLException;
import java.util.List;

public class BookService {
    private final BookDAO dao = new BookDAO();

    public void add(Book book) throws SQLException { dao.add(book); }
    public List<Book> findAll() throws SQLException { return dao.findAll(); }
    public void delete(int id) throws SQLException { dao.delete(id); }
}
