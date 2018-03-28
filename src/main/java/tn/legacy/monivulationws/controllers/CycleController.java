package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tn.legacy.monivulationws.CustomClasses.PeriodInfo;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.Cycle;
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
        AppUser appUser = userService.getUser(id);
        String returnMessage = "";
        if (appUser != null){
            newCycle.setAppUser(appUser);
            returnMessage+= cycleService.saveFirstCycle(newCycle);
            returnMessage += " - " + statusService.createFirstStatus(appUser,newCycle.getStartDate());
            return returnMessage;
        }else{
            returnMessage = "AppUser of Id "+id+" Not found";
            throw new NotFoundException(returnMessage);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cycle/startPeriod/{id}")
    public String startCyclePeriod (@PathVariable int id) throws NotFoundException {
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return statusService.confirmStartCycle(appUser);
        }else{
            throw new NotFoundException("AppUser of Id "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cycle/endPeriod/{id}")
    public String endCyclePeriod (@PathVariable int id) throws NotFoundException {
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return cycleService.endCyclePeriod(appUser);
        }else{
            throw new NotFoundException("AppUser of Id "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cycle/getAvgLength/{lengthName}/{id}")
    public float getAverageLength (@PathVariable int id, @PathVariable LengthName lengthName) throws NotFoundException, MethodArgumentTypeMismatchException {
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            switch (lengthName){
                case cycle:
                    return cycleService.getAverageCycleLenght(appUser);
                case period:
                    return cycleService.getAveragePeriodLenght(appUser);
                case follicular:
                    return cycleService.getAverageFollicularLength(appUser);
                case luteal:
                    return cycleService.getAverageLutealLength(appUser);
            }
        }else{
            throw new NotFoundException("AppUser of Id "+id+" Not found");
        }
        return 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cycle/periodInfo/{id}")
    public PeriodInfo getPeriodInfo (@PathVariable int id) throws NotFoundException {
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return cycleService.getPeriodInfo(appUser);
        }else{
            throw new NotFoundException("AppUser of Id "+id+" Not found");
        }
    }
}
