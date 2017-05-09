package com.poseitech.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.poseitech.assignment.entity.Grade;

@Transactional
public interface GradeDao extends JpaRepository<Grade, String>{
	
}
