package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Dmitry", "Lebedev", (byte) 22);
        userService.saveUser("Semen", "Mars", (byte) 20);
        userService.saveUser("Julio", "Snikers", (byte) 40);
        userService.saveUser("Pes", "Komissar", (byte) 60);
        System.out.println(userService.getAllUsers());
//        userService.cleanUsersTable();
//        userService.dropUsersTable();
    }
}

