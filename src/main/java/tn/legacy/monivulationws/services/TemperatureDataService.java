package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.TemperatureData;
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

    @Autowired
    private CycleService cycleService;

    //---------------CRUD---------------
    public String  addTemperatureData(TemperatureData temperatureData){
        if (statusService.getStatus(temperatureData.getAppUser()) != null && cycleService.getCycle(temperatureData.getAppUser()) != null){
            temperatureDataRepository.save(temperatureData);
            return statusService.checkStatus(temperatureData);
        }
        return "No Status Or/And Cycle Created! Please call the addFirstCycle Service before adding other data for user of id " + temperatureData.getAppUser().getId();

    }

    //return temperature at specific date
    public TemperatureData getTemperatureData (AppUser appUser, LocalDateTime date, DateSearchType dateSearchType){
        switch (dateSearchType){
            case FullDate:
                return temperatureDataRepository.findFirstByAppUserAndEntryDate(appUser,date);
            case DayOnly:
                LocalDate dateOnly = date.toLocalDate();
                LocalDateTime startOfTheDay = dateOnly.atStartOfDay();
                LocalDateTime endOfTheDay = dateOnly.atTime(23,59,59);
                return temperatureDataRepository.findFirstByAppUserAndAndEntryDateBetween(appUser,startOfTheDay,endOfTheDay);
        }
        return null;
    }

    //return temperature between two dates of a specific appUser
    public List<TemperatureData> getTemperatureDataBetween(AppUser appUser, LocalDateTime startDate, LocalDateTime endDate, DateSearchType dateSearchType){
        switch (dateSearchType) {
            case FullDate:
                return temperatureDataRepository.findAllByAppUserAndAndEntryDateBetween(appUser,startDate,endDate);
            case DayOnly:
                LocalDate start_DateOnly = startDate.toLocalDate();
                LocalDateTime start_StartOfTheDay = start_DateOnly.atStartOfDay();
                LocalDate end_DateOnly = endDate.toLocalDate();
                LocalDateTime end_EndOfTheDay = end_DateOnly.atTime(23,59,59);

                return temperatureDataRepository.findAllByAppUserAndAndEntryDateBetween(appUser,start_StartOfTheDay,end_EndOfTheDay);
        }

        return  null;
    }
    //------------------------------------------


}
