package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tn.legacy.monivulationws.entities.Anomaly;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.exceptions.NotFoundException;
import tn.legacy.monivulationws.services.AnomalyService;
import tn.legacy.monivulationws.services.UserService;

import java.util.List;

@RestController
public class AnomalyController {

    @Autowired
    private AnomalyService anomalyService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/anomaly/getAll/{id}")
    public List<Anomaly> getAllAnomaly (@PathVariable int id) throws NotFoundException {
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return anomalyService.getAllAnomaly(appUser);
        }else{
            throw new NotFoundException("AppUser of Id "+id+" Not found");
        }
    }
}
