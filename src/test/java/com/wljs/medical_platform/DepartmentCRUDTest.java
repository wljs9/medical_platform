package com.wljs.medical_platform;

import com.wljs.medical_platform.entity.Department;
import com.wljs.medical_platform.mapper.DepartmentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepartmentCRUDTest {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Test
    @Transactional
    void testCompleteCRUDOperations() {
        // 测试插入
        Department newDepartment = new Department();
        newDepartment.setDepartmentName("测试科室");

        departmentMapper.insertDepartment(newDepartment);
        assertNotNull(newDepartment.getDepartmentId());

        // 测试查询
        Department foundDepartment = departmentMapper.findById(newDepartment.getDepartmentId());
        assertNotNull(foundDepartment);
        assertEquals("测试科室", foundDepartment.getDepartmentName());

        // 测试更新
        foundDepartment.setDepartmentName("修改后的科室");
        departmentMapper.updateDepartment(foundDepartment);

        Department updatedDepartment = departmentMapper.findById(newDepartment.getDepartmentId());
        assertEquals("修改后的科室", updatedDepartment.getDepartmentName());

        // 测试删除
        departmentMapper.deleteDepartment(newDepartment.getDepartmentId());
        Department deletedDepartment = departmentMapper.findById(newDepartment.getDepartmentId());
        assertNull(deletedDepartment);
    }

    @Test
    @Transactional
    void testBatchOperations() {
        // 测试批量插入
        Department dept1 = new Department();
        dept1.setDepartmentName("批量科室1");

        Department dept2 = new Department();
        dept2.setDepartmentName("批量科室2");

        List<Department> departments = Arrays.asList(dept1, dept2);
        departmentMapper.batchInsertDepartments(departments);

        // 验证插入
        List<Department> allDepartments = departmentMapper.findAll();
        assertTrue(allDepartments.size() >= 2);

        // 测试批量删除
        List<Integer> departmentIds = Arrays.asList(dept1.getDepartmentId(), dept2.getDepartmentId());
        departmentMapper.batchDeleteDepartments(departmentIds);

        // 验证删除
        Department d1 = departmentMapper.findById(dept1.getDepartmentId());
        Department d2 = departmentMapper.findById(dept2.getDepartmentId());
        assertNull(d1);
        assertNull(d2);
    }
}