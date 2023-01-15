package pl.markowski.edoctor.model.dto;

import lombok.Builder;
import lombok.Value;

import java.sql.Date;
import java.time.LocalTime;

@Builder
@Value
public class VisitDto {
    Long id;
    Date date;
    LocalTime time;
    String roomNumber;
    DoctorDto doctor;
    PatientDto patient;
}
