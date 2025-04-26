package service;

import dao.UserDao;
import model.User;

public class UserService {
    private UserDao userDAO;

    public UserService() {
        this.userDAO = new UserDao();
    }

    public boolean register(String username, String password, int role) {
        return userDAO.register(username, password, role);
    }

    public User login(String username, String password) {
        return userDAO.login(username, password);
    }
}