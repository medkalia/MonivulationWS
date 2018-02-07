package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.exceptions.NotFoundException;
import tn.legacy.monivulationws.services.CycleService;
import tn.legacy.monivulationws.services.StatusService;
import tn.legacy.monivulationws.services.UserService;

@RestController
public class CycleController {

    @Autowired
    private StatusService statusService;
    @Autowired
    private CycleService cycleService;
    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.POST, value = "/cycle/first/{id}")
    public String addFirstCycle (@RequestBody Cycle newCycle, @PathVariable int id) throws NotFoundException {
        User user = userService.getUser(id);
        if (user != null){
            newCycle.setUser(user);
            cycleService.saveFirstCycle(newCycle);
            statusService.createFirstStatus(user,newCycle.getStartDate());
            return "success";
        }else{
            throw new NotFoundException("User of Id "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cycle/startPeriod/{id}")
    public String startCyclePeriod (@PathVariable int id) throws NotFoundException {
        User user = userService.getUser(id);
        if (user != null){
            statusService.confirmStartCycle(user);
            return "success";
        }else{
            throw new NotFoundException("User of Id "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cycle/endPeriod/{id}")
    public String endCyclePeriod (@PathVariable int id) throws NotFoundException {
        User user = userService.getUser(id);
        if (user != null){
            cycleService.endCyclePeriod(user);
            return "success";
        }else{
            throw new NotFoundException("User of Id "+id+" Not found");
        }
    }
}
