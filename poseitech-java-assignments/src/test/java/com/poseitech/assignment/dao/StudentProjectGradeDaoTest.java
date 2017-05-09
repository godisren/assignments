package com.poseitech.assignment.dao;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.poseitech.assignment.MyApplication;
import com.poseitech.assignment.entity.StudentProjectGrade;
import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyApplication.class)
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
		System.out.println("FindByIdProjectId total:" + resutList.size());
		System.out.println(resutList);
	}

	@Test
	public void testFindByIdProjectIdIn() {
		List<StudentProjectGrade> resutList = studentProjectGradeDao
				.findByIdProjectIdIn(Arrays.asList(new Integer[] { 1, 2 }));
		System.out.println("FindByIdProjectIdIn total:" + resutList.size());
		System.out.println(resutList);
	}
	
	@Test
	public void testDeleteByIdProjectIdIn() {
		List<Integer> delProIds = Arrays.asList(new Integer[] {3, 4 });
		studentProjectGradeDao.deleteByIdProjectIdIn(delProIds);
		List<StudentProjectGrade> resutList = studentProjectGradeDao
				.findByIdProjectIdIn(delProIds);
		assertTrue(resutList.size()==0);
	}
}
