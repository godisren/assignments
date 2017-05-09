package com.poseitech.assignment.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.poseitech.assignment.MyApplication;
import com.poseitech.assignment.entity.Project;
import com.poseitech.assignment.entity.Student;
import com.poseitech.assignment.pojo.StudentStatistics;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyApplication.class)
@WebAppConfiguration
@Transactional
public class StudentDaoTest {
	@Autowired
	StudentDao studentDao;

	@Test
	public void testFindStudentById() throws Exception {
		Integer studentId = 1;

		Student s = studentDao.findById(studentId);
		assertNotNull(s);
		assertEquals(studentId, s.getId());
	}

	@Test
	public void testFindByExample() throws Exception {
		String exceptedRemark = "handsome";

		Student queryStudent = new Student();
		queryStudent.setRemark(exceptedRemark);

		List<Student> results = studentDao.findByExample(queryStudent);
		assertEquals(2, results.size());
		for (Student s : results) {
			assertEquals(s.getRemark(), exceptedRemark);
		}
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		Student s = new Student();
		s.setName("王大明");
		s.setBirthday(new Date());
		s.setRegisterDate(new Date());
		s.setRemark("師哥");

		studentDao.saveOrUpdate(s);
	}

	@Test
	public void testDelete() throws Exception {
		String randomName = UUID.randomUUID().toString();

		// 新增一筆
		Student s = new Student();
		s.setName(randomName);
		s.setBirthday(new Date());
		s.setRegisterDate(new Date());
		s.setRemark("nice guy");
		Student newStd = studentDao.saveOrUpdate(s);

		// 判斷是否新增成功
		Student findStd = studentDao.findById(newStd.getId());
		assertNotNull(findStd);

		// 執行刪除並確認是否存在
		studentDao.delete(newStd.getId());
		findStd = studentDao.findById(newStd.getId());
		assertNull(findStd);
	}

	@Test
	public void testFindStudentByProjectId() throws Exception {
		Integer mathProjectId = 1;
		List<Student> students = this.studentDao.findStudentByProjectId(mathProjectId);
		assertEquals(3, students.size());
	}

	@Test
	public void testFindProjectsByStudentId() throws Exception {
		Integer stoneStudentId = 1;
		List<Project> projects = this.studentDao.findProjectsByStudentId(stoneStudentId);
		assertEquals(4, projects.size());
	}

	@Test
	public void testFindAllStudents() throws Exception {
		int pStartRowNumber = 1;
		int pFectchSize = 3; // 每次三筆

		List<Student> stdList = this.studentDao.findAllStudents(pStartRowNumber, pFectchSize);

		// 判斷總筆數是否正確
		assertEquals(pFectchSize, stdList.size());

		// 判斷第一筆的id應該為2
		assertEquals(Integer.valueOf(2), stdList.get(0).getId());

	}

	@Test
	public void testFindByHql() throws Exception {
		Integer exceptedId = 2;
		String pHql = "from Student where id= :studentId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("studentId", exceptedId);
		List<Student> stdList = this.studentDao.findByHql(pHql, 0, -1, params);

		assertEquals(1, stdList.size());
		assertEquals(exceptedId, stdList.get(0).getId());
	}

	@Test
	public void testFindByNamedQuery() throws Exception {
		String queryStudentName = "David";
		String pNameQuery = "findStudentByName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", queryStudentName);
		int pStartRowNumber = 0;
		int pFectchSize = 1;

		List<Student> stdList = this.studentDao.findByNamedQuery(pNameQuery, Student.class, params, pStartRowNumber,
				pFectchSize);
		assertEquals(1, stdList.size());
		assertEquals(queryStudentName, stdList.get(0).getName());
	}

	@Test
	public void testCalculateStudentStatistics() throws Exception {
		List<StudentStatistics> statList = studentDao.calculateStudentStatistics();
		assertTrue(statList.size() > 0);
	}
}
