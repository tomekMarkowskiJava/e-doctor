package pl.markowski.edoctor.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.markowski.edoctor.exception.CanNotDeleteException;
import pl.markowski.edoctor.exception.NotFoundException;
import pl.markowski.edoctor.mapper.PatientMapper;
import pl.markowski.edoctor.model.dto.PatientDto;
import pl.markowski.edoctor.model.entity.Patient;
import pl.markowski.edoctor.repository.PatientRepository;
import pl.markowski.edoctor.service.PatientService;

import java.util.Optional;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    @Autowired
    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public PatientDto getPatientDtoById(Long id) {
        return PatientMapper.mapToDto(getPatientById(id));
    }

    private Patient getPatientById(Long id) {
        Optional<Patient> patient = repository.findById(id);
        if (patient.isPresent())
            return patient.get();
        else
            throw new NotFoundException(String.format("Patient with id %s not found", id));
    }

    @Override
    public PatientDto savePatient(PatientDto patient) {
        Patient patientToSave = PatientMapper.mapToEntity(patient);
        return PatientMapper.mapToDto(repository.save(patientToSave));
    }

    @Override
    public PatientDto updatePatient(PatientDto patient) {
        Patient patientToUpdate = getPatientById(patient.getId());
        patientToUpdate.setAddress(patient.getAddress());
        patientToUpdate.setName(patient.getName());
        patientToUpdate.setSurname(patient.getSurname());
        return PatientMapper.mapToDto(repository.save(patientToUpdate));
    }

    @Override
    public void deletePatient(Long patientId) {
        Patient patientToDelete = getPatientById(patientId);
        try {
            repository.delete(patientToDelete);
        } catch (Exception e) {
            throw new CanNotDeleteException(e.getMessage());
        }
    }
}
