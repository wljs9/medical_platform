package com.wljs.medical_platform;

import com.wljs.medical_platform.entity.Doctor;
import com.wljs.medical_platform.entity.ExaminationRequest;
import com.wljs.medical_platform.entity.Patient;
import com.wljs.medical_platform.mapper.DoctorMapper;
import com.wljs.medical_platform.mapper.ExaminationRequestMapper;
import com.wljs.medical_platform.mapper.PatientMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExaminationRequestCRUDTest {

    @Autowired
    private ExaminationRequestMapper examinationRequestMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    private Integer getValidPatientId() {
        List<Patient> patients = patientMapper.findAll();
        if (patients.isEmpty()) {
            fail("没有可用的患者数据");
        }
        return patients.get(0).getPatientId();
    }

    private Integer getValidDoctorId() {
        List<Doctor> doctors = doctorMapper.findAll();
        if (doctors.isEmpty()) {
            fail("没有可用的医生数据");
        }
        return doctors.get(0).getDoctorId();
    }

    @Test
    @Transactional
    void testCompleteCRUDOperations() {
        // 使用存在的患者ID和医生ID
        Integer existingPatientId = getValidPatientId();
        Integer existingDoctorId = getValidDoctorId();

        // 测试插入
        ExaminationRequest newRequest = new ExaminationRequest();
        newRequest.setPatientId(existingPatientId);
        newRequest.setDoctorId(existingDoctorId);
        newRequest.setRequestDate(new Date());
        newRequest.setExaminationType("CT检查");
        newRequest.setStatus("pending");

        examinationRequestMapper.insertExaminationRequest(newRequest);
        assertNotNull(newRequest.getRequestId());

        // 测试查询
        ExaminationRequest foundRequest = examinationRequestMapper.findById(newRequest.getRequestId());
        assertNotNull(foundRequest);
        assertEquals("CT检查", foundRequest.getExaminationType());

        // 测试更新
        foundRequest.setExaminationType("MRI检查");
        examinationRequestMapper.updateExaminationRequest(foundRequest);

        ExaminationRequest updatedRequest = examinationRequestMapper.findById(newRequest.getRequestId());
        assertEquals("MRI检查", updatedRequest.getExaminationType());

        // 测试删除
        examinationRequestMapper.deleteExaminationRequest(newRequest.getRequestId());
        ExaminationRequest deletedRequest = examinationRequestMapper.findById(newRequest.getRequestId());
        assertNull(deletedRequest);
    }

    @Test
    @Transactional
    void testBatchOperations() {
        // 使用存在的患者ID和医生ID
        Integer existingPatientId = getValidPatientId();
        Integer existingDoctorId = getValidDoctorId();

        // 测试批量插入
        ExaminationRequest request1 = new ExaminationRequest();
        request1.setPatientId(existingPatientId);
        request1.setDoctorId(existingDoctorId);
        request1.setRequestDate(new Date());
        request1.setExaminationType("血常规");
        request1.setStatus("pending");

        ExaminationRequest request2 = new ExaminationRequest();
        request2.setPatientId(existingPatientId);
        request2.setDoctorId(existingDoctorId);
        request2.setRequestDate(new Date());
        request2.setExaminationType("尿常规");
        request2.setStatus("pending");

        List<ExaminationRequest> requests = Arrays.asList(request1, request2);
        examinationRequestMapper.batchInsertExaminationRequests(requests);

        // 验证插入
        List<ExaminationRequest> allRequests = examinationRequestMapper.findAll();
        assertTrue(allRequests.size() >= 2);

        // 测试批量删除
        List<Integer> requestIds = Arrays.asList(request1.getRequestId(), request2.getRequestId());
        examinationRequestMapper.batchDeleteExaminationRequests(requestIds);

        // 验证删除
        ExaminationRequest r1 = examinationRequestMapper.findById(request1.getRequestId());
        ExaminationRequest r2 = examinationRequestMapper.findById(request2.getRequestId());
        assertNull(r1);
        assertNull(r2);
    }

    @Test
    @Transactional
    void testStatusUpdate() {
        // 使用存在的患者ID和医生ID
        Integer existingPatientId = getValidPatientId();
        Integer existingDoctorId = getValidDoctorId();

        // 创建测试数据
        ExaminationRequest request = new ExaminationRequest();
        request.setPatientId(existingPatientId);
        request.setDoctorId(existingDoctorId);
        request.setRequestDate(new Date());
        request.setExaminationType("心电图");
        request.setStatus("pending");
        examinationRequestMapper.insertExaminationRequest(request);

        // 测试状态更新
        examinationRequestMapper.updateStatus(request.getRequestId(), "completed");

        ExaminationRequest updatedRequest = examinationRequestMapper.findById(request.getRequestId());
        assertEquals("completed", updatedRequest.getStatus());

        // 清理
        examinationRequestMapper.deleteExaminationRequest(request.getRequestId());
    }

    @Test
    @Transactional
    void testDeleteByPatientAndDoctor() {
        // 使用存在的患者ID和医生ID
        Integer existingPatientId = getValidPatientId();
        Integer existingDoctorId = getValidDoctorId();

        // 创建测试数据
        ExaminationRequest request = new ExaminationRequest();
        request.setPatientId(existingPatientId);
        request.setDoctorId(existingDoctorId);
        request.setRequestDate(new Date());
        request.setExaminationType("B超");
        request.setStatus("pending");
        examinationRequestMapper.insertExaminationRequest(request);

        // 测试根据患者ID删除
        examinationRequestMapper.deleteByPatientId(existingPatientId);
        ExaminationRequest deletedByPatient = examinationRequestMapper.findById(request.getRequestId());
        assertNull(deletedByPatient);

        // 重新插入
        examinationRequestMapper.insertExaminationRequest(request);

        // 测试根据医生ID删除
        examinationRequestMapper.deleteByDoctorId(existingDoctorId);
        ExaminationRequest deletedByDoctor = examinationRequestMapper.findById(request.getRequestId());
        assertNull(deletedByDoctor);
    }
}