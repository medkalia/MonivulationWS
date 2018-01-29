package tn.legacy.monivulationws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import tn.legacy.monivulationws.services.TemperatureDataService;

@RestController
public class TemperatureDataController {
    @Autowired
    private TemperatureDataService temperatureDataService;


}
