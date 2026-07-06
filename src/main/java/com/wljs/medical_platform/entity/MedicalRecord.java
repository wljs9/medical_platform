package com.wljs.medical_platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {
    // Getter和Setter
    private Integer recordId;
    private Integer patientId;
    private Integer doctorId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;
    private String symptoms;
    private String diagnosis;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;


    // 重写toString方法
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("就诊:%s 诊断:%s",
                dateFormat.format(visitDate), diagnosis);
    }

}