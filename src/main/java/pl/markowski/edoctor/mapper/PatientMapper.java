package pl.markowski.edoctor.mapper;

import pl.markowski.edoctor.model.dto.PatientDto;
import pl.markowski.edoctor.model.entity.Patient;

public class PatientMapper {

    public static PatientDto mapToDto(Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .surname(patient.getSurname())
                .address(patient.getAddress())
                .build();
    }

    public static Patient mapToEntity(PatientDto dto) {
        return Patient.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .address(dto.getAddress())
                .build();
    }
}
