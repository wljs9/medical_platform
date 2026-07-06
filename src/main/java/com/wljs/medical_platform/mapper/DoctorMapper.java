package com.wljs.medical_platform.mapper;

import com.wljs.medical_platform.entity.Doctor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper  // 必须有这个注解
public interface DoctorMapper {


    List<Doctor> findAll();

    Doctor findById(Integer doctorId);

    List<Doctor> findByName(String name);

    List<Doctor> findByDepartmentId(Integer departmentId);

    int countDoctors();

    // 插入数据
    int insertDoctor(Doctor doctor);

    // 批量插入数据
    int batchInsertDoctors(List<Doctor> doctors);

    // 修改数据
    int updateDoctor(Doctor doctor);

    // 删除数据
    int deleteDoctor(Integer doctorId);

    // 批量删除数据
    int batchDeleteDoctors(List<Integer> doctorIds);
}