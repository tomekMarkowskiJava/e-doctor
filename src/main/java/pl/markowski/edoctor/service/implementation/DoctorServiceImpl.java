package pl.markowski.edoctor.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.markowski.edoctor.model.entity.Doctor;
import pl.markowski.edoctor.repository.DoctorRepository;
import pl.markowski.edoctor.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository repository) {
        this.repository = repository;
    }

    public Doctor saveDoctor(Doctor doctor) {
        return repository.save(doctor);
    }
}
