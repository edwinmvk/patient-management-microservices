package com.pm.patient_service.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.pm.patient_service.dto.PatientDTO;
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

    public List<PatientDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        List<PatientDTO> patientDTOs = patients.stream().map(patient -> PatientMapper.toDTO(patient))
                .toList();

        return patientDTOs;
    }
}
