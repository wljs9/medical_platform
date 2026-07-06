package com.wljs.medical_platform.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    private Integer doctorId;
    private String name;
    private String gender;
    private Integer departmentId;
    private String title;
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    // 重写toString方法
    @Override
    public String toString() {
        return String.format("医生ID:%d 姓名:%s 性别:%s 职称:%s 科室ID:%d",
                doctorId, name, gender, title, departmentId);
    }
}
