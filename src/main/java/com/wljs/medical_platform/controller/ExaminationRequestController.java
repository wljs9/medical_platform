package com.wljs.medical_platform.controller;

import com.wljs.medical_platform.entity.ExaminationRequest;
import com.wljs.medical_platform.service.ExaminationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examination-requests")
@CrossOrigin(origins = "*")
public class ExaminationRequestController {
    @Autowired
    private ExaminationRequestService examinationRequestService;

    @GetMapping
    public List<ExaminationRequest> getAllExaminationRequests() {
        return examinationRequestService.getAllExaminationRequests();
    }

    @GetMapping("/{id}")
    public ExaminationRequest getExaminationRequestById(@PathVariable Integer id) {
        return examinationRequestService.getExaminationRequestById(id);
    }

    @GetMapping("/patient/{patientId}")
    public List<ExaminationRequest> getExaminationRequestsByPatient(@PathVariable Integer patientId) {
        return examinationRequestService.getExaminationRequestsByPatient(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<ExaminationRequest> getExaminationRequestsByDoctor(@PathVariable Integer doctorId) {
        return examinationRequestService.getExaminationRequestsByDoctor(doctorId);
    }

    @GetMapping("/status/{status}")
    public List<ExaminationRequest> getExaminationRequestsByStatus(@PathVariable String status) {
        return examinationRequestService.getExaminationRequestsByStatus(status);
    }

    @GetMapping("/count")
    public int getExaminationRequestCount() {
        return examinationRequestService.getExaminationRequestCount();
    }

    // 添加以下接口到现有的ExaminationRequestController中

    @PostMapping
    public ResponseEntity<String> createExaminationRequest(@RequestBody ExaminationRequest examinationRequest) {
        try {
            examinationRequestService.addExaminationRequest(examinationRequest);
            return ResponseEntity.ok("检查申请创建成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("创建失败: " + e.getMessage());
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<String> batchCreateExaminationRequests(@RequestBody List<ExaminationRequest> examinationRequests) {
        try {
            examinationRequestService.batchCreateExaminationRequests(examinationRequests);
            return ResponseEntity.ok("批量创建检查申请成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("批量创建失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateExaminationRequest(@PathVariable Integer id, @RequestBody ExaminationRequest examinationRequest) {
        try {
            examinationRequest.setRequestId(id);
            examinationRequestService.updateExaminationRequestInfo(examinationRequest);
            return ResponseEntity.ok("检查申请信息更新成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("更新失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExaminationRequest(@PathVariable Integer id) {
        try {
            examinationRequestService.deleteExaminationRequestWithTransaction(id);
            return ResponseEntity.ok("检查申请删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("删除失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/patient/{patientId}")
    public ResponseEntity<String> deleteExaminationRequestsByPatient(@PathVariable Integer patientId) {
        try {
            examinationRequestService.deleteExaminationRequestsByPatient(patientId);
            return ResponseEntity.ok("患者检查申请删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("删除失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/doctor/{doctorId}")
    public ResponseEntity<String> deleteExaminationRequestsByDoctor(@PathVariable Integer doctorId) {
        try {
            examinationRequestService.deleteExaminationRequestsByDoctor(doctorId);
            return ResponseEntity.ok("医生检查申请删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("删除失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateExaminationRequestStatus(@PathVariable Integer id, @RequestParam String status) {
        try {
            examinationRequestService.updateExaminationRequestStatus(id, status);
            return ResponseEntity.ok("检查申请状态更新成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("状态更新失败: " + e.getMessage());
        }
    }
}
