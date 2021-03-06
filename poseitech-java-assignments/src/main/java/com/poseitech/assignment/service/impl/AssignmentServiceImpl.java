package com.poseitech.assignment.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseitech.assignment.dao.ProjectDao;
import com.poseitech.assignment.dao.StudentDao;
import com.poseitech.assignment.dao.StudentProjectGradeDao;
import com.poseitech.assignment.dto.ProjectDto;
import com.poseitech.assignment.dto.StudentDto;
import com.poseitech.assignment.entity.Project;
import com.poseitech.assignment.entity.Student;
import com.poseitech.assignment.entity.StudentProjectGrade;
import com.poseitech.assignment.pojo.StudentStatistics;
import com.poseitech.assignment.service.AssignmentService;

@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentServiceImpl.class);

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private StudentProjectGradeDao studentProjectGradeDao;

	@Override
	public StudentDto createStudent(StudentDto pNewStudent) throws Exception {
		return StudentDto.valueOf(studentDao.saveOrUpdate(pNewStudent.toStudent()));
	}

	@Override
	public StudentDto getStudentById(Integer id) throws Exception {
		return StudentDto.valueOf(studentDao.findById(id));
	}

	@Override
	public List<StudentDto> getAllStudents(int pStartRowNumber, int pLimit) throws Exception {
		List<Student> stuList = studentDao.findAllStudents(pStartRowNumber, pLimit);

		return toStudentDtoList(stuList);
	}

	@Override
	public boolean deleteStudent(StudentDto pNewStudent) throws Exception {
		try {
			studentDao.delete(pNewStudent.getId());
		} catch (Exception e) {
			LOGGER.error("刪除學生錯誤(" + pNewStudent + ")", e);
			return false;
		}
		return true;
	}

	@Override
	public List<ProjectDto> getInterestedProjects(StudentDto pStudent) throws Exception {
		List<Project> projects = studentDao.findProjectsByStudentId(pStudent.getId());
		List<ProjectDto> proDtoList = new ArrayList<ProjectDto>();
		for (Project p : projects)
			proDtoList.add(ProjectDto.valueOf(p));
		return proDtoList;
	}

	@Override
	public StudentDto registerProjects(Integer pStudendId, List<ProjectDto> pProjects) throws Exception {
		List<StudentProjectGrade> spgGpgList = new ArrayList<StudentProjectGrade>();
		for (ProjectDto proDto : pProjects) {
			spgGpgList.add(new StudentProjectGrade(new StudentProjectGrade.Key(pStudendId, proDto.getId())));
		}

		studentProjectGradeDao.save(spgGpgList);

		return StudentDto.valueOf(studentDao.findById(pStudendId));
	}

	@Override
	public List<ProjectDto> createProjects(List<ProjectDto> pProjects) throws Exception {
		List<ProjectDto> proDtoList = new ArrayList<ProjectDto>();
		for (ProjectDto proDto : pProjects)
			proDtoList.add(ProjectDto.valueOf(projectDao.save(proDto.toProject())));

		return proDtoList;
	}

	@Override
	public boolean deleteProjects(List<Integer> pProjectsIds) throws Exception {
		try {
			// 刪除關聯到學生的專案
			studentProjectGradeDao.deleteByIdProjectIdIn(pProjectsIds);
			projectDao.deleteByIdIn(pProjectsIds);

			return true;
		} catch (Exception e) {
			e.printStackTrace();

		}

		return false;
	}

	@Override
	public List<StudentStatistics> calculateStudentStatistics() throws Exception {
		return this.studentDao.calculateStudentStatistics();
	}

	@Override
	public List<StudentDto> getStudentsByExample(StudentDto studentDto) throws Exception {
		return toStudentDtoList(this.studentDao.findByExample(studentDto.toStudent()));
	}

	@Override
	public List<ProjectDto> getProjectsByIds(List<Integer> projectIds) {
		return toProjectDtoList(this.projectDao.findAll(projectIds));
	}

	@Override
	public List<ProjectDto> getAllProjects() {
		return toProjectDtoList(this.projectDao.findAll());
	}

	protected List<ProjectDto> toProjectDtoList(List<Project> proList) {
		if (CollectionUtils.isEmpty(proList))
			return Collections.emptyList();

		List<ProjectDto> proDtoList = new ArrayList<ProjectDto>(proList.size());
		for (Project s : proList)
			proDtoList.add(ProjectDto.valueOf(s));

		return proDtoList;
	}

	protected List<StudentDto> toStudentDtoList(List<Student> stuList) {
		if (CollectionUtils.isEmpty(stuList))
			return Collections.emptyList();

		List<StudentDto> stuDtoList = new ArrayList<StudentDto>(stuList.size());
		for (Student s : stuList)
			stuDtoList.add(StudentDto.valueOf(s));

		return stuDtoList;
	}

}
