package com.wljs.medical_platform.mapper;

import com.wljs.medical_platform.entity.ExaminationRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExaminationRequestMapper {
    List<ExaminationRequest> findAll();
    ExaminationRequest findById(Integer requestId);
    List<ExaminationRequest> findByPatientId(Integer patientId);
    List<ExaminationRequest> findByDoctorId(Integer doctorId);
    List<ExaminationRequest> findByStatus(String status);
    int countExaminationRequests();
    int insertExaminationRequest(ExaminationRequest examinationRequest);
    // 批量插入数据
    int batchInsertExaminationRequests(List<ExaminationRequest> examinationRequests);

    // 修改数据
    int updateExaminationRequest(ExaminationRequest examinationRequest);

    // 删除数据
    int deleteExaminationRequest(Integer requestId);

    // 批量删除数据
    int batchDeleteExaminationRequests(List<Integer> requestIds);

    // 根据患者ID删除检查申请
    int deleteByPatientId(Integer patientId);

    // 根据医生ID删除检查申请
    int deleteByDoctorId(Integer doctorId);

    // 更新检查状态
    int updateStatus(Integer requestId, String status);
}
