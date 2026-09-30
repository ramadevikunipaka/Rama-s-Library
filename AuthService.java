package com.library.service;

import com.library.dao.UserDAO;
import com.library.model.User;
import java.sql.SQLException;

public class AuthService {
    private final UserDAO dao = new UserDAO();

    public User login(String username, String password) throws SQLException {
        return dao.login(username, password);
    }
}
