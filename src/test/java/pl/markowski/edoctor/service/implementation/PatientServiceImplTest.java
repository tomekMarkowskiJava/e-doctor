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
import pl.markowski.edoctor.exception.NotFoundException;
import pl.markowski.edoctor.mapper.PatientMapper;
import pl.markowski.edoctor.model.dto.PatientDto;
import pl.markowski.edoctor.model.entity.Patient;
import pl.markowski.edoctor.service.PatientService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PatientServiceImplTest {

    @Autowired
    PatientService service;
    static PatientDto patient;

    @BeforeAll
    public static void init() {
        TenantContext.setCurrentTenant("doctor1");
        patient = PatientDto.builder()
                .name("Józef")
                .surname("Chory")
                .address("Głogów, Lipowa")
                .build();
    }

    @Test
    @Order(1)
    void savePatientTest() {
        patient = service.savePatient(patient);
        Assertions.assertTrue(patient.getId() > 0);
    }

    @Test
    @Order(2)
    void getPatientDtoByIdTest() {
        PatientDto tempPatient = service.getPatientDtoById(patient.getId());
        Assertions.assertEquals(tempPatient.getId(), patient.getId());
    }

    @Test
    @Order(3)
    void updatePatientTest() {
        String newAddress = "Zgierz, Łódzka";
        Patient tempPatient = PatientMapper.mapToEntity(patient);
        tempPatient.setAddress(newAddress);
        service.updatePatient(PatientMapper.mapToDto(tempPatient));

        patient = service.getPatientDtoById(patient.getId());

        Assertions.assertEquals(patient.getAddress(), newAddress);
    }

    @Test
    @Order(4)
    void deletePatientTest() {
        service.deletePatient(patient.getId());
        Assertions.assertThrows(NotFoundException.class, () -> service.getPatientDtoById(patient.getId()));
    }
}