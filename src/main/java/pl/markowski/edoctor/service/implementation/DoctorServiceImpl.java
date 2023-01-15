package pl.markowski.edoctor.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.markowski.edoctor.exception.CanNotDeleteException;
import pl.markowski.edoctor.exception.NotFoundException;
import pl.markowski.edoctor.mapper.DoctorMapper;
import pl.markowski.edoctor.model.dto.DoctorDto;
import pl.markowski.edoctor.model.entity.Doctor;
import pl.markowski.edoctor.repository.DoctorRepository;
import pl.markowski.edoctor.service.DoctorService;

import java.util.Optional;

@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository repository) {
        this.repository = repository;
    }

    @Override
    public DoctorDto getDoctorDtoById(Long id) {
        return DoctorMapper.mapToDto(getDoctorById(id));
    }

    private Doctor getDoctorById(Long id) {
        Optional<Doctor> doctor = repository.findById(id);
        if (doctor.isPresent())
            return doctor.get();
        else
            throw new NotFoundException(String.format("Patient with id %s not found", id));
    }

    @Override
    public DoctorDto saveDoctor(DoctorDto doctor) {
        Doctor doctorToSave = DoctorMapper.mapToEntity(doctor);
        return DoctorMapper.mapToDto(repository.save(doctorToSave));
    }

    @Override
    public DoctorDto updateDoctor(DoctorDto doctor) {
        Doctor doctorToUpdate = getDoctorById(doctor.getId());
        doctorToUpdate.setName(doctor.getName());
        doctorToUpdate.setSurname(doctor.getSurname());
        doctorToUpdate.setSpecialization(doctor.getSpecialization());
        return DoctorMapper.mapToDto(repository.save(doctorToUpdate));
    }

    @Override
    public void deleteDoctor(Long doctorId) {
        Doctor doctorToDelete = getDoctorById(doctorId);
        try {
            repository.delete(doctorToDelete);
        } catch (Exception e) {
            throw new CanNotDeleteException(e.getMessage());
        }
    }
}
