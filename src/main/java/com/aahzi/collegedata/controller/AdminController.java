package com.aahzi.collegedata.controller;

import com.aahzi.collegedata.entity.Admin;
import com.aahzi.collegedata.repository.AdminRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = {"http://localhost:5000", "https://internal.aahzi.com/api/admin", "https://aahzi-2026.netlify.app/api/admin"})
public class AdminController {

    private final AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        return adminRepository.findByUsername(username)
                .map(admin -> {
                    if (admin.getPassword().equals(password)) {
                        return ResponseEntity.ok(Map.of("message", "Login successful", "username", username));
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
                })
                .orElseGet(() -> {
                    // For initial setup, if no admin exists and using default creds, create one
                    if ("admin".equals(username) && "admin123".equals(password)) {
                        Admin newAdmin = new Admin(username, password);
                        adminRepository.save(newAdmin);
                        return ResponseEntity
                                .ok(Map.of("message", "Login successful produced default admin", "username", username));
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
                });
    }
}
