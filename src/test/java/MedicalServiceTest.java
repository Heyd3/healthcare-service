import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.*;
import ru.netology.patient.repository.*;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalServiceTest {
    PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
    SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
    MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);

    @BeforeEach
    public void init(){
        String str = "1";
        Mockito.when(patientInfoRepository.add(Mockito.any(PatientInfo.class))).thenReturn(str);
        Mockito.when(patientInfoRepository.getById(str)).thenReturn(new PatientInfo(str,"Семен", "Михайлов", LocalDate.of(1982, 1, 16),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(125, 78))));
    }

    @Test
     void checkBloodPressureTest(){
        BloodPressure currentPressure = new BloodPressure(120, 80);
        medicalService.checkBloodPressure("1", currentPressure);

        Mockito.verify(sendAlertService, Mockito.only()).send("Warning, patient with id: 1, need help");

    }

    @Test
    void checkTemperature(){
        BigDecimal currentTemperature = new BigDecimal("34.6");
        medicalService.checkTemperature("1", currentTemperature);

        Mockito.verify(sendAlertService, Mockito.only()).send("Warning, patient with id: 1, need help");

    }

    @Test
    void checkBloodPressureTest1(){
        BloodPressure currentPressure = new BloodPressure(125, 78);
        medicalService.checkBloodPressure("1", currentPressure);

        Mockito.verify(sendAlertService, Mockito.never()).send("Warning, patient with id: 1, need help");
    }

    @Test
    void checkTemperature1(){
        BigDecimal currentTemperature = new BigDecimal("36.6");
        medicalService.checkTemperature("1", currentTemperature);

        Mockito.verify(sendAlertService, Mockito.never()).send("Warning, patient with id: 1, need help");

    }

}
