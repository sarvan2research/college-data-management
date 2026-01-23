package com.aahzi.collegedata.controller;

import com.aahzi.collegedata.dto.UserQueryDTO;
import com.aahzi.collegedata.service.UserQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-query")
@CrossOrigin(origins = { "https://internal.aahzi.com/api/v1/user-query", "https://aahzi.com", "http://localhost:5000" })
public class UserQueryController {

    private final UserQueryService service;

    public UserQueryController(UserQueryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserQueryDTO> create(@RequestBody UserQueryDTO dto) {
        try {
            UserQueryDTO created = service.create(dto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            if ("Free limit exceeded".equals(e.getMessage())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
            throw e;
        }
    }

    @GetMapping
    public List<UserQueryDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserQueryDTO> getById(@PathVariable Long id) {
        try {
            UserQueryDTO dto = service.getById(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserQueryDTO> update(@PathVariable Long id, @RequestBody UserQueryDTO dto) {
        try {
            UserQueryDTO updated = service.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
