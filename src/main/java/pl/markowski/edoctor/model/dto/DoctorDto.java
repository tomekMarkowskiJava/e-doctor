package pl.markowski.edoctor.model.dto;

import lombok.Builder;
import lombok.Value;
import pl.markowski.edoctor.model.enums.SpecializationEnum;

@Builder
@Value
public class DoctorDto {
    Long id;
    String name;
    String surname;
    SpecializationEnum specialization;
}

