package com.poseitech.assignment.dao;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.poseitech.assignment.MyApplication;
import com.poseitech.assignment.entity.StudentProjectGrade;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyApplication.class)
@WebAppConfiguration
@Transactional
public class StudentProjectGradeDaoTest {
	@Autowired
	private StudentProjectGradeDao studentProjectGradeDao;

	@Test
	public void testSave() {
		StudentProjectGrade spg = new StudentProjectGrade();
		spg.setId(new StudentProjectGrade.Key(1, 2));
		spg.setGradelevel("B");
		studentProjectGradeDao.save(spg);
	}

	@Test
	public void testFindByIdProjectId() {
		List<StudentProjectGrade> resutList = studentProjectGradeDao.findByIdProjectId(1);
		Assert.assertTrue(resutList.size() > 0);
	}

	@Test
	public void testFindByIdProjectIdIn() {
		List<StudentProjectGrade> resutList = studentProjectGradeDao
				.findByIdProjectIdIn(Arrays.asList(new Integer[] { 1, 2 }));
		Assert.assertTrue(resutList.size() > 0);		
	}

	@Test
	public void testDeleteByIdProjectIdIn() {
		List<Integer> delProIds = Arrays.asList(new Integer[] { 3, 4 });
		studentProjectGradeDao.deleteByIdProjectIdIn(delProIds);
		List<StudentProjectGrade> resutList = studentProjectGradeDao.findByIdProjectIdIn(delProIds);
		assertTrue(resutList.size() == 0);
	}
}
