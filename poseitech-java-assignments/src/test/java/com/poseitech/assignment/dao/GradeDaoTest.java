package com.poseitech.assignment.dao;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.poseitech.assignment.MyApplication;
import com.poseitech.assignment.entity.Grade;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyApplication.class)
@WebAppConfiguration
@Transactional
public class GradeDaoTest {
	@Autowired
	private GradeDao gradeDao;

	@Test
	public void testCRUD() {
		gradeDao.save(new Grade("A", "excellent"));
		gradeDao.save(new Grade("B", "good"));
		gradeDao.save(new Grade("C", "so so"));
		gradeDao.save(new Grade("D", "bad"));

		assertTrue(gradeDao.findAll().size() > 0);
	}

}
