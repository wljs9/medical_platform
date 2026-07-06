package com.wljs.medical_platform.controller;

import com.wljs.medical_platform.entity.Patient;
import com.wljs.medical_platform.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Integer id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("/search")
    public List<Patient> searchPatients(@RequestParam String name) {
        return patientService.searchPatientsByName(name);
    }

    @GetMapping("/count")
    public int getPatientCount() {
        return patientService.getPatientCount();
    }

    // 新增的JOIN查询接口
    @GetMapping("/{id}/with-records")
    public Patient getPatientWithRecords(@PathVariable Integer id) {
        return patientService.getPatientWithRecords(id);
    }

    @GetMapping("/{id}/details")
    public List<Map<String, Object>> getPatientDetails(@PathVariable Integer id) {
        return patientService.getPatientDetails(id);
    }

    @GetMapping("/stats/department")
    public List<Map<String, Object>> getDepartmentStats() {
        return patientService.getDepartmentStats();
    }

    @PostMapping
    public ResponseEntity<String> createPatient(@RequestBody Patient patient) {
        try {
            patientService.createPatientWithTransaction(patient);
            return ResponseEntity.ok("患者创建成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("创建失败: " + e.getMessage());
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<String> batchCreatePatients(@RequestBody List<Patient> patients) {
        try {
            patientService.batchCreatePatients(patients);
            return ResponseEntity.ok("批量创建患者成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("批量创建失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable Integer id, @RequestBody Patient patient) {
        try {
            patient.setPatientId(id);
            patientService.updatePatientInfo(patient);
            return ResponseEntity.ok("患者信息更新成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("更新失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Integer id) {
        try {
            patientService.deletePatientWithRecords(id);
            return ResponseEntity.ok("患者删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("删除失败: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/with-all-records")
    public Patient getPatientWithAllRecords(@PathVariable Integer id) {
        return patientService.getPatientWithAllRecords(id);
    }
}