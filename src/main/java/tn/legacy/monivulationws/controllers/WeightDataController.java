package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.legacy.monivulationws.CustomClasses.StartEndDate;
import tn.legacy.monivulationws.entities.WeightData;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.entities.WeightData;
import tn.legacy.monivulationws.enumerations.DateSearchType;
import tn.legacy.monivulationws.exceptions.NotFoundException;
import tn.legacy.monivulationws.services.TemperatureDataService;
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
        User user = userService.getUser(id);
        if (user != null){
            weightData.setUser(user);
            weightDataService.addWeightData(weightData);
            return "success";
        }else{
                throw new NotFoundException("User of Id : "+id+" Not found");
        }
    }

    @RequestMapping(value = "/weightData/getBetween/{id}")
    public List<WeightData> getAllWeightDataBetween(@PathVariable int id, @RequestBody StartEndDate startEndDate) throws NotFoundException{
        User user = userService.getUser(id);
        if (user != null){
            return weightDataService.getWeightDataBetween(user,startEndDate.getStartDate(),startEndDate.getEndDate(),DateSearchType.DayOnly);
        }else{
            throw new NotFoundException("User of Id : "+id+" Not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST,value = "/weightData/getAt/{id}")
    public WeightData getWeightDataAt(@PathVariable int id,@RequestBody WeightData data) throws NotFoundException{

        User user = userService.getUser(id);
        if (user != null){
            return weightDataService.getWeightData(user, data.getEntryDate(), DateSearchType.DayOnly);
        }else{
            throw new NotFoundException("User of Id : "+id+" Not found");
        }

    }

}
