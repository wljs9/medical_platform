package com.wljs.medical_platform;

import com.wljs.medical_platform.entity.ExaminationRequest;
import com.wljs.medical_platform.mapper.ExaminationRequestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExaminationRequestTest {
    @Autowired
    private ExaminationRequestMapper examinationRequestMapper;

    @Test
    void testFindAll() {
        List<ExaminationRequest> requests = examinationRequestMapper.findAll();
        assertNotNull(requests);
        assertFalse(requests.isEmpty());
        System.out.println("查询到 " + requests.size() + " 条检查申请");

        requests.stream().limit(5).forEach(System.out::println);
    }

    @Test
    void testFindById() {
        ExaminationRequest request = examinationRequestMapper.findById(1);
        assertNotNull(request);
        assertEquals(1, request.getRequestId());
        System.out.println(request);
    }

    @Test
    void testFindByPatientId() {
        List<ExaminationRequest> requests = examinationRequestMapper.findByPatientId(1);
        assertNotNull(requests);
        System.out.println("患者1有 " + requests.size() + " 条检查申请");

        requests.forEach(System.out::println);
    }

    @Test
    void testFindByDoctorId() {
        List<ExaminationRequest> requests = examinationRequestMapper.findByDoctorId(1);
        assertNotNull(requests);
        System.out.println("医生1有 " + requests.size() + " 条检查申请");

        requests.forEach(System.out::println);
    }

    @Test
    void testFindByStatus() {
        List<ExaminationRequest> requests = examinationRequestMapper.findByStatus("completed");
        assertNotNull(requests);
        System.out.println("有 " + requests.size() + " 条已完成的检查申请");

        requests.stream().limit(3).forEach(System.out::println);
    }

    @Test
    void testCountExaminationRequests() {
        int count = examinationRequestMapper.countExaminationRequests();
        assertTrue(count > 0);
        System.out.println("检查申请总数: " + count);
    }
}
