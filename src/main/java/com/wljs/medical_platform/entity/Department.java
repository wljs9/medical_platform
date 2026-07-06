package com.wljs.medical_platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private Integer departmentId;
    private String departmentName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @Override
    public String toString() {
        return String.format("科室ID:%d 名称:%s 创建时间:%s", departmentId, departmentName,createdTime);
    }
}
