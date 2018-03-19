package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.legacy.monivulationws.CustomClasses.Login;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.services.UserService;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users")
    public List<AppUser> getAllUsers(){
        return userService.getUsers();
    }

    @RequestMapping(value = "/users/{id}")
    public AppUser getUser(@PathVariable int id){
        return userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public AppUser addUser(@RequestBody AppUser user){
        return userService.addUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users")
    public String updateUser(@RequestBody AppUser user){
        return userService.updateUser(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
    public String deleteUser(@PathVariable int id){
        return userService.deleteUser(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public AppUser login(@RequestBody Login login){
        return userService.login(login);
    }
}
