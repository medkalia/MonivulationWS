package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.entities.TemperatureData;
import tn.legacy.monivulationws.repositories.TemperatureDataRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemperatureDataService {

    @Autowired
    private TemperatureDataRepository temperatureDataRepository;

    public List<TemperatureData> getTemperatureDataArray() {
        List<TemperatureData> temperatureDataArray = new ArrayList<>();
        temperatureDataRepository.findAll().forEach(temperatureDataArray::add);
        return temperatureDataArray;

    }

    public TemperatureData getTemperatureData(int id) {
        return temperatureDataRepository.findOne(id);

    }

    public void addTemperatureData(TemperatureData temperatureData){
        temperatureDataRepository.save(temperatureData);

    }

    public void updateTemperatureData(TemperatureData temperatureData) {

        temperatureDataRepository.save(temperatureData);

    }

    public void deleteTemperatureData(int id) {

        temperatureDataRepository.delete(id);
    }

}
