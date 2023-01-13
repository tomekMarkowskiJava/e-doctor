package pl.markowski.edoctor.service;

import pl.markowski.edoctor.model.entity.Visit;

public interface VisitService {

    public Visit saveVisit(Visit visit);
    public void deleteVisit(Long visitId);
    public Visit updateVisit(Visit visit);
}
