package com.aahzi.collegedata.service;

import org.springframework.stereotype.Service;

import com.aahzi.collegedata.entity.UserPayment;
import com.aahzi.collegedata.repository.UserPaymentRepository;

@Service
public class UserPaymentService {
	
	private final UserPaymentRepository repository;
	
	public UserPaymentService(UserPaymentRepository repository) {
		this.repository = repository;
	}
	
	public UserPayment save(UserPayment payment) {
		return repository.save(payment);
	}

}
