package com.poseitech.assignment.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.poseitech.assignment.dao.StudentDao;
import com.poseitech.assignment.entity.Project;
import com.poseitech.assignment.entity.Student;
import com.poseitech.assignment.pojo.StudentStatistics;

@Transactional
@Repository
public class StudentDaoImpl implements StudentDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Student saveOrUpdate(Student pStudent) throws Exception {
		// saveOrUpdate(hibernate feature)
		getSession().saveOrUpdate(pStudent);
		return pStudent;
	}

	@Override
	public Student findById(Integer pStudentId) throws Exception {
		return entityManager.find(Student.class, pStudentId);
	}

	@Override
	public List<Student> findByExample(Student pStudent) throws Exception {
		// find by Example(hibernate feature)

		if (pStudent.getId() != null)
			return Collections.singletonList(this.findById(pStudent.getId()));

		Session session = this.getSession();
		Example customerExample = Example.create(pStudent);// .excludeZeroes();
		Criteria criteria = session.createCriteria(Student.class).add(customerExample);
		return criteria.list();
	}

	@Override
	public List<Student> findStudentByProjectId(Integer pProjectId) throws Exception {
		String hql = "select s FROM Student s where s.id in ("
				+ "select g.id.studentId from StudentProjectGrade g where g.id.projectId=:projectId)";

		return this.entityManager.createQuery(hql).setParameter("projectId", pProjectId).getResultList();
	}

	@Override
	public List<Project> findProjectsByStudentId(Integer studentId) throws Exception {
		String hql = "select p FROM Project p where p.id in ("
				+ "select g.id.projectId from StudentProjectGrade g where g.id.studentId=:studentId)";

		return this.entityManager.createQuery(hql).setParameter("studentId", studentId).getResultList();
	}

	@Override
	public Student delete(Integer pStudentId) throws Exception {
		Student s = this.findById(pStudentId);

		if (s == null)
			return null;

		this.entityManager.remove(s);

		return s;
	}

	@Override
	public List<Student> findAllStudents(int pStartRowNumber, int pFectchSize) throws Exception {
		return findByHql("FROM Student s", pStartRowNumber, pFectchSize, new HashMap<String, Object>());
	}

	@Override
	public List<Student> findByHql(String pHql, int pStartRowNumber, int pFectchSize, Map<String, Object> params)
			throws Exception {
		Query query = this.entityManager.createQuery(pHql);

		if (params != null)
			for (Map.Entry<String, Object> entry : params.entrySet())
				query.setParameter(entry.getKey(), entry.getValue());

		if (pStartRowNumber > 0)
			query.setFirstResult(pStartRowNumber);

		if (pFectchSize > 0)
			query.setMaxResults(pFectchSize);

		return query.getResultList();
	}

	@Override
	public List<Student> findByNamedQuery(String pNameQuery, Class<?> clazz, Map<String, Object> params,
			int pStartRowNumber, int pFectchSize) throws Exception {

		TypedQuery<Student> query = (TypedQuery<Student>) entityManager.createNamedQuery(pNameQuery, clazz);

		if (params != null)
			for (Map.Entry<String, Object> entry : params.entrySet())
				query.setParameter(entry.getKey(), entry.getValue());

		if (pStartRowNumber > 0)
			query.setFirstResult(pStartRowNumber);

		if (pFectchSize > 0)
			query.setMaxResults(pFectchSize);

		return query.getResultList();
	}

	@Override
	public List<StudentStatistics> calculateStudentStatistics() throws Exception {
		String sql = "select p.name, projectId, gradelevel, count(studentId) as cnt from strudent_project_grade g "
				+ " left join project p on g.projectId = p.id group by name, projectId, gradelevel";

		List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();
		List<StudentStatistics> statList = new ArrayList<StudentStatistics>();
		results.stream().forEach((record) -> {
			statList.add(new StudentStatistics((String) record[0], (Integer) record[1], String.valueOf(record[2]),
					(BigInteger) record[3]));
		});

		return statList;
	}

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}
}
