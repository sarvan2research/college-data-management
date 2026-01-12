package com.aahzi.collegedata.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_payments")
public class UserPayment extends BaseEntity {

	private String name;
	private String email;
	private String phoneNumber;

	private String paymentMethod;

	private String upiId;

	private String cardNumber;
	private String cardHolderName;
	private String expiry;
	private String cvv;

	public UserPayment() {
	}

	public UserPayment(String name, String email, String phoneNumber, String paymentMethod,
			String upiId, String cardNumber, String cardHolderName, String expiry, String cvv) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.paymentMethod = paymentMethod;
		this.upiId = upiId;
		this.cardNumber = cardNumber;
		this.cardHolderName = cardHolderName;
		this.expiry = expiry;
		this.cvv = cvv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getUpiId() {
		return upiId;
	}

	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	@Override
	public String toString() {
		return "UserPayment [id=" + id + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", paymentMethod=" + paymentMethod + ", upiId=" + upiId + ", cardNumber=" + cardNumber
				+ ", cardHolderName=" + cardHolderName + ", expiry=" + expiry + ", cvv=" + cvv + "]";
	}
}
