package pl.markowski.edoctor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.markowski.edoctor.exception.CanNotDeleteException;
import pl.markowski.edoctor.exception.NotFoundException;
import pl.markowski.edoctor.model.dto.PatientDto;
import pl.markowski.edoctor.service.PatientService;

import javax.validation.constraints.Positive;

import static pl.markowski.edoctor.utils.EDoctorConstants.API;

@RestController
@RequestMapping(API + "/patient")
public class PatientController {

    PatientService service;

    @Autowired
    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PatientDto> addPatient(@RequestBody PatientDto patient) {
        try {
            return ResponseEntity.ok(service.savePatient(patient));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<PatientDto> getPatient(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(service.getPatientDtoById(id));
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@Positive @PathVariable("id") Long id) {
        try {
            service.deletePatient(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CanNotDeleteException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<PatientDto> updatePatient(@RequestBody PatientDto patient) {
        try {
            return ResponseEntity.ok(service.updatePatient(patient));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}