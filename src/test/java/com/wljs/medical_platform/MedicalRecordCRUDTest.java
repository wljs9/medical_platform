package com.wljs.medical_platform;

import com.wljs.medical_platform.entity.Doctor;
import com.wljs.medical_platform.entity.MedicalRecord;
import com.wljs.medical_platform.entity.Patient;
import com.wljs.medical_platform.mapper.DoctorMapper;
import com.wljs.medical_platform.mapper.MedicalRecordMapper;
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
class MedicalRecordCRUDTest {

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @Test
    @Transactional
    void testCompleteCRUDOperations() {
        // 测试插入
        MedicalRecord newRecord = new MedicalRecord();
        newRecord.setPatientId(1);
        newRecord.setDoctorId(1);
        newRecord.setVisitDate(new Date());
        newRecord.setSymptoms("测试症状");
        newRecord.setDiagnosis("测试诊断");

        medicalRecordMapper.insertMedicalRecord(newRecord);
        assertNotNull(newRecord.getRecordId());

        // 测试查询
        MedicalRecord foundRecord = medicalRecordMapper.findById(newRecord.getRecordId());
        assertNotNull(foundRecord);
        assertEquals("测试症状", foundRecord.getSymptoms());

        // 测试更新
        foundRecord.setSymptoms("修改后的症状");
        medicalRecordMapper.updateMedicalRecord(foundRecord);

        MedicalRecord updatedRecord = medicalRecordMapper.findById(newRecord.getRecordId());
        assertEquals("修改后的症状", updatedRecord.getSymptoms());

        // 测试删除
        medicalRecordMapper.deleteMedicalRecord(newRecord.getRecordId());
        MedicalRecord deletedRecord = medicalRecordMapper.findById(newRecord.getRecordId());
        assertNull(deletedRecord);
    }

    @Test
    @Transactional
    void testBatchOperations() {
        // 测试批量插入
        MedicalRecord record1 = new MedicalRecord();
        record1.setPatientId(1);
        record1.setDoctorId(1);
        record1.setVisitDate(new Date());
        record1.setSymptoms("症状1");
        record1.setDiagnosis("诊断1");

        MedicalRecord record2 = new MedicalRecord();
        record2.setPatientId(2);
        record2.setDoctorId(2);
        record2.setVisitDate(new Date());
        record2.setSymptoms("症状2");
        record2.setDiagnosis("诊断2");

        List<MedicalRecord> records = Arrays.asList(record1, record2);
        medicalRecordMapper.batchInsertMedicalRecords(records);

        // 验证插入
        List<MedicalRecord> allRecords = medicalRecordMapper.findAll();
        assertTrue(allRecords.size() >= 2);

        // 测试批量删除
        List<Integer> recordIds = Arrays.asList(record1.getRecordId(), record2.getRecordId());
        medicalRecordMapper.batchDeleteMedicalRecords(recordIds);

        // 验证删除
        MedicalRecord r1 = medicalRecordMapper.findById(record1.getRecordId());
        MedicalRecord r2 = medicalRecordMapper.findById(record2.getRecordId());
        assertNull(r1);
        assertNull(r2);
    }

}