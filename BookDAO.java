package com.library.dao;

import com.library.model.Book;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public void add(Book b) throws SQLException {
        String sql = "INSERT INTO books(title,author,isbn,category,quantity,available_quantity) VALUES(?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getIsbn());
            ps.setString(4, b.getCategory());
            ps.setInt(5, b.getQuantity());
            ps.setInt(6, b.getQuantity());
            ps.executeUpdate();
        }
    }

    public List<Book> findAll() throws SQLException {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY id DESC";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setIsbn(rs.getString("isbn"));
                b.setCategory(rs.getString("category"));
                b.setQuantity(rs.getInt("quantity"));
                b.setAvailableQuantity(rs.getInt("available_quantity"));
                list.add(b);
            }
        }
        return list;
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM books WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
