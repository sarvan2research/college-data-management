package com.aahzi.collegedata.service;

import com.aahzi.collegedata.dto.UserQueryDTO;
import com.aahzi.collegedata.entity.UserQuery;
import com.aahzi.collegedata.repository.UserQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserQueryService {

    private final UserQueryRepository repository;

    public UserQueryService(UserQueryRepository repository) {
        this.repository = repository;
    }

    public UserQueryDTO create(UserQueryDTO dto) {
        if ("free".equalsIgnoreCase(dto.getPaymentMode())) {
            if (repository.existsByMobileNumberOrEmailId(dto.getMobileNumber(), dto.getEmailId())) {
                throw new RuntimeException("Free limit exceeded");
            }
        }

        UserQuery entity = mapToEntity(dto);
        // Ensure ID is null for creation
        entity.setId(null);
        UserQuery savedEntity = repository.save(entity);
        return mapToDTO(savedEntity);
    }

    public List<UserQueryDTO> getAll() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public UserQueryDTO getById(Long id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("UserQuery not found with id: " + id));
    }

    public UserQueryDTO update(Long id, UserQueryDTO dto) {
        UserQuery existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserQuery not found with id: " + id));

        // Update fields
        existing.setName(dto.getName());
        existing.setMobileNumber(dto.getMobileNumber());
        existing.setEmailId(dto.getEmailId());
        existing.setPaymentMode(dto.getPaymentMode());
        existing.setUpiId(dto.getUpiId());
        existing.setServiceType(dto.getServiceType());
        existing.setQueryRequest(dto.getQueryRequest());
        existing.setQueryResult(dto.getQueryResult());

        // Note: version, timeCreated, timeUpdated are handled by JPA/Entity

        UserQuery updated = repository.save(existing);
        return mapToDTO(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("UserQuery not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private UserQuery mapToEntity(UserQueryDTO dto) {
        UserQuery entity = new UserQuery();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setMobileNumber(dto.getMobileNumber());
        entity.setEmailId(dto.getEmailId());
        entity.setPaymentMode(dto.getPaymentMode());
        entity.setUpiId(dto.getUpiId());
        entity.setServiceType(dto.getServiceType());
        entity.setQueryRequest(dto.getQueryRequest());
        entity.setQueryResult(dto.getQueryResult());
        entity.setVersion(dto.getVersion());
        // timeCreated and timeUpdated are usually managed by entity, not set from DTO
        // on creation
        return entity;
    }

    private UserQueryDTO mapToDTO(UserQuery entity) {
        return new UserQueryDTO(
                entity.getId(),
                entity.getName(),
                entity.getMobileNumber(),
                entity.getEmailId(),
                entity.getPaymentMode(),
                entity.getUpiId(),
                entity.getServiceType(),
                entity.getQueryRequest(),
                entity.getQueryResult(),
                entity.getTimeCreated(),
                entity.getTimeUpdated(),
                entity.getVersion());
    }
}
