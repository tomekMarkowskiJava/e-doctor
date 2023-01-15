package pl.markowski.edoctor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.markowski.edoctor.model.dto.VisitDto;
import pl.markowski.edoctor.service.VisitService;

import java.util.List;

import static pl.markowski.edoctor.utils.EDoctorConstants.API;

@RestController
@RequestMapping(API + "/visit")
public class VisitController {

    VisitService service;

    @Autowired
    public VisitController(VisitService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VisitDto> addVisit (@RequestBody VisitDto visit) {
        try {
            return ResponseEntity.ok(service.saveVisit(visit));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<VisitDto>> getAllVisitsForPatient(@PathVariable("patientId") Long patientId) {
        return ResponseEntity.ok(service.getVisitsForPatient(patientId));
    }

    @GetMapping
    public ResponseEntity<List<VisitDto>> getAllVisitsLong() {
        return ResponseEntity.ok(service.getVisits());
    }
}
