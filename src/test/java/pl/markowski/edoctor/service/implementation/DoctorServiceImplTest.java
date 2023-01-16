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
import pl.markowski.edoctor.mapper.DoctorMapper;
import pl.markowski.edoctor.model.dto.DoctorDto;
import pl.markowski.edoctor.model.entity.Doctor;
import pl.markowski.edoctor.model.enums.SpecializationEnum;
import pl.markowski.edoctor.service.DoctorService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DoctorServiceImplTest {

    @Autowired
    DoctorService service;
    static DoctorDto doctor;

    @BeforeAll
    public static void init() {
        TenantContext.setCurrentTenant("doctor1");
        doctor = DoctorDto.builder()
                .specialization(SpecializationEnum.SURGEON)
                .surname("Kowalski")
                .name("Jan")
                .build();
    }

    @Test
    @Order(1)
    void saveDoctorTest() {
        doctor = service.saveDoctor(doctor);
        Assertions.assertTrue(doctor.getId() > 0);
    }

    @Test
    @Order(2)
    void getDoctorDtoByIdTest() {
        DoctorDto tempDoctor = service.getDoctorDtoById(doctor.getId());
        Assertions.assertEquals(tempDoctor.getId(), doctor.getId());
    }

    @Test
    @Order(3)
    void updateDoctorTest() {
        String newSurname = "Nowy";
        Doctor tempDoctor = DoctorMapper.mapToEntity(doctor);
        tempDoctor.setSurname(newSurname);
        service.updateDoctor(DoctorMapper.mapToDto(tempDoctor));

        doctor = service.getDoctorDtoById(doctor.getId());

        Assertions.assertEquals(doctor.getSurname(), newSurname);
    }

    @Test
    @Order(4)
    void deleteDoctorTest() {
        service.deleteDoctor(doctor.getId());
        Assertions.assertThrows(NotFoundException.class, () -> service.getDoctorDtoById(doctor.getId()));
    }
}