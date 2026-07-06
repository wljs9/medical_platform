package com.wljs.medical_platform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PatientDetailDTO {
    private Integer patientId;
    private String patientName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;
    private String diagnosis;
    private String doctorName;
    private String departmentName;


}
