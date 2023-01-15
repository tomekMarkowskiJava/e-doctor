package pl.markowski.edoctor.model.dto;

import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class PatientDto {
    Long id;
    String name;
    String surname;
    String address;
}
