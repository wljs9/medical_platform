package com.wljs.medical_platform.mapper;

import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.wljs.medical_platform.dto.PatientDetailDTO;
import com.wljs.medical_platform.entity.Patient;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PatientMapper {

    // 基础查询
    List<Patient> findAll();
    Patient findById(Integer patientId);
    List<Patient> findByName(String name);
    int countPatients();
    Integer getIdByName(String name);

    // 新增的多表查询方法
    Patient findPatientWithRecords(Integer patientId);
    //Map方法的作用
    @MapKey("recordId")
    List<Map<String,Object>>findPatientDetails(Integer patientId);
    @MapKey("departmentName")
    List<Map<String,Object>> getDepartmentPatientStats();

    // 插入数据
    int insertPatient(Patient patient);

    // 批量插入数据
    int batchInsertPatients(List<Patient> patients);

    // 修改数据
    int updatePatient(Patient patient);

    // 删除数据
    int deletePatient(Integer patientId);

    // 批量删除数据
    int batchDeletePatients(List<Integer> patientIds);

    // 包含检查申请的完整JOIN查询方法
    Patient findPatientWithAllRecords(Integer patientId);

// 删除数据表（谨慎使用）
// void dropPatientTable();

// 创建数据表（如果表不存在）
// void createPatientTableIfNotExists();
}