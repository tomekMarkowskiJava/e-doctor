package pl.markowski.edoctor.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.markowski.edoctor.exception.InvalidTimeOfVisitException;
import pl.markowski.edoctor.exception.NotFoundException;
import pl.markowski.edoctor.mapper.VisitMapper;
import pl.markowski.edoctor.model.dto.VisitDto;
import pl.markowski.edoctor.model.entity.Visit;
import pl.markowski.edoctor.repository.VisitRepository;
import pl.markowski.edoctor.service.VisitService;
import pl.markowski.edoctor.utils.EDoctorConstants;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository repository;

    @Autowired
    public VisitServiceImpl(VisitRepository repository) {
        this.repository = repository;
    }

    @Override
    public VisitDto saveVisit(VisitDto visit) {
        if (validateTimeOfVisit(visit)) {
            Visit visitToSave = VisitMapper.mapToEntity(visit);
            return VisitMapper.mapToDto(repository.save(visitToSave));
        } else {
            throw new InvalidTimeOfVisitException("Visit with a given doctor is already scheduled for this hour. Select a different time.");
        }
    }

    private boolean validateTimeOfVisit(VisitDto visit) {
        List<Visit> visitsByWantedDay = repository.findAllByDateAndDoctor_Id(visit.getDate(), visit.getDoctor().getId());

        return visitsByWantedDay.stream()
                .filter(v -> visit.getTime().isAfter(v.getTime().minusMinutes(EDoctorConstants.VISIT_DURATION)))
                .filter(v -> visit.getTime().isBefore(v.getTime().plusMinutes(EDoctorConstants.VISIT_DURATION)))
                .findFirst().isEmpty();
    }

    @Override
    public void deleteVisit(Long visitId) {
        repository.deleteById(visitId);
    }

    @Override
    public VisitDto updateVisit(VisitDto visit) {
        if (validateTimeOfVisit(visit)) {
            Optional<Visit> optionalVisit = repository.findById(visit.getId());
            if (optionalVisit.isPresent()) {
                Visit visitToUpdate = optionalVisit.get();
                visitToUpdate.setDate(visit.getDate());
                visitToUpdate.setTime(visit.getTime());
                return VisitMapper.mapToDto(repository.save(visitToUpdate));
            } else {
                throw new NotFoundException(String.format("Visit with id %s not found", visit.getId()));
            }
        } else {
            throw new InvalidTimeOfVisitException("Visit with a given doctor is already scheduled for this hour. Select a different time.");
        }
    }

    @Override
    public List<VisitDto> getVisitsForPatient(Long patientId) {
        List<Visit> visits = repository.findAllByPatient_Id(patientId);
        return visits.stream()
                .map(VisitMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitDto> getVisits() {
        List<Visit> visits = repository.findAll();
        return visits.stream()
                .map(VisitMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
