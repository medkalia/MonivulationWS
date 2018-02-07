package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.legacy.monivulationws.CustomClasses.Login;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.services.UserService;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    @RequestMapping(value = "/users/{id}")
    public User getUser(@PathVariable int id){
        return userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public void updateUser(@RequestBody User user, @PathVariable int id){
        userService.updateUser(id, user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public boolean login(@RequestBody Login login){
        return userService.login(login);
    }
}
