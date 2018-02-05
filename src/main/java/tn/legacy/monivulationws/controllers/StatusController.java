package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.DailyRecord;
import tn.legacy.monivulationws.entities.TemperatureData;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.exceptions.NotFoundException;
import tn.legacy.monivulationws.services.CycleService;
import tn.legacy.monivulationws.services.DailyRecordService;
import tn.legacy.monivulationws.services.StatusService;
import tn.legacy.monivulationws.services.UserService;

import java.util.List;

@RestController
public class StatusController {

    @Autowired
    private StatusService statusService;
    @Autowired
    private CycleService cycleService;
    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.POST, value = "/Status/first/{id}")
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
}
