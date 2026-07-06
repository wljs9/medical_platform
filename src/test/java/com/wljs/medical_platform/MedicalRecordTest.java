package com.wljs.medical_platform;

import com.wljs.medical_platform.entity.MedicalRecord;
import com.wljs.medical_platform.mapper.MedicalRecordMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicalRecordTest {
    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @Test
    void testFindAll() {
        List<MedicalRecord> records = medicalRecordMapper.findAll();
        assertNotNull(records);
        assertFalse(records.isEmpty());
        System.out.println("查询到 " + records.size() + " 条病历记录");

        records.stream().limit(5).forEach(System.out::println);
    }

    @Test
    void testFindById() {
        MedicalRecord record = medicalRecordMapper.findById(1);
        assertNotNull(record);
        assertEquals(1, record.getRecordId());
        System.out.println(record);
    }

    @Test
    void testFindByPatientId() {
        List<MedicalRecord> records = medicalRecordMapper.findByPatientId(1);
        assertNotNull(records);
        System.out.println("患者1有 " + records.size() + " 条病历记录");

        records.forEach(System.out::println);
    }

    @Test
    void testFindByDoctorId() {
        List<MedicalRecord> records = medicalRecordMapper.findByDoctorId(1);
        assertNotNull(records);
        System.out.println("医生1有 " + records.size() + " 条病历记录");

        records.forEach(System.out::println);
    }

    @Test
    void testCountMedicalRecords() {
        int count = medicalRecordMapper.countMedicalRecords();
        assertTrue(count > 0);
        System.out.println("病历记录总数: " + count);
    }
}
