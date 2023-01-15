package pl.markowski.edoctor.service;

import pl.markowski.edoctor.model.dto.VisitDto;

import java.util.List;

public interface VisitService {

    VisitDto saveVisit(VisitDto visit);

    void deleteVisit(Long visitId);

    VisitDto updateVisit(VisitDto visit);

    List<VisitDto> getVisitsForPatient(Long patientId);

    List<VisitDto> getVisits();
}
