package pl.markowski.edoctor.service;

import pl.markowski.edoctor.model.dto.PatientDto;
import pl.markowski.edoctor.model.entity.Patient;

public interface PatientService {

    PatientDto getPatientDtoById(Long id);

    PatientDto savePatient(PatientDto patient);

    PatientDto updatePatient(PatientDto patient);

    void deletePatient(Long patientId);
}
