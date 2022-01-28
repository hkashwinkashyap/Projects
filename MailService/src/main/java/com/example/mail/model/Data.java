package com.example.mail.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Data {
	private String name;
	@Id
	private String email;
	private Long phone;

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

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Data [name=" + name + ", email=" + email + ", phone=" + phone + "]";
	}

}
