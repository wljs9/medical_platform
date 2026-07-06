package com.wljs.medical_platform.service;

import com.wljs.medical_platform.entity.MedicalRecord;
import com.wljs.medical_platform.mapper.MedicalRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordMapper.findAll();
    }

    public MedicalRecord getMedicalRecordById(Integer recordId) {
        return medicalRecordMapper.findById(recordId);
    }

    public List<MedicalRecord> getMedicalRecordsByPatient(Integer patientId) {
        return medicalRecordMapper.findByPatientId(patientId);
    }

    public List<MedicalRecord> getMedicalRecordsByDoctor(Integer doctorId) {
        return medicalRecordMapper.findByDoctorId(doctorId);
    }

    public int getMedicalRecordCount() {
        return medicalRecordMapper.countMedicalRecords();
    }

    @Transactional
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordMapper.insertMedicalRecord(medicalRecord);
    }

    @Transactional
    public void batchCreateMedicalRecords(List<MedicalRecord> medicalRecords) {
        medicalRecordMapper.batchInsertMedicalRecords(medicalRecords);
    }

    @Transactional
    public void updateMedicalRecordInfo(MedicalRecord medicalRecord) {
        medicalRecordMapper.updateMedicalRecord(medicalRecord);
    }

    @Transactional
    public void deleteMedicalRecordWithTransaction(Integer recordId) {
        medicalRecordMapper.deleteMedicalRecord(recordId);
    }

    @Transactional
    public void deleteMedicalRecordsByPatient(Integer patientId) {
        medicalRecordMapper.deleteByPatientId(patientId);
    }

    @Transactional
    public void deleteMedicalRecordsByDoctor(Integer doctorId) {
        medicalRecordMapper.deleteByDoctorId(doctorId);
    }
}
