package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.BpmData;
import tn.legacy.monivulationws.enumerations.DateSearchType;
import tn.legacy.monivulationws.repositories.BpmDataRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BpmDataService {

    @Autowired
    private BpmDataRepository bpmDataRepository;

    public void  addBpmData(BpmData bpmData){
        bpmDataRepository.save(bpmData);
    }

    //return temperature at specific date
    public BpmData getBpmData (AppUser appUser, LocalDateTime date, DateSearchType dateSearchType){
        switch (dateSearchType){
            case FullDate:
                return bpmDataRepository.findFirstByAppUserAndEntryDate(appUser,date);
            case DayOnly:
                LocalDate dateOnly = date.toLocalDate();
                LocalDateTime startOfTheDay = dateOnly.atStartOfDay();
                LocalDateTime endOfTheDay = dateOnly.atTime(23,59,59);
                return bpmDataRepository.findFirstByAppUserAndAndEntryDateBetween(appUser,startOfTheDay,endOfTheDay);
        }
        return null;
    }

    //return temperature between two dates of a specific appUser
    public List<BpmData> getBpmDataBetween(AppUser appUser, LocalDateTime startDate, LocalDateTime endDate, DateSearchType dateSearchType){
        switch (dateSearchType) {
            case FullDate:
                return bpmDataRepository.findAllByAppUserAndAndEntryDateBetween(appUser,startDate,endDate);
            case DayOnly:
                LocalDate start_DateOnly = startDate.toLocalDate();
                LocalDateTime start_StartOfTheDay = start_DateOnly.atStartOfDay();
                LocalDate end_DateOnly = endDate.toLocalDate();
                LocalDateTime end_EndOfTheDay = end_DateOnly.atTime(23,59,59);

                return bpmDataRepository.findAllByAppUserAndAndEntryDateBetween(appUser,start_StartOfTheDay,end_EndOfTheDay);
        }

        return  null;
    }
}
