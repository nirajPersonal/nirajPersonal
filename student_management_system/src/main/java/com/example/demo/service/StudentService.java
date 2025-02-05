package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.repo.CourseRepository;
import com.example.demo.repo.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	// 1. Admit a student
	public Student admitStudent(Student student) {
		return studentRepository.save(student);
	}

	// 2. Search students by name
	public List<Student> searchStudentByName(String name) {
		return studentRepository.findByName(name);
	}

	// 3. Assign courses to student
	public Student assignCoursesToStudent(Long studentId, List<Long> courseIds) {
		Optional<Student> student = studentRepository.findById(studentId);
		if (student.isPresent()) {
			List<Course> courses = courseRepository.findAllById(courseIds);
			student.get().getCourses().addAll(courses);
			return studentRepository.save(student.get());
		}
		return null;
	}

	public Student updateStudentProfile(Long studentId, Student studentDetails) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new RuntimeException("Student not found"));
		student.setEmail(studentDetails.getEmail());
		student.setMobileNumber(studentDetails.getMobileNumber());
		student.setParentsName(studentDetails.getParentsName());
		student.setAddresses(studentDetails.getAddresses());
		return studentRepository.save(student);
	}

	public List<Course> getCoursesByStudent(Long studentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new RuntimeException("Student not found"));
		return student.getCourses();
	}

	public String leaveCourse(Long studentId, Long courseId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new RuntimeException("Student not found"));
		Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
		student.getCourses().remove(course);
		studentRepository.save(student);
		return "Student has successfully left the course.";
	}
}
