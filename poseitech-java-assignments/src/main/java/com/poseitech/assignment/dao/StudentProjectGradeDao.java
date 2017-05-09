package com.poseitech.assignment.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poseitech.assignment.entity.StudentProjectGrade;

@Transactional
public interface StudentProjectGradeDao extends JpaRepository<StudentProjectGrade, StudentProjectGrade.Key> {

	public List<StudentProjectGrade> findByIdProjectId(Integer projectId);
	
	public List<StudentProjectGrade> findByIdProjectIdIn(List<Integer> projectIds);
	
	public void deleteByIdProjectIdIn(List<Integer> projectIds);
}
