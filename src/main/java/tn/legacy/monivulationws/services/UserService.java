package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //hard coded data (for crud test)
    private List<User> users = new ArrayList<>(Arrays.asList(
            new User(1,"mohamed","ben salah"),
                new User(2,"ali","ben mohsen")
    ));

    // CRUD

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;

        //return users for hard coded data
    }

    public User getUser(int id) {
        return userRepository.findOne(id);
        //return users.stream().filter(t->t.getId()==id).findFirst().get();     ==> this is for hard coded data
    }

    public void addUser(User user){
        userRepository.save(user);
        //hard coded data
        //users.add(user);
    }

    public void updateUser(int id, User user) {

        //save does insert and update

        userRepository.save(user);

        //for hard coded data
        /*for (int i=0; i<users.size();i++) {
            User u = users.get(i);
            if (u.getId()==id){
                users.set(i,user);
            }
        }*/
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
        //hard coded data
        //users.removeIf(t->t.getId()==id);
    }
}
