package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.legacy.monivulationws.entities.DailyRecord;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.services.DailyRecordService;
import tn.legacy.monivulationws.services.UserService;

import java.util.List;

@RestController
public class DailyRecordController {
    @Autowired
    private DailyRecordService dailyRecordService;

    @RequestMapping(value = "/dailyrecords")
    public List<DailyRecord> getAllDailyRecords(){
        return dailyRecordService.getDailyRecords();
    }
}
