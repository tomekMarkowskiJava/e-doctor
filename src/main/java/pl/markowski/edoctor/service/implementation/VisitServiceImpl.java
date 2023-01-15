package pl.markowski.edoctor.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.markowski.edoctor.model.entity.Visit;
import pl.markowski.edoctor.repository.VisitRepository;
import pl.markowski.edoctor.service.VisitService;

import java.util.Optional;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository repository;

    @Autowired
    public VisitServiceImpl(VisitRepository repository) {
        this.repository = repository;
    }

    public Visit saveVisit(Visit visit) {
        return repository.save(visit);
    }

    @Override
    public void deleteVisit(Long visitId) {
        repository.deleteById(visitId);
    }

    @Override
    public Visit updateVisit(Visit visit) {
        Optional<Visit> optionalVisit = repository.findById(visit.getId());
        if (optionalVisit.isPresent()){
            Visit visitToUpdate = optionalVisit.get();
            visitToUpdate.setDate(visit.getDate());
            visitToUpdate.setTime(visit.getTime());
            return saveVisit(visitToUpdate);
        } else {
            throw new RuntimeException(String.format("Visit with id %s not found", visit.getId()));
        }
    }
}
