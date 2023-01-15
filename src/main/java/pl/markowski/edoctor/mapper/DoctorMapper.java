package pl.markowski.edoctor.mapper;

import pl.markowski.edoctor.model.dto.DoctorDto;
import pl.markowski.edoctor.model.entity.Doctor;


public class DoctorMapper {
    public static DoctorDto mapToDto(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .specialization(doctor.getSpecialization())
                .build();
    }

    public static Doctor mapToEntity(DoctorDto dto) {
        return Doctor.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .specialization(dto.getSpecialization())
                .build();
    }
}
