package com.wljs.medical_platform.controller;

import com.wljs.medical_platform.entity.Department;
import com.wljs.medical_platform.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Integer id) {
        return departmentService.getDepartmentById(id);
    }

    @GetMapping("/name/{name}")
    public Department getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartmentByName(name);
    }

    @GetMapping("/count")
    public int getDepartmentCount() {
        return departmentService.getDepartmentCount();
    }


    @PostMapping
    public ResponseEntity<String> createDepartment(@RequestBody Department department) {
        try {
            departmentService.addDepartment(department);
            return ResponseEntity.ok("科室创建成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("创建失败: " + e.getMessage());
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<String> batchCreateDepartments(@RequestBody List<Department> departments) {
        try {
            departmentService.batchCreateDepartments(departments);
            return ResponseEntity.ok("批量创建科室成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("批量创建失败: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDepartment(@PathVariable Integer id, @RequestBody Department department) {
        try {
            department.setDepartmentId(id);
            departmentService.updateDepartmentInfo(department);
            return ResponseEntity.ok("科室信息更新成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("更新失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Integer id) {
        try {
            departmentService.deleteDepartmentWithRelatedData(id);
            return ResponseEntity.ok("科室删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("删除失败: " + e.getMessage());
        }
    }
}
