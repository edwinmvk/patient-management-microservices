package com.pm.patient_service.mapper;

import com.pm.patient_service.dto.PatientDTO;
import com.pm.patient_service.model.Patient;

// Mapper is a helper class which converts the Domain Entity Model into Domain Transfer Object (DTO) and vice versa

public class PatientMapper {
    public static PatientDTO toDTO(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientDTO;
    }
}
