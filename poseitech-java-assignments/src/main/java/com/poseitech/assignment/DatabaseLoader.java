package com.poseitech.assignment;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.poseitech.assignment.dao.GradeDao;
import com.poseitech.assignment.dao.ProjectDao;
import com.poseitech.assignment.dao.StudentDao;
import com.poseitech.assignment.dao.StudentProjectGradeDao;
import com.poseitech.assignment.entity.Grade;
import com.poseitech.assignment.entity.Project;
import com.poseitech.assignment.entity.Student;
import com.poseitech.assignment.entity.StudentProjectGrade;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private StudentDao studentDao;
	private ProjectDao projectDao;
	private StudentProjectGradeDao studentProjectGradeDao;
	private GradeDao gradeDao;


	@Autowired
	public DatabaseLoader(StudentDao studentDao, ProjectDao projectDao, StudentProjectGradeDao studentProjectGradeDao,GradeDao gradeDao) {
		this.studentDao = studentDao;
		this.projectDao = projectDao;
		this.studentProjectGradeDao = studentProjectGradeDao;
		this.gradeDao = gradeDao;
	}

	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		// 新增學生
		Student stone = this.studentDao
				.saveOrUpdate(new Student("Stone", df.parse("19990101"), df.parse("20170105"), "handsome"));
		Student naccy = this.studentDao
				.saveOrUpdate(new Student("Naccy", df.parse("20000101"), df.parse("20170215"), "beauty"));
		Student david = this.studentDao
				.saveOrUpdate(new Student("David", df.parse("19800501"), df.parse("20170303"), "nice guy"));
		this.studentDao.saveOrUpdate(new Student("John", df.parse("20010101"), df.parse("20170413"), "handsome"));
		this.studentDao.saveOrUpdate(new Student("Wang", df.parse("20050101"), df.parse("20170505"), "young man"));

		// 新增專案
		Date now = new Date();
		Project proMath = this.projectDao.save(new Project("Math", now, "about math"));
		Project proEng = this.projectDao.save(new Project("English", now, "about English"));
		Project proLiter = this.projectDao.save(new Project("Literature", now, "about Literature"));
		Project proHist = this.projectDao.save(new Project("History", now, "about History"));
		
		// 新增成績評等
		gradeDao.save(new Grade("A", "excellent"));
		gradeDao.save(new Grade("B", "good"));
		gradeDao.save(new Grade("C", "so so"));
		gradeDao.save(new Grade("D", "bad"));

		// 新增學生成績
		// stone
		this.studentProjectGradeDao
				.save(new StudentProjectGrade(new StudentProjectGrade.Key(stone.getId(), proMath.getId()), "A"));
		this.studentProjectGradeDao
				.save(new StudentProjectGrade(new StudentProjectGrade.Key(stone.getId(), proEng.getId()), "B"));
		this.studentProjectGradeDao
				.save(new StudentProjectGrade(new StudentProjectGrade.Key(stone.getId(), proLiter.getId()), "A"));
		this.studentProjectGradeDao
				.save(new StudentProjectGrade(new StudentProjectGrade.Key(stone.getId(), proHist.getId()), "D"));

		// naccy
		this.studentProjectGradeDao
				.save(new StudentProjectGrade(new StudentProjectGrade.Key(naccy.getId(), proMath.getId()), "C"));
		this.studentProjectGradeDao
				.save(new StudentProjectGrade(new StudentProjectGrade.Key(naccy.getId(), proLiter.getId()), "B"));

		// david
		this.studentProjectGradeDao
				.save(new StudentProjectGrade(new StudentProjectGrade.Key(david.getId(), proMath.getId()), "C"));
		this.studentProjectGradeDao
				.save(new StudentProjectGrade(new StudentProjectGrade.Key(david.getId(), proEng.getId()), "A"));
		this.studentProjectGradeDao
				.save(new StudentProjectGrade(new StudentProjectGrade.Key(david.getId(), proLiter.getId()), "A"));

	}

}
