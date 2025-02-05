package com.example.demo.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.CourseDto;
import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.CourseService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Common common;

	// Can Upload course details by admin
	@PostMapping("/upload")
	public ResponseEntity<Course> uploadCourse(@RequestBody CourseDto course, HttpServletRequest req) {
		User userDetails = userRepository.findByUsername(common.getRoleByToken(req));
		if (userDetails != null && userDetails.getRole().equalsIgnoreCase(Constants.ADMIN)) {
			Course courses = modelMapper.map(course, Course.class);
			return new ResponseEntity<>(courseService.uploadCourse(courses), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	// Can assign courses to student by admin
	@GetMapping("/assigned/{studentId}")
	public ResponseEntity<List<Course>> getCoursesByStudent(@PathVariable Long studentId, HttpServletRequest req) {
		User userDetails = userRepository.findByUsername(common.getRoleByToken(req));
		if (userDetails != null && userDetails.getRole().equalsIgnoreCase(Constants.ADMIN)) {
			return new ResponseEntity<>(courseService.getCoursesByStudent(studentId), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	// Can get students by name.(search) by admin
	@GetMapping("/search")
	public List<Course> searchCoursesByTopic(@RequestParam String topic, HttpServletRequest req) {
		User userDetails = userRepository.findByUsername(common.getRoleByToken(req));
		if (userDetails != null && userDetails.getRole().equalsIgnoreCase(Constants.ADMIN)) {
			return courseService.findCoursesByTopic(topic);
		} else {
			return null;
		}
	}
}