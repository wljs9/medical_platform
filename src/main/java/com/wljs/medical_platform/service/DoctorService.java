package com.wljs.medical_platform.service;

import com.wljs.medical_platform.entity.Doctor;
import com.wljs.medical_platform.mapper.DoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorMapper doctorMapper;

    public List<Doctor> getAllDoctors() {
        return doctorMapper.findAll();
    }

    public Doctor getDoctorById(Integer doctorId) {
        return doctorMapper.findById(doctorId);
    }

    public List<Doctor> searchDoctorsByName(String name) {
        return doctorMapper.findByName(name);
    }

    public List<Doctor> getDoctorsByDepartment(Integer departmentId) {
        return doctorMapper.findByDepartmentId(departmentId);
    }

    public int getDoctorCount() {
        return doctorMapper.countDoctors();
    }

    // 添加事务支持的方法
    @Transactional
    public void createDoctorWithTransaction(Doctor doctor) {
        doctorMapper.insertDoctor(doctor);
    }

    @Transactional
    public void batchCreateDoctors(List<Doctor> doctors) {
        doctorMapper.batchInsertDoctors(doctors);
    }

    @Transactional
    public void updateDoctorInfo(Doctor doctor) {
        doctorMapper.updateDoctor(doctor);
    }

    @Transactional
    public void deleteDoctorWithRelatedData(Integer doctorId) {
        // 先删除关联的记录（如果外键约束允许，或者设置级联删除）
        // medicalRecordMapper.deleteByDoctorId(doctorId);
        // examinationRequestMapper.deleteByDoctorId(doctorId);

        // 再删除医生
        doctorMapper.deleteDoctor(doctorId);
    }
}
