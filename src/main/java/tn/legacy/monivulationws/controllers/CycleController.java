package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tn.legacy.monivulationws.CustomClasses.PeriodInfo;
import tn.legacy.monivulationws.Util.DebugUtil;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.enumerations.LengthName;
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

    @RequestMapping(method = RequestMethod.GET, value = "/cycle/getAvgLength/{lengthName}/{id}")
    public float getAverageLength (@PathVariable int id, @PathVariable LengthName lengthName) throws NotFoundException, MethodArgumentTypeMismatchException {
        User user = userService.getUser(id);
        if (user != null){
            switch (lengthName){
                case cycle:
                    return cycleService.getAverageCycleLenght(user);
                case period:
                    return cycleService.getAveragePeriodLenght(user);
                case follicular:
                    return cycleService.getAverageFollicularLength(user);
                case luteal:
                    return cycleService.getAverageLutealLength(user);
            }
        }else{
            throw new NotFoundException("User of Id "+id+" Not found");
        }
        return 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cycle/periodInfo/{id}")
    public PeriodInfo getPeriodInfo (@PathVariable int id) throws NotFoundException {
        User user = userService.getUser(id);
        if (user != null){
            return cycleService.getPeriodInfo(user);
        }else{
            throw new NotFoundException("User of Id "+id+" Not found");
        }
    }
}
