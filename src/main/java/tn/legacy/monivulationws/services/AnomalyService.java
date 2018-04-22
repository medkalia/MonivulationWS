package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.repositories.AnomalyRepository;

@Service
public class AnomalyService {

    @Autowired
    private AnomalyRepository anomalyRepository;
}
