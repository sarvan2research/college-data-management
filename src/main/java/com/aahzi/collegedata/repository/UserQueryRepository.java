package com.aahzi.collegedata.repository;

import com.aahzi.collegedata.entity.UserQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQueryRepository extends JpaRepository<UserQuery, Long> {
    boolean existsByMobileNumberOrEmailId(String mobileNumber, String emailId);
}
