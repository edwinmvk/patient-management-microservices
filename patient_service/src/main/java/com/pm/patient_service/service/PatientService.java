package com.pm.patient_service.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    // Constructor injection of PatientRepository
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientDTOs = patients.stream().map(patient -> PatientMapper.toDTO(patient))
                .toList();
        return patientDTOs;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        try {
            // Check if a patient with the same email already exists and throw 409 Conflict
            // If we don't do this check here, a DB-level constraint violation will occur
            // and return a 500 Internal Server Error
            if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "A patient with this email already exists: " + patientRequestDTO.getEmail());
            }
            // Save the new patient
            Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
            PatientResponseDTO patientResponseDTO = PatientMapper.toDTO(newPatient);
            return patientResponseDTO;
        } catch (DataIntegrityViolationException e) {
            // Handle potential DataIntegrityViolationException and return 409 Conflict
            // This usually executes in case of Race Condition
            // ie. Two requests with the same email arrive simultaneously
            // And pass the existsByEmail check
            // Both try save()
            // One succeeds, the other hits a database constraint violation
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Duplicate entry detected for email: " + patientRequestDTO.getEmail(),
                    e);
        }

    }
}
