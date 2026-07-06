package com.wljs.medical_platform.controller;

import com.wljs.medical_platform.entity.Doctor;
import com.wljs.medical_platform.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Integer id) {
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/search")
    public List<Doctor> searchDoctors(@RequestParam String name) {
        return doctorService.searchDoctorsByName(name);
    }

    @GetMapping("/department/{departmentId}")
    public List<Doctor> getDoctorsByDepartment(@PathVariable Integer departmentId) {
        return doctorService.getDoctorsByDepartment(departmentId);
    }

    @GetMapping("/count")
    public int getDoctorCount() {
        return doctorService.getDoctorCount();
    }

    // 添加以下接口到现有的DoctorController中

    @PostMapping
    public ResponseEntity<String> createDoctor(@RequestBody Doctor doctor) {
        try {
            doctorService.createDoctorWithTransaction(doctor);
            return ResponseEntity.ok("医生创建成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("创建失败: " + e.getMessage());
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<String> batchCreateDoctors(@RequestBody List<Doctor> doctors) {
        try {
            doctorService.batchCreateDoctors(doctors);
            return ResponseEntity.ok("批量创建医生成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("批量创建失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDoctor(@PathVariable Integer id, @RequestBody Doctor doctor) {
        try {
            doctor.setDoctorId(id);
            doctorService.updateDoctorInfo(doctor);
            return ResponseEntity.ok("医生信息更新成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("更新失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Integer id) {
        try {
            doctorService.deleteDoctorWithRelatedData(id);
            return ResponseEntity.ok("医生删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("删除失败: " + e.getMessage());
        }
    }
}
