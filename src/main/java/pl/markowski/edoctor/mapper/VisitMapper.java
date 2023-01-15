package pl.markowski.edoctor.mapper;

import pl.markowski.edoctor.model.dto.VisitDto;

import pl.markowski.edoctor.model.entity.Visit;

public class VisitMapper {

    public static VisitDto mapToDto(Visit visit) {
        return VisitDto.builder()
                .id(visit.getId())
                .date(visit.getDate())
                .time(visit.getTime())
                .doctor(DoctorMapper.mapToDto(visit.getDoctor()))
                .patient(PatientMapper.mapToDto(visit.getPatient()))
                .roomNumber(visit.getRoomNumber())
                .build();
    }

    public static Visit mapToEntity(VisitDto dto) {
        return Visit.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .time(dto.getTime())
                .patient(PatientMapper.mapToEntity(dto.getPatient()))
                .doctor(DoctorMapper.mapToEntity(dto.getDoctor()))
                .roomNumber(dto.getRoomNumber())
                .build();
    }
}
