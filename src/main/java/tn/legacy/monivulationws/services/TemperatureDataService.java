package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.entities.TemperatureData;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.enumerations.DateSearchType;
import tn.legacy.monivulationws.repositories.TemperatureDataRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TemperatureDataService {

    @Autowired
    private TemperatureDataRepository temperatureDataRepository;

    @Autowired
    private StatusService statusService;

    public void addTemperatureData(TemperatureData temperatureData){
        temperatureDataRepository.save(temperatureData);

        statusService.checkStatus(temperatureData);
    }

    public TemperatureData getTemperatureData (User user, LocalDateTime date, DateSearchType dateSearchType){
        switch (dateSearchType){
            case FullDate:
                return temperatureDataRepository.findFirstByUserAndEntryDate(user,date);
            case DayOnly:
                LocalDate dateOnly = date.toLocalDate();
                LocalDateTime startOfTheDay = dateOnly.atStartOfDay();
                LocalDateTime endOfTheDay = dateOnly.atTime(23,59,59);
                return temperatureDataRepository.findFirstByUserAndAndEntryDateBetween(user,startOfTheDay,endOfTheDay);
        }
        return null;
    }

    //return temperature between two dates of a specific user
    public List<TemperatureData> getTemperatureDataBetween(User user, LocalDateTime startDate, LocalDateTime endDate, DateSearchType dateSearchType){
        switch (dateSearchType) {
            case FullDate:
                return temperatureDataRepository.findAllByUserAndAndEntryDateBetween(user,startDate,endDate);
            case DayOnly:
                LocalDate start_DateOnly = startDate.toLocalDate();
                LocalDateTime start_StartOfTheDay = start_DateOnly.atStartOfDay();

                LocalDate end_DateOnly = endDate.toLocalDate();
                LocalDateTime end_EndOfTheDay = end_DateOnly.atTime(23,59,59);

                return temperatureDataRepository.findAllByUserAndAndEntryDateBetween(user,start_StartOfTheDay,end_EndOfTheDay);
        }

        return  null;
    }


}
