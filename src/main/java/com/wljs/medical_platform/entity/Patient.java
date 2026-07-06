package com.wljs.medical_platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    private Integer patientId;
    private String name;
    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String phone;
    private String idCard;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    // 新增：关联的病历列表（用于JOIN查询结果）
    private List<MedicalRecord> medicalRecords;

    // 新增：关联的检查申请列表（用于JOIN查询结果）
    private List<ExaminationRequest> examinationRequests;





    // 重写toString方法
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return String.format(
                "ID:%d 姓名:%s 性别:%s 生日:%s 电话:%s id_card:%s 创建时间:%s",
                patientId, name, gender, dateFormat.format(birthDate), phone, idCard, timeFormat.format(createdTime)
        );
    }




}