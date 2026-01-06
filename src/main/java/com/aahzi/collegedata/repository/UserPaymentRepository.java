package com.aahzi.collegedata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aahzi.collegedata.entity.UserPayment;

public interface UserPaymentRepository extends JpaRepository<UserPayment, Long>{

}
