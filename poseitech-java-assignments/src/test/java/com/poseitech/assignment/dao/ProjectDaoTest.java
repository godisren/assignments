package com.poseitech.assignment.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.poseitech.assignment.MyApplication;
import com.poseitech.assignment.entity.Project;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyApplication.class)
@WebAppConfiguration
@Transactional
public class ProjectDaoTest {

	@Autowired
	private ProjectDao projectDao;

	@Test
	public void testCRUD() {
		Project newPro = new Project();
		newPro.setName("成本會計");
		newPro.setRemark("學習計帳");
		newPro.setCreateDate(new Date());
		projectDao.save(newPro);

		Project findPro = projectDao.findOne(newPro.getId());
		assertNotNull(findPro);

		projectDao.delete(newPro.getId());

		findPro = projectDao.findOne(newPro.getId());
		assertNull(findPro);
	}
	
	@Test
	public void testDelete() {
		List<Integer> delIds = Arrays.asList(new Integer[]{4});
		projectDao.deleteByIdIn(delIds);
	
		assertTrue(projectDao.findAll(delIds).size() == 0);
	}
	
}
