package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.CustomClasses.Login;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // CRUD

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User login(Login login) {
        User user = userRepository.findUserByEmail(login.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(login.getPassword(), user.getPassword())){
            return user;
        }
        return null;

    }

    public User getUser(int id) {
        return userRepository.findOne(id);
        //return users.stream().filter(t->t.getId()==id).findFirst().get();     ==> this is for hard coded data
    }

    public String addUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User addedUser = userRepository.save(user);
        if (addedUser!=null)
            return "success";
        return "failed to create a new user";

    }

    public String updateUser(User user) {

        //save does insert and update
        User updatedUser = userRepository.save(user);
        if (updatedUser!=null)
            return "success";
        return "failed to update user";

    }

    public String deleteUser(int id) {
        User userToDelete = userRepository.findOne(id);
        if (userToDelete!=null)
        {
            userRepository.delete(id);
            return "success";
        }
        return "failed to delete user";




        //hard coded data
        //users.removeIf(t->t.getId()==id);
    }
}
