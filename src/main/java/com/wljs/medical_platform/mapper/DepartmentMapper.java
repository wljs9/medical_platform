package com.wljs.medical_platform.mapper;

import com.wljs.medical_platform.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DepartmentMapper {

    List<Department> findAll();

    Department findById(Integer departmentId);

    Department findByName(String departmentName);

    int countDepartments();

    int insertDepartment(Department department);

    // 批量插入数据
    int batchInsertDepartments(List<Department> departments);

    // 修改数据
    int updateDepartment(Department department);

    // 删除数据
    int deleteDepartment(Integer departmentId);

    // 批量删除数据
    int batchDeleteDepartments(List<Integer> departmentIds);
}
