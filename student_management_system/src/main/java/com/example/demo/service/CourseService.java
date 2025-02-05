package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Course;
import com.example.demo.repo.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	// 1. Upload course details
	public Course uploadCourse(Course course) {
		return courseRepository.save(course);
	}

	// 2. Get courses by student
	public List<Course> getCoursesByStudent(Long studentId) {
		return courseRepository.findByStudentsId(studentId);
	}

	public List<Course> findCoursesByTopic(String topic) {
		return courseRepository.findByTopicsContaining(topic); 

	}
}
