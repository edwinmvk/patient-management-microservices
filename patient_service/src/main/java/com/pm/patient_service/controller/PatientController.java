package com.pm.patient_service.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pm.patient_service.dto.PatientDTO;
import com.pm.patient_service.service.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    // Constructor injection of PatientService
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getPatients() {
        List<PatientDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }
}
