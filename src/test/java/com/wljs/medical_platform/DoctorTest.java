package com.wljs.medical_platform;

import com.wljs.medical_platform.entity.Doctor;
import com.wljs.medical_platform.mapper.DoctorMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DoctorTest {

    @Autowired
    private DoctorMapper doctorMapper;

    @Test
    void testFindAll() {
        List<Doctor> doctors = doctorMapper.findAll();
        assertNotNull(doctors);
        assertFalse(doctors.isEmpty());
        System.out.println("查询到 " + doctors.size() + " 个医生");

        doctors.stream().limit(10).forEach(System.out::println);

    }

    @Test
    void testFindById() {
        Doctor doctor = doctorMapper.findById(1);
        assertNotNull(doctor);
        assertEquals(1, doctor.getDoctorId());
        System.out.println(doctor);
    }

    @Test
    void testFindByName() {
        List<Doctor> doctors = doctorMapper.findByName("张");
        assertNotNull(doctors);
        System.out.println("搜索到 " + doctors.size() + " 个姓张的医生");

        doctors.forEach(System.out::println);
    }

    @Test
    void testFindByDepartmentId() {
        List<Doctor> doctors = doctorMapper.findByDepartmentId(1);
        assertNotNull(doctors);
        System.out.println("科室1有 " + doctors.size() + " 个医生");

        doctors.forEach(System.out::println);
    }

    @Test
    void testCountDoctors() {
        int count = doctorMapper.countDoctors();
        assertTrue(count > 0);
        System.out.println("医生总数: " + count);
    }
}
