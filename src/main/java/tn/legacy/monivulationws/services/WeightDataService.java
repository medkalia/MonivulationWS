package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.entities.WeightData;
import tn.legacy.monivulationws.enumerations.DateSearchType;
import tn.legacy.monivulationws.repositories.WeightDataRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeightDataService {


    @Autowired
    private WeightDataRepository weightDataRepository;

    //---------------CRUD---------------
    public void addWeightData (WeightData weightData){
        weightDataRepository.save(weightData);
    }

    //return weight at specific date
    public WeightData getWeightData (User user, LocalDateTime date, DateSearchType dateSearchType){
        switch (dateSearchType){
            case FullDate:
                return weightDataRepository.findFirstByUserAndEntryDate(user,date);
            case DayOnly:
                LocalDate dateOnly = date.toLocalDate();
                LocalDateTime startOfTheDay = dateOnly.atStartOfDay();
                LocalDateTime endOfTheDay = dateOnly.atTime(23,59,59);
                return weightDataRepository.findFirstByUserAndAndEntryDateBetween(user,startOfTheDay,endOfTheDay);
        }
        return null;
    }

    //return weight between two dates of a specific user
    public List<WeightData> getWeightDataBetween(User user, LocalDateTime startDate, LocalDateTime endDate, DateSearchType dateSearchType){
        switch (dateSearchType) {
            case FullDate:
                return weightDataRepository.findAllByUserAndAndEntryDateBetween(user,startDate,endDate);
            case DayOnly:
                LocalDate start_DateOnly = startDate.toLocalDate();
                LocalDateTime start_StartOfTheDay = start_DateOnly.atStartOfDay();

                LocalDate end_DateOnly = endDate.toLocalDate();
                LocalDateTime end_EndOfTheDay = end_DateOnly.atTime(23,59,59);

                return weightDataRepository.findAllByUserAndAndEntryDateBetween(user,start_StartOfTheDay,end_EndOfTheDay);
        }

        return  null;
    }
    //------------------------------------------

}
