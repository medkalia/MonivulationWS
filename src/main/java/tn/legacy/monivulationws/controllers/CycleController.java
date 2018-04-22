package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tn.legacy.monivulationws.CustomClasses.CycleInfo;
import tn.legacy.monivulationws.CustomClasses.DateEntry;
import tn.legacy.monivulationws.CustomClasses.PeriodInfo;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.enumerations.LengthName;
import tn.legacy.monivulationws.exceptions.NotFoundException;
import tn.legacy.monivulationws.services.CycleService;
import tn.legacy.monivulationws.services.StatusService;
import tn.legacy.monivulationws.services.UserService;

import java.util.List;

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
            returnMessage += " - " + statusService.createFirstStatus(appUser,newCycle);
            return returnMessage;
        }else{
            returnMessage = "AppUser of Id "+id+" Not found";
            throw new NotFoundException(returnMessage);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cycle/startPeriod/{id}")
    public String startCyclePeriod (@RequestBody DateEntry dateEntry, @PathVariable int id) throws NotFoundException {
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return statusService.confirmStartCycle(appUser,dateEntry.getStartDate());
        }else{
            throw new NotFoundException("AppUser of Id "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cycle/endPeriod/{id}")
    public String endCyclePeriod (@RequestBody DateEntry dateEntry,@PathVariable int id) throws NotFoundException {
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return cycleService.endCyclePeriod(appUser,dateEntry.getEndDate());
        }else{
            throw new NotFoundException("AppUser of Id "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cycle/getAll/{id}")
    public List<Cycle> getAllCycle (@PathVariable int id) throws NotFoundException {
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return cycleService.getAllCycle(appUser);
        }else{
            throw new NotFoundException("AppUser of Id "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cycle/infoAt/{id}")
    public CycleInfo getCycleInfoAt (@PathVariable int id,@RequestBody Cycle infoCycle) throws NotFoundException {
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return cycleService.getCycleInfo(appUser,infoCycle.getStartDate());
        }else{
            throw new NotFoundException("AppUser of Id "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cycle/info/{id}")
    public CycleInfo getCycleInfo (@PathVariable int id) throws NotFoundException {
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return cycleService.getCycleInfo(appUser);
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
