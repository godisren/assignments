package com.poseitech.assignment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.poseitech.assignment.entity.Project;

@Transactional
public interface ProjectDao extends JpaRepository<Project, Integer> {
	public void deleteByIdIn(List<Integer> projectIds);
}
