package com.wljs.medical_platform;

import com.wljs.medical_platform.entity.Department;
import com.wljs.medical_platform.mapper.DepartmentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DepartmentTest {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Test
    void testFindAll() {
        List<Department> departments = departmentMapper.findAll();
        assertNotNull(departments);
        assertFalse(departments.isEmpty());
        System.out.println("查询到 " + departments.size() + " 个科室");

        departments.forEach(System.out::println);
    }

    @Test
    void testFindById() {
        Department department = departmentMapper.findById(9);
        assertNotNull(department);
        assertEquals(9, department.getDepartmentId());
        //验证id是否为预期的值
        System.out.println(department);
    }

    @Test
    void testFindByName() {
        Department department = departmentMapper.findByName("儿科");
        assertNotNull(department);
        assertEquals("儿科", department.getDepartmentName());
        System.out.println(department);
    }

    @Test
    void testCountDepartments() {
        int count = departmentMapper.countDepartments();
        assertTrue(count > 0);
        System.out.println("科室总数: " + count);
    }
}
