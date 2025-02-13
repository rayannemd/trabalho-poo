package utils;

import model.User;
import model.Role;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private static final String USERS_FILE_PATH = "./resources/users.txt";

    public static void saveUsers(List<User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH))) {
            for (User user : users) {
                writer.write(user.getUsername() + ";" + user.getPassword() + ";" + user.getEmail() + ";" + user.getRole().getName());
                writer.newLine();
            }
        }
    }

    public static List<User> loadUsers() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                Role role = new Role(data[3], null); 
                User user = new User(data[0], data[1], data[2], role);
                users.add(user);
            }
        }
        return users;
    }
}
