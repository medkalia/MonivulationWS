package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.Util.DebugUtil;
import tn.legacy.monivulationws.entities.Anomaly;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.enumerations.AnomalyDegree;
import tn.legacy.monivulationws.enumerations.AnomalyName;
import tn.legacy.monivulationws.repositories.AnomalyRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnomalyService {

    //**************************LIMITS
    public static final float PROBLEMATIC_FEVER_TEMPERATURE = 38;
    public static final float CRITICAL_FEVER_TEMPERATURE = 39;
    public static final float MINIMUM_PERIOD_LENGTH = 2;
    public static final float MAXIMUM_PERIOD_LENGTH = 7;
    public static final float MINIMUM_CYCLE_LENGTH = 21;
    public static final float MAXIMUM_CYCLE_LENGTH = 35;

    //**************************DESCRIPTIONS
    public static final String PROBLEMATIC_FEVER_TEMPERATURE_DESCRIPTION = "Temperature is higher than normal";
    public static final String CRITICAL_FEVER_TEMPERATURE_DESCRIPTION = "Temperature is critically higher than normal";
    public static final String PROBLEMATIC_MINIMUM_CYCLE_LENGTH_DESCRIPTION = "Cycle length is short";
    public static final String CRITICAL_MINIMUM_CYCLE_LENGTH_DESCRIPTION = "Cycle length is critically short";
    public static final String PROBLEMATIC_MAXIMUM_CYCLE_LENGTH_DESCRIPTION = "Cycle length is long";
    public static final String CRITICAL_MAXIMUM_CYCLE_LENGTH_DESCRIPTION = "Cycle length is critically long";
    public static final String PROBLEMATIC_MINIMUM_PERIOD_LENGTH_DESCRIPTION = "Period length is short";
    public static final String CRITICAL_MINIMUM_PERIOD_LENGTH_DESCRIPTION = "Period length is critically short";
    public static final String PROBLEMATIC_MAXIMUM_PERIOD_LENGTH_DESCRIPTION = "Period length is long";
    public static final String CRITICAL_MAXIMUM_PERIOD_LENGTH_DESCRIPTION = "Period length is critically long";

    //**************************ADVICES
    public static final String PROBLEMATIC_FEVER_TEMPERATURE_ADVICE = "Check if your temperature gets to normal in the next days";
    public static final String CRITICAL_FEVER_TEMPERATURE_ADVICE = "39° and higher is a sign of a fever please consult your doctor";
    public static final String PROBLEMATIC_MINIMUM_CYCLE_LENGTH_ADVICE = "A diet high in fiber can cause an increase in irregular periods. Fiber is believed to remove excess estrogen, which is required to regulate your menstrual cycle. Dietary fat also might help to control your monthly cycle. Fat helps to increase your body’s production of estrogen and regulate your menstrual cycle. A sudden change in weight affects your menstrual cycle. Women with eating disorders might experience missed periods because of a loss of body fat, which reduces estrogen production.";
    public static final String CRITICAL_MINIMUM_CYCLE_LENGTH_ADVICE = "Consult your gynecologist to rule out any medical issues";
    public static final String PROBLEMATIC_MAXIMUM_CYCLE_LENGTH_ADVICE = " A long cycle often means that the ovaries aren’t producing hormonal events on a regular basis, perhaps running on a delayed schedule. Delayed ovulation can be caused by stress, anxiety or a hormonal imbalance. In some cases, a physical illness or too much exercise can cause delayed ovulation.";
    public static final String CRITICAL_MAXIMUM_CYCLE_LENGTH_ADVICE = "Consult your gynecologist to rule out any medical issues";
    public static final String PROBLEMATIC_MINIMUM_PERIOD_LENGTH_ADVICE = "The length of your period all depends on how much estrogen you produce. If your body doesn’t make a lot of it, your uterine lining won’t be very thick and, when it sheds, the bleeding is light and only lasts for a few days.";
    public static final String CRITICAL_MINIMUM_PERIOD_LENGTH_ADVICE = "Consult your gynecologist to rule out any medical issues";
    public static final String PROBLEMATIC_MAXIMUM_PERIOD_LENGTH_ADVICE = " it’s often the result of normal hormonal fluctuations and not something to worry about. If you’ve experienced a sudden change in the number of days you menstruate, it’s always wise to check with a doctor to find out if there’s a medical issue behind it. If your period has always run long, mention it at your next gyno exam.";
    public static final String CRITICAL_MAXIMUM_PERIOD_LENGTH_ADVICE = " it’s often the result of normal hormonal fluctuations and not something to worry about. If you’ve experienced a sudden change in the number of days you menstruate, it’s always wise to check with a doctor to find out if there’s a medical issue behind it. If your period has always run long, mention it at your next gyno exam.";

    @Autowired
    private AnomalyRepository anomalyRepository;

    public void checkTempForAnomaly (float tempValue, Cycle cycle, LocalDateTime date){
        if (tempValue >= PROBLEMATIC_FEVER_TEMPERATURE){
            Anomaly newAnomaly = new Anomaly();
            newAnomaly.setAppUser(cycle.getAppUser());
            newAnomaly.setName(AnomalyName.Fever);
            newAnomaly.setStartDate(date);
            newAnomaly.setCycle(cycle);
            if (tempValue >= CRITICAL_FEVER_TEMPERATURE){
                newAnomaly.setAnomalyDegree(AnomalyDegree.Critical);
                newAnomaly.setDescription(CRITICAL_FEVER_TEMPERATURE_DESCRIPTION);
                newAnomaly.setAdvice(CRITICAL_FEVER_TEMPERATURE_ADVICE);
            }else{
                newAnomaly.setDescription(PROBLEMATIC_FEVER_TEMPERATURE_DESCRIPTION);
                newAnomaly.setAdvice(PROBLEMATIC_FEVER_TEMPERATURE_ADVICE);
            }
            anomalyRepository.save(newAnomaly);
            DebugUtil.logError("Temperature anomaly detected and saved");
        }
    }

    public void checkCycleLengthForAnomaly (Cycle cycle, LocalDateTime date){
        if (cycle.getLength()  <= MINIMUM_CYCLE_LENGTH || cycle.getLength()  >= MAXIMUM_CYCLE_LENGTH){
            Anomaly newAnomaly = new Anomaly();
            newAnomaly.setAppUser(cycle.getAppUser());
            newAnomaly.setStartDate(date);
            newAnomaly.setCycle(cycle);
            if (cycle.getLength()  <= MINIMUM_CYCLE_LENGTH){
                newAnomaly.setName(AnomalyName.Polymenorrhea);
                if (cycle.getLength() == MINIMUM_CYCLE_LENGTH){
                    newAnomaly.setDescription(PROBLEMATIC_MINIMUM_CYCLE_LENGTH_DESCRIPTION);
                    newAnomaly.setAdvice(PROBLEMATIC_MINIMUM_CYCLE_LENGTH_ADVICE);
                }else{
                    newAnomaly.setAnomalyDegree(AnomalyDegree.Critical);
                    newAnomaly.setDescription(CRITICAL_MINIMUM_CYCLE_LENGTH_DESCRIPTION);
                    newAnomaly.setAdvice(CRITICAL_MINIMUM_CYCLE_LENGTH_ADVICE);
                }
            }else{
                newAnomaly.setName(AnomalyName.Oligomenorrhea);
                if (cycle.getLength() == MAXIMUM_CYCLE_LENGTH){
                    newAnomaly.setDescription(PROBLEMATIC_MAXIMUM_CYCLE_LENGTH_DESCRIPTION);
                    newAnomaly.setAdvice(PROBLEMATIC_MAXIMUM_CYCLE_LENGTH_ADVICE);
                }else{
                    newAnomaly.setAnomalyDegree(AnomalyDegree.Critical);
                    newAnomaly.setDescription(CRITICAL_MAXIMUM_CYCLE_LENGTH_DESCRIPTION);
                    newAnomaly.setAdvice(CRITICAL_MAXIMUM_CYCLE_LENGTH_ADVICE);
                }
            }
            anomalyRepository.save(newAnomaly);
            DebugUtil.logError("Cycle length  anomaly detected and saved");
        }
    }

    public void checkPeriodLengthForAnomaly (Cycle cycle, LocalDateTime date){
        if (cycle.getPeriodLength() <= MINIMUM_PERIOD_LENGTH || cycle.getPeriodLength() >= MAXIMUM_PERIOD_LENGTH ){
            Anomaly newAnomaly = new Anomaly();
            newAnomaly.setAppUser(cycle.getAppUser());
            newAnomaly.setStartDate(date);
            newAnomaly.setCycle(cycle);
            if (cycle.getPeriodLength()  <= MINIMUM_PERIOD_LENGTH){
                newAnomaly.setName(AnomalyName.ShortPeriod);
                if (cycle.getPeriodLength() == MINIMUM_PERIOD_LENGTH){
                    newAnomaly.setDescription(PROBLEMATIC_MINIMUM_PERIOD_LENGTH_DESCRIPTION);
                    newAnomaly.setAdvice(PROBLEMATIC_MINIMUM_PERIOD_LENGTH_ADVICE);
                }else{
                    newAnomaly.setAnomalyDegree(AnomalyDegree.Critical);
                    newAnomaly.setDescription(CRITICAL_MINIMUM_PERIOD_LENGTH_DESCRIPTION);
                    newAnomaly.setAdvice(CRITICAL_MINIMUM_PERIOD_LENGTH_ADVICE);
                }
            }else{
                newAnomaly.setName(AnomalyName.LongPeriod);
                if (cycle.getLength() == MAXIMUM_PERIOD_LENGTH){
                    newAnomaly.setDescription(PROBLEMATIC_MAXIMUM_PERIOD_LENGTH_DESCRIPTION);
                    newAnomaly.setAdvice(PROBLEMATIC_MAXIMUM_PERIOD_LENGTH_ADVICE);
                }else{
                    newAnomaly.setAnomalyDegree(AnomalyDegree.Critical);
                    newAnomaly.setDescription(CRITICAL_MAXIMUM_PERIOD_LENGTH_DESCRIPTION);
                    newAnomaly.setAdvice(CRITICAL_MAXIMUM_PERIOD_LENGTH_ADVICE);
                }
            }
            anomalyRepository.save(newAnomaly);
            DebugUtil.logError("Period length  anomaly detected and saved");
        }
    }

    public List<Anomaly> getAllAnomaly (AppUser appUser){
        return anomalyRepository.getAllByAppUser(appUser);
    }

}
