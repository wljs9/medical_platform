package com.wljs.medical_platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExaminationRequest {
    private Integer requestId;
    private Integer patientId;
    private Integer doctorId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date requestDate;

    private String examinationType;
    private String status; // pending, completed, cancelled

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @Override
    public String toString() {
        return String.format("检查ID:%d 患者ID:%d 类型:%s 状态:%s",
                requestId, patientId, examinationType, status);
    }
}
