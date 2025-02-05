package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	List<Course> findByStudentsId(Long studentId);
	
	List<Course> findByTopicsContaining(String topic);

}
