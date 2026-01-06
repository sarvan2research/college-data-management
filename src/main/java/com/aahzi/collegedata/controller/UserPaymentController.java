package com.aahzi.collegedata.controller;

import org.springframework.web.bind.annotation.*;

import com.aahzi.collegedata.entity.UserPayment;
import com.aahzi.collegedata.service.UserPaymentService;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:5000")
public class UserPaymentController {
	
	private final UserPaymentService service;
	
	public UserPaymentController(UserPaymentService service) {
		this.service = service;
	}
	
	@PostMapping
	public UserPayment savePayment(@RequestBody UserPayment payment) {
		return service.save(payment);
	}
}
