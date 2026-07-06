package com.wljs.medical_platform.service;

import com.wljs.medical_platform.entity.Patient;
import com.wljs.medical_platform.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PatientService {

    @Autowired
    private PatientMapper patientMapper;

    public List<Patient> getAllPatients() {
        return patientMapper.findAll();
    }

    public Patient getPatientById(Integer patientId) {
        return patientMapper.findById(patientId);
    }

    public List<Patient> searchPatientsByName(String name) {
        return patientMapper.findByName(name);
    }

    public int getPatientCount() {
        return patientMapper.countPatients();
    }

    // 新增的多表查询方法
    public Patient getPatientWithRecords(Integer patientId) {
        return patientMapper.findPatientWithRecords(patientId);
    }

    public List<Map<String, Object>> getPatientDetails(Integer patientId) {
        return patientMapper.findPatientDetails(patientId);
    }

    public List<Map<String, Object>> getDepartmentStats() {
        return patientMapper.getDepartmentPatientStats();
    }

    // 添加事务支持的方法
    @Transactional
    public void createPatientWithTransaction(Patient patient) {
        patientMapper.insertPatient(patient);
        // 可以在这里添加其他需要事务保证的操作
    }

    @Transactional
    public void batchCreatePatients(List<Patient> patients) {
        patientMapper.batchInsertPatients(patients);
    }

    @Transactional
    public void updatePatientInfo(Patient patient) {
        patientMapper.updatePatient(patient);
    }

    @Transactional
    public void deletePatientWithRecords(Integer patientId) {
        // 先删除关联的记录
        // medicalRecordMapper.deleteByPatientId(patientId);
        // examinationRequestMapper.deleteByPatientId(patientId);

        // 再删除患者
        patientMapper.deletePatient(patientId);
    }

    public Patient getPatientWithAllRecords(Integer patientId) {
        return patientMapper.findPatientWithAllRecords(patientId);
    }
}