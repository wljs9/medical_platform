package com.wljs.medical_platform;

import com.wljs.medical_platform.entity.Doctor;
import com.wljs.medical_platform.mapper.DoctorMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DoctorCRUDTest {

    @Autowired
    private DoctorMapper doctorMapper;

    @Test
    @Transactional
    void testCompleteCRUDOperations() {
        // 测试插入
        Doctor newDoctor = new Doctor();
        newDoctor.setName("测试医生");
        newDoctor.setGender("M");
        newDoctor.setDepartmentId(1);
        newDoctor.setTitle("主治医师");
        newDoctor.setPhone("13900139000");

        doctorMapper.insertDoctor(newDoctor);
        assertNotNull(newDoctor.getDoctorId());

        // 测试查询
        Doctor foundDoctor = doctorMapper.findById(newDoctor.getDoctorId());
        assertNotNull(foundDoctor);
        assertEquals("测试医生", foundDoctor.getName());

        // 测试更新
        foundDoctor.setName("修改后的医生");
        doctorMapper.updateDoctor(foundDoctor);

        Doctor updatedDoctor = doctorMapper.findById(newDoctor.getDoctorId());
        assertEquals("修改后的医生", updatedDoctor.getName());

        // 测试删除
        doctorMapper.deleteDoctor(newDoctor.getDoctorId());
        Doctor deletedDoctor = doctorMapper.findById(newDoctor.getDoctorId());
        assertNull(deletedDoctor);
    }

    @Test
    @Transactional
    void testBatchOperations() {
        // 测试批量插入
        Doctor doctor1 = new Doctor();
        doctor1.setName("批量医生1");
        doctor1.setGender("F");
        doctor1.setDepartmentId(1);
        doctor1.setTitle("医师");
        doctor1.setPhone("13900139001");

        Doctor doctor2 = new Doctor();
        doctor2.setName("批量医生2");
        doctor2.setGender("M");
        doctor2.setDepartmentId(2);
        doctor2.setTitle("医师");
        doctor2.setPhone("13900139002");

        List<Doctor> doctors = Arrays.asList(doctor1, doctor2);
        doctorMapper.batchInsertDoctors(doctors);

        // 验证插入
        List<Doctor> allDoctors = doctorMapper.findAll();
        assertTrue(allDoctors.size() >= 2);

        // 测试批量删除
        List<Integer> doctorIds = Arrays.asList(doctor1.getDoctorId(), doctor2.getDoctorId());
        doctorMapper.batchDeleteDoctors(doctorIds);

        // 验证删除
        Doctor d1 = doctorMapper.findById(doctor1.getDoctorId());
        Doctor d2 = doctorMapper.findById(doctor2.getDoctorId());
        assertNull(d1);
        assertNull(d2);
    }
}