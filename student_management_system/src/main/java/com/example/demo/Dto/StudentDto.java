package com.example.demo.Dto;

import java.util.List;

import com.example.demo.model.StudentAddress;

public class StudentDto {

	private String uniqueStudentCode;
	private String name;
	private String dateOfBirth;
	private String gender;

	private String email;
	private String mobileNumber;
	private String parentsName;
	private List<StudentAddressDto> addresses;

	public String getUniqueStudentCode() {
		return uniqueStudentCode;
	}

	public void setUniqueStudentCode(String uniqueStudentCode) {
		this.uniqueStudentCode = uniqueStudentCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getParentsName() {
		return parentsName;
	}

	public void setParentsName(String parentsName) {
		this.parentsName = parentsName;
	}

	public List<StudentAddressDto> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<StudentAddressDto> addresses) {
		this.addresses = addresses;
	}

}
