package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.legacy.monivulationws.CustomClasses.DateEntry;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.BpmData;
import tn.legacy.monivulationws.enumerations.DateSearchType;
import tn.legacy.monivulationws.exceptions.NotFoundException;
import tn.legacy.monivulationws.services.BpmDataService;
import tn.legacy.monivulationws.services.UserService;

import java.util.List;

@RestController
public class BpmDataController {


    @Autowired
    private BpmDataService bpmDataService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/bpmData/add/{id}")
    public String addBpmData (@RequestBody BpmData bpmData, @PathVariable int id) throws NotFoundException {
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            bpmData.setAppUser(appUser);
            bpmDataService.addBpmData(bpmData);
            return "BPM with value " + bpmData.getValue() + " added with success for user of id " + appUser.getId() ;
        }else{
            throw new NotFoundException("AppUser of Id : "+id+" Not found");
        }
    }

    @RequestMapping(value = "/bpmData/getBetween/{id}")
    public List<BpmData> getAllBpmDataBetween(@PathVariable int id, @RequestBody DateEntry dateEntry) throws NotFoundException{
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return bpmDataService.getBpmDataBetween(appUser, dateEntry.getStartDate(), dateEntry.getEndDate(), DateSearchType.DayOnly);
        }else{
            throw new NotFoundException("AppUser of Id : "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST,value = "/bpmData/getAt/{id}")
    public BpmData getBpmDataAt(@PathVariable int id,@RequestBody BpmData data) throws NotFoundException{

        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return bpmDataService.getBpmData(appUser, data.getEntryDate(), DateSearchType.DayOnly);
        }else{
            throw new NotFoundException("AppUser of Id : "+id+" Not found");
        }
    }
}
