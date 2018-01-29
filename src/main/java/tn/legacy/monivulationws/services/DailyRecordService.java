package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.entities.DailyRecord;
import tn.legacy.monivulationws.repositories.DailyRecordRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DailyRecordService {

    private static final float ovulationTemperature = 37;
    private static final float varianceTemperature = 0.4f;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;


    public List<DailyRecord> getDailyRecords() {
        List<DailyRecord> dailyRecords = new ArrayList<>();
        dailyRecordRepository.findAll().forEach(dailyRecords::add);
        return dailyRecords;
    }

    public DailyRecord getDailyRecord(int id, Date date) {

        return dailyRecordRepository.findByUserIdAndRecordDate(id, date);

    }


    public String getDailyStatus(DailyRecord dailyRecord) {
        /*List<DailyRecord> dailyRecords = new ArrayList<>();
        dailyRecordRepository.findAll().forEach(dailyRecords::add);*/

        DailyRecord lastRecord = getDailyRecord(1,new Date()); //TODO static values
        if (lastRecord!=null){

            if (dailyRecord.getTemperature()-lastRecord.getTemperature()>varianceTemperature && dailyRecord.getTemperature()>=ovulationTemperature && lastRecord.getTemperature()<ovulationTemperature){
                return "ovulation detected, luteal phase started";
            }
            return "follicular phase";
        }

        return "first record, follicular phase";
    }

}
