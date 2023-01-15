package pl.markowski.edoctor.service;

import pl.markowski.edoctor.model.dto.DoctorDto;

public interface DoctorService {

    DoctorDto getDoctorDtoById(Long id);

    public DoctorDto saveDoctor (DoctorDto doctor);

    DoctorDto updateDoctor(DoctorDto doctor);

    void deleteDoctor(Long doctorId);
}
