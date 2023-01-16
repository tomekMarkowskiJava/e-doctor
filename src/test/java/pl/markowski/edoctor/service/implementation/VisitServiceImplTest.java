package pl.markowski.edoctor.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.markowski.edoctor.configuration.TenantContext;
import pl.markowski.edoctor.exception.InvalidTimeOfVisitException;
import pl.markowski.edoctor.exception.NotFoundException;
import pl.markowski.edoctor.mapper.VisitMapper;
import pl.markowski.edoctor.model.dto.DoctorDto;
import pl.markowski.edoctor.model.dto.PatientDto;
import pl.markowski.edoctor.model.dto.VisitDto;
import pl.markowski.edoctor.model.entity.Visit;
import pl.markowski.edoctor.model.enums.SpecializationEnum;
import pl.markowski.edoctor.service.DoctorService;
import pl.markowski.edoctor.service.PatientService;
import pl.markowski.edoctor.service.VisitService;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VisitServiceImplTest {

    PatientService patientService;
    VisitService visitService;
    DoctorService doctorService;

    static PatientDto patient;
    static DoctorDto doctor;
    static VisitDto visit;

    @Autowired
    public VisitServiceImplTest(PatientService patientService, VisitService visitService, DoctorService doctorService) {
        this.patientService = patientService;
        this.visitService = visitService;
        this.doctorService = doctorService;
    }

    @BeforeAll
    static void init() {
        TenantContext.setCurrentTenant("doctor1");
        patient = PatientDto.builder()
                .name("Józef")
                .surname("Chory")
                .address("Głogów, Lipowa")
                .build();


        doctor = DoctorDto.builder()
                .specialization(SpecializationEnum.SURGEON)
                .surname("Kowalski")
                .name("Jan")
                .build();

        visit = VisitDto.builder()
                .date(new Date(1673901381664L))
                .time(LocalTime.now())
                .roomNumber("101")
                .doctor(doctor)
                .patient(patient)
                .build();
    }

    @Test
    @Order(1)
    void saveVisit() {
        patient = patientService.savePatient(patient);
        doctor = doctorService.saveDoctor(doctor);

        visit = visitService.saveVisit(visit);
        Assertions.assertTrue(visit.getId() > 0);
    }

    @Test
    @Order(2)
    void saveVisitWithInvalidTime() {
        VisitDto invalidVisit = VisitDto.builder()
                .date(visit.getDate())
                .time(visit.getTime().plusMinutes(20))
                .roomNumber(visit.getRoomNumber())
                .doctor(visit.getDoctor())
                .patient(visit.getPatient())
                .build();

        Assertions.assertThrows(InvalidTimeOfVisitException.class, () -> visitService.saveVisit(invalidVisit));
    }

    @Test
    @Order(3)
    void updateVisit() {
        Visit tempVisit = VisitMapper.mapToEntity(visit);
        tempVisit.setTime(visit.getTime().plusMinutes(40));
        ;

        Assertions.assertEquals(visit.getTime().plusMinutes(40),
                visitService.updateVisit(VisitMapper.mapToDto(tempVisit)).getTime());
    }

    @Test
    @Order(4)
    void getVisitsForPatient() {
        List<VisitDto> visits = visitService.getVisitsForPatient(visit.getPatient().getId());
        Assertions.assertFalse(visits.isEmpty());
    }

    @Test
    @Order(5)
    void getVisits() {
        List<VisitDto> visits = visitService.getVisits();
        Assertions.assertFalse(visits.isEmpty());
    }

    @Test
    @Order(6)
    void deleteVisit() {
        visitService.deleteVisit(visit.getId());
        Assertions.assertTrue(visitService.getVisitsForPatient(visit.getPatient().getId()).isEmpty());
    }

    @Test
    @Order(7)
    void deletePatientAndDoctor() {
        patientService.deletePatient(patient.getId());
        doctorService.deleteDoctor(doctor.getId());
        Assertions.assertThrows(NotFoundException.class, () -> doctorService.getDoctorDtoById(visit.getDoctor().getId()));
        Assertions.assertThrows(NotFoundException.class, () -> patientService.getPatientDtoById(visit.getPatient().getId()));
    }
}