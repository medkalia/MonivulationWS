package tn.legacy.monivulationws.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.legacy.monivulationws.CustomClasses.StartEndDate;
import tn.legacy.monivulationws.Util.DateUtil;
import tn.legacy.monivulationws.entities.TemperatureData;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.enumerations.DateSearchType;
import tn.legacy.monivulationws.exceptions.NotFoundException;
import tn.legacy.monivulationws.services.TemperatureDataService;
import tn.legacy.monivulationws.services.UserService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TemperatureDataController {

    @Autowired
    private TemperatureDataService temperatureDataService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/temperatureData/add/{id}")
    public String addTemperatureData (@RequestBody TemperatureData temperatureData, @PathVariable int id) throws NotFoundException{
        User user = userService.getUser(id);
        if (user != null){
            temperatureData.setUser(user);
            temperatureDataService.addTemperatureData(temperatureData);
            return "success";
        }else{
                throw new NotFoundException("User of Id : "+id+" Not found");
        }
    }

    @RequestMapping(value = "/temperatureData/getBetween/{id}")
    public List<TemperatureData> getAllTemperatureDataBetween(@PathVariable int id, @RequestBody StartEndDate startEndDate) throws NotFoundException{
        User user = userService.getUser(id);
        if (user != null){
            return temperatureDataService.getTemperatureDataBetween(user,startEndDate.getStartDate(),startEndDate.getEndDate(),DateSearchType.DayOnly);
        }else{
            throw new NotFoundException("User of Id : "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST,value = "/temperatureData/getAt/{id}")
    public TemperatureData getTemperatureDataAt(@PathVariable int id,@RequestBody TemperatureData data) throws NotFoundException{

        User user = userService.getUser(id);
        if (user != null){
            return temperatureDataService.getTemperatureData(user, data.getEntryDate(), DateSearchType.DayOnly);
        }else{
            throw new NotFoundException("User of Id : "+id+" Not found");
        }

    }

}
