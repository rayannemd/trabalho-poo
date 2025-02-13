package service;

import model.User;
import utils.FileUtil;
import service.exceptions.UserNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthService {

    private List<User> users;

    public AuthService() {
        try {
            this.users = FileUtil.loadUsers(); 
        } catch (IOException e) {
            System.out.println("Erro ao carregar os usuários: " + e.getMessage());
            this.users = new ArrayList<>();
        }
    }

    public User authenticate(String username, String password) throws UserNotFoundException {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new UserNotFoundException("Usuário ou senha inválidos.");
    }

    public void addUser(User user) throws IOException {
        users.add(user);
        FileUtil.saveUsers(users); 
    }

    public boolean userExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
