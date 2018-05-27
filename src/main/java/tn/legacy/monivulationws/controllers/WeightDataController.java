package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.legacy.monivulationws.CustomClasses.DateEntry;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.WeightData;
import tn.legacy.monivulationws.enumerations.DateSearchType;
import tn.legacy.monivulationws.exceptions.NotFoundException;
import tn.legacy.monivulationws.services.UserService;
import tn.legacy.monivulationws.services.WeightDataService;

import java.util.List;

@RestController
public class WeightDataController {

    @Autowired
    private WeightDataService weightDataService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/weightData/add/{id}")
    public String addWeightData (@RequestBody WeightData weightData, @PathVariable int id) throws NotFoundException{
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            weightData.setAppUser(appUser);
            weightDataService.addWeightData(weightData);
            return "success";
        }else{
                throw new NotFoundException("AppUser of Id : "+id+" Not found");
        }
    }

    @RequestMapping(value = "/weightData/getBetween/{id}")
    public List<WeightData> getAllWeightDataBetween(@PathVariable int id, @RequestBody DateEntry dateEntry) throws NotFoundException{
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return weightDataService.getWeightDataBetween(appUser, dateEntry.getStartDate(), dateEntry.getEndDate(),DateSearchType.DayOnly);
        }else{
            throw new NotFoundException("AppUser of Id : "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST,value = "/weightData/getAt/{id}")
    public WeightData getWeightDataAt(@PathVariable int id,@RequestBody WeightData data) throws NotFoundException{

        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return weightDataService.getWeightData(appUser, data.getEntryDate(), DateSearchType.DayOnly);
        }else{
            throw new NotFoundException("AppUser of Id : "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/weightData/getLast/{id}")
    public WeightData getLastWeightData(@PathVariable int id) throws NotFoundException{

        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return weightDataService.getLastWeightData(appUser);
        }else{
            throw new NotFoundException("AppUser of Id : "+id+" Not found");
        }
    }

}
