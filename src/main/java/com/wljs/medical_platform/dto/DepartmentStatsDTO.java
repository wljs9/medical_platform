package com.wljs.medical_platform.dto;

import lombok.Data;

@Data
public class DepartmentStatsDTO {
    private String departmentName;
    private Long patientCount;
    private Long visitCount;

    // 可选：添加格式化方法
    public String getDisplayInfo() {
        return String.format("%s: %d患者/%d就诊", departmentName, patientCount, visitCount);
    }
}
