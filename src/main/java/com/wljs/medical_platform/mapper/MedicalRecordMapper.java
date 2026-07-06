package com.wljs.medical_platform.mapper;

import com.wljs.medical_platform.entity.MedicalRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MedicalRecordMapper {
    List<MedicalRecord> findAll();
    MedicalRecord findById(Integer recordId);
    List<MedicalRecord> findByPatientId(Integer patientId);
    List<MedicalRecord> findByDoctorId(Integer doctorId);
    int countMedicalRecords();
    int insertMedicalRecord(MedicalRecord medicalRecord);

    // 批量插入数据
    int batchInsertMedicalRecords(List<MedicalRecord> medicalRecords);

    // 修改数据
    int updateMedicalRecord(MedicalRecord medicalRecord);

    // 删除数据
    int deleteMedicalRecord(Integer recordId);

    // 批量删除数据
    int batchDeleteMedicalRecords(List<Integer> recordIds);

    // 根据患者ID删除病历
    int deleteByPatientId(Integer patientId);

    // 根据医生ID删除病历
    int deleteByDoctorId(Integer doctorId);
}
