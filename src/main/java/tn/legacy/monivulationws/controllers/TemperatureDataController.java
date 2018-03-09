package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.legacy.monivulationws.CustomClasses.StartEndDate;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.TemperatureData;
import tn.legacy.monivulationws.enumerations.DateSearchType;
import tn.legacy.monivulationws.exceptions.NotFoundException;
import tn.legacy.monivulationws.services.TemperatureDataService;
import tn.legacy.monivulationws.services.UserService;

import java.util.List;

@RestController
public class TemperatureDataController {

    @Autowired
    private TemperatureDataService temperatureDataService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/temperatureData/add/{id}")
    public String addTemperatureData (@RequestBody TemperatureData temperatureData, @PathVariable int id) throws NotFoundException{
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            temperatureData.setAppUser(appUser);
            temperatureDataService.addTemperatureData(temperatureData);
            return "success";
        }else{
                throw new NotFoundException("AppUser of Id : "+id+" Not found");
        }
    }

    @RequestMapping(value = "/temperatureData/getBetween/{id}")
    public List<TemperatureData> getAllTemperatureDataBetween(@PathVariable int id, @RequestBody StartEndDate startEndDate) throws NotFoundException{
        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return temperatureDataService.getTemperatureDataBetween(appUser,startEndDate.getStartDate(),startEndDate.getEndDate(),DateSearchType.DayOnly);
        }else{
            throw new NotFoundException("AppUser of Id : "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST,value = "/temperatureData/getAt/{id}")
    public TemperatureData getTemperatureDataAt(@PathVariable int id,@RequestBody TemperatureData data) throws NotFoundException{

        AppUser appUser = userService.getUser(id);
        if (appUser != null){
            return temperatureDataService.getTemperatureData(appUser, data.getEntryDate(), DateSearchType.DayOnly);
        }else{
            throw new NotFoundException("AppUser of Id : "+id+" Not found");
        }

    }

}
