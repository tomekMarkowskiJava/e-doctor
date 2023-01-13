package pl.markowski.edoctor.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.markowski.edoctor.model.entity.Patient;
import pl.markowski.edoctor.repository.PatientRepository;
import pl.markowski.edoctor.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    @Autowired
    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

    public Patient savePatient(Patient patient) {
        return repository.save(patient);
    }
}
