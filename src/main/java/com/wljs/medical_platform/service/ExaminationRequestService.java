package com.wljs.medical_platform.service;

import com.wljs.medical_platform.entity.ExaminationRequest;
import com.wljs.medical_platform.mapper.ExaminationRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExaminationRequestService {
    @Autowired
    private ExaminationRequestMapper examinationRequestMapper;

    public List<ExaminationRequest> getAllExaminationRequests() {
        return examinationRequestMapper.findAll();
    }

    public ExaminationRequest getExaminationRequestById(Integer requestId) {
        return examinationRequestMapper.findById(requestId);
    }

    public List<ExaminationRequest> getExaminationRequestsByPatient(Integer patientId) {
        return examinationRequestMapper.findByPatientId(patientId);
    }

    public List<ExaminationRequest> getExaminationRequestsByDoctor(Integer doctorId) {
        return examinationRequestMapper.findByDoctorId(doctorId);
    }

    public List<ExaminationRequest> getExaminationRequestsByStatus(String status) {
        return examinationRequestMapper.findByStatus(status);
    }

    public int getExaminationRequestCount() {
        return examinationRequestMapper.countExaminationRequests();
    }

    @Transactional
    public void addExaminationRequest(ExaminationRequest examinationRequest) {
        examinationRequestMapper.insertExaminationRequest(examinationRequest);
    }

    @Transactional
    public void batchCreateExaminationRequests(List<ExaminationRequest> examinationRequests) {
        examinationRequestMapper.batchInsertExaminationRequests(examinationRequests);
    }

    @Transactional
    public void updateExaminationRequestInfo(ExaminationRequest examinationRequest) {
        examinationRequestMapper.updateExaminationRequest(examinationRequest);
    }

    @Transactional
    public void deleteExaminationRequestWithTransaction(Integer requestId) {
        examinationRequestMapper.deleteExaminationRequest(requestId);
    }

    @Transactional
    public void deleteExaminationRequestsByPatient(Integer patientId) {
        examinationRequestMapper.deleteByPatientId(patientId);
    }

    @Transactional
    public void deleteExaminationRequestsByDoctor(Integer doctorId) {
        examinationRequestMapper.deleteByDoctorId(doctorId);
    }

    @Transactional
    public void updateExaminationRequestStatus(Integer requestId, String status) {
        examinationRequestMapper.updateStatus(requestId, status);
    }
}
