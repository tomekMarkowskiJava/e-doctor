package pl.markowski.edoctor.service.implementation;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.markowski.edoctor.model.entity.Doctor;
import pl.markowski.edoctor.model.entity.Patient;
import pl.markowski.edoctor.model.entity.Visit;
import pl.markowski.edoctor.model.enums.SpecializationEnum;
import pl.markowski.edoctor.repository.DoctorRepository;
import pl.markowski.edoctor.repository.PatientRepository;
import pl.markowski.edoctor.repository.VisitRepository;
import pl.markowski.edoctor.service.DoctorService;
import pl.markowski.edoctor.service.PatientService;
import pl.markowski.edoctor.service.VisitService;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Service
public class InitService {

    private DoctorService doctorService;
    private PatientService patientService;
    private VisitService visitService;

    @Autowired
    public InitService(DoctorService doctorService, PatientService patientService, VisitService visitService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.visitService = visitService;
    }

    @PostConstruct
    private void init(){
        Patient newPatient = Patient.builder()
                .name("Jan")
                .surname("Kowalski")
                .address("Lipowa 1, Rumia")
                .build();

        Doctor newDoctor = Doctor.builder()
                .name("Wiktor")
                .surname("Frankenstein")
                .specialization(SpecializationEnum.SURGEON)
                .build();

        patientService.savePatient(newPatient);
        doctorService.saveDoctor(newDoctor);

        Visit newVisit = Visit.builder()
                .patient(newPatient)
                .doctor(newDoctor)
                .date(Date.valueOf(LocalDate.now()))
                .time(Time.valueOf(LocalTime.now()))
                .roomNumber("106a")
                .build();

        visitService.saveVisit(newVisit);
        log.info("Visit saved");
    }
}
