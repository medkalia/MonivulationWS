package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.entities.DailyRecord;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.repositories.DailyRecordRepository;
import tn.legacy.monivulationws.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyRecordService {

    @Autowired
    private DailyRecordRepository dailyRecordRepository;


    public List<DailyRecord> getDailyRecords() {
        List<DailyRecord> dailyRecords = new ArrayList<>();
        dailyRecordRepository.findAll().forEach(dailyRecords::add);
        return dailyRecords;
    }
}
