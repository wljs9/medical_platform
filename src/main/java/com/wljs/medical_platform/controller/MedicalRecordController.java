package com.wljs.medical_platform.controller;

import com.wljs.medical_platform.entity.MedicalRecord;
import com.wljs.medical_platform.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
@CrossOrigin(origins = "*")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    @GetMapping("/{id}")
    public MedicalRecord getMedicalRecordById(@PathVariable Integer id) {
        return medicalRecordService.getMedicalRecordById(id);
    }

    @GetMapping("/patient/{patientId}")
    public List<MedicalRecord> getMedicalRecordsByPatient(@PathVariable Integer patientId) {
        return medicalRecordService.getMedicalRecordsByPatient(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<MedicalRecord> getMedicalRecordsByDoctor(@PathVariable Integer doctorId) {
        return medicalRecordService.getMedicalRecordsByDoctor(doctorId);
    }

    @GetMapping("/count")
    public int getMedicalRecordCount() {
        return medicalRecordService.getMedicalRecordCount();
    }

    @PostMapping
    public ResponseEntity<String> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        try {
            medicalRecordService.addMedicalRecord(medicalRecord);
            return ResponseEntity.ok("病历创建成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("创建失败: " + e.getMessage());
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<String> batchCreateMedicalRecords(@RequestBody List<MedicalRecord> medicalRecords) {
        try {
            medicalRecordService.batchCreateMedicalRecords(medicalRecords);
            return ResponseEntity.ok("批量创建病历成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("批量创建失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMedicalRecord(@PathVariable Integer id, @RequestBody MedicalRecord medicalRecord) {
        try {
            medicalRecord.setRecordId(id);
            medicalRecordService.updateMedicalRecordInfo(medicalRecord);
            return ResponseEntity.ok("病历信息更新成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("更新失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicalRecord(@PathVariable Integer id) {
        try {
            medicalRecordService.deleteMedicalRecordWithTransaction(id);
            return ResponseEntity.ok("病历删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("删除失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/patient/{patientId}")
    public ResponseEntity<String> deleteMedicalRecordsByPatient(@PathVariable Integer patientId) {
        try {
            medicalRecordService.deleteMedicalRecordsByPatient(patientId);
            return ResponseEntity.ok("患者病历删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("删除失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/doctor/{doctorId}")
    public ResponseEntity<String> deleteMedicalRecordsByDoctor(@PathVariable Integer doctorId) {
        try {
            medicalRecordService.deleteMedicalRecordsByDoctor(doctorId);
            return ResponseEntity.ok("医生病历删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("删除失败: " + e.getMessage());
        }
    }
}
