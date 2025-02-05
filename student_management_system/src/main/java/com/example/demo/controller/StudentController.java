package com.example.demo.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.StudentDto;
import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Common common;

	// Can admit a student to the school by admin
	@PostMapping("/admit")
	public ResponseEntity<Student> admitStudent(HttpServletRequest req, @RequestBody StudentDto student) {
		User userDetails = userRepository.findByUsername(common.getRoleByToken(req));
		if (userDetails != null && userDetails.getRole().equalsIgnoreCase(Constants.ADMIN)) {
			Student students = modelMapper.map(student, Student.class);
			return new ResponseEntity<>(studentService.admitStudent(students), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	// Search for students by name for student
	@GetMapping("/search")
	public ResponseEntity<List<Student>> searchStudentByName(@RequestParam String name) {
		return new ResponseEntity<>(studentService.searchStudentByName(name), HttpStatus.OK);
	}

	// Can get students assigned to a particular course by admin
	@PostMapping("/assign-courses/{studentId}")
	public ResponseEntity<Student> assignCoursesToStudent(@PathVariable Long studentId,
			@RequestBody List<Long> courseIds, HttpServletRequest req) {
		User userDetails = userRepository.findByUsername(common.getRoleByToken(req));
		if (userDetails != null && userDetails.getRole().equalsIgnoreCase(Constants.ADMIN)) {
			return new ResponseEntity<>(studentService.assignCoursesToStudent(studentId, courseIds), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

//	 Can update profile details for student
	@PostMapping("/update-Profile/{studentId}")
	public Student updateProfile(@PathVariable Long studentId, @RequestBody StudentDto studentDetails,
			HttpServletRequest req) {
		User userDetails = userRepository.findByUsername(common.getRoleByToken(req));
		if (userDetails != null && userDetails.getRole().equalsIgnoreCase(Constants.STUDENT)) {
			Student students = modelMapper.map(studentDetails, Student.class);
			return studentService.updateStudentProfile(studentId, students);
		} else {
			return null;
		}

	}

	// Can get students assigned to a particular course for admin
	@GetMapping("/courses/{studentId}")
	public List<Course> getCoursesByStudent(@PathVariable Long studentId, HttpServletRequest req) {
		User userDetails = userRepository.findByUsername(common.getRoleByToken(req));
		if (userDetails != null && userDetails.getRole().equalsIgnoreCase(Constants.ADMIN)) {
			return studentService.getCoursesByStudent(studentId);
		} else {
			return null;
		}
	}

	// Can leave a course for student
	@DeleteMapping("/leave-course/{studentId}/{courseId}")
	public String leaveCourse(@PathVariable Long studentId, @PathVariable Long courseId, HttpServletRequest req) {
		User userDetails = userRepository.findByUsername(common.getRoleByToken(req));
		if (userDetails != null && userDetails.getRole().equalsIgnoreCase(Constants.STUDENT)) {
			return studentService.leaveCourse(studentId, courseId);
		} else {
			return null;
		}
	}
}
