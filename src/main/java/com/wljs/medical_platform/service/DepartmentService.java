package com.wljs.medical_platform.service;

import com.wljs.medical_platform.entity.Department;
import com.wljs.medical_platform.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Department> getAllDepartments() {
        return departmentMapper.findAll();
    }

    public Department getDepartmentById(Integer departmentId) {
        return departmentMapper.findById(departmentId);
    }

    public Department getDepartmentByName(String departmentName) {
        return departmentMapper.findByName(departmentName);
    }

    public int getDepartmentCount() {
        return departmentMapper.countDepartments();
    }

    @Transactional
    public void addDepartment(Department department) {
        departmentMapper.insertDepartment(department);
    }

    @Transactional
    public void batchCreateDepartments(List<Department> departments) {
        departmentMapper.batchInsertDepartments(departments);
    }

    @Transactional
    public void updateDepartmentInfo(Department department) {
        departmentMapper.updateDepartment(department);
    }

    @Transactional
    public void deleteDepartmentWithRelatedData(Integer departmentId) {
        // 注意：由于department_id被其他表引用，删除前需要处理外键约束
        // 在实际项目中，我们可能会检查是否有医生属于该科室，如果有则不能删除，或者级联删除
        // 这里我们假设允许删除，并且外键约束设置为RESTRICT或NO ACTION，那么删除会失败
        // 因此，我们先不实现关联删除，仅删除科室（如果外键约束允许，或者先更新医生表的科室为null）
        departmentMapper.deleteDepartment(departmentId);
    }
}
