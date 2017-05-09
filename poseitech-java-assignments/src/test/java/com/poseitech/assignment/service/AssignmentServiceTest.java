package com.poseitech.assignment.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.poseitech.assignment.MyApplication;
import com.poseitech.assignment.dto.ProjectDto;
import com.poseitech.assignment.dto.StudentDto;

/**
 * 部份測試案例已於相關dao測試，在此不重覆驗證相關關邏輯，僅對主要商務驗證
 * 
 * @author Stone
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyApplication.class)
@WebAppConfiguration
@Transactional
public class AssignmentServiceTest {

	@Autowired
	private AssignmentService assignmentService;

	/**
	 * <pre>
	 * 	【故事案例】
	 *  建立新課程及學生，並且將新課程註冊給新學生，
	 *  隨後分別將課程及學生刪除，驗證每一步驟有效性
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testStory() throws Exception {
		// 建立尚不存在的2個課程
		Date now = new Date();
		List<ProjectDto> pProjects = new ArrayList<ProjectDto>();
		pProjects.add(new ProjectDto("Computer", now, "about Computer"));
		pProjects.add(new ProjectDto("Social", now, "about Social"));

		int exceptedNewProjectSize = pProjects.size();

		List<ProjectDto> newProjects = assignmentService.createProjects(pProjects);

		// 確認課程是否新增成功
		List<Integer> newProjectIds = new ArrayList<Integer>();
		for (ProjectDto proDto : newProjects) {
			newProjectIds.add(proDto.getId());
		}
		List<ProjectDto> findProjects = assignmentService.getProjectsByIds(newProjectIds);
		assertEquals(exceptedNewProjectSize, findProjects.size());

		// 建立新學生
		String randomName = UUID.randomUUID().toString();
		StudentDto pNewStudent = new StudentDto(randomName, new Date(), "20170509", "nice guy");
		pNewStudent = assignmentService.createStudent(pNewStudent);

		// 將以上課程註冊給新學生(尚未註冊任何課程)
		pNewStudent = assignmentService.registerProjects(pNewStudent.getId(), newProjects);

		// 判斷學生註冊課程數是否正確
		List<ProjectDto> registerProjects = assignmentService.getInterestedProjects(pNewStudent);
		assertEquals(exceptedNewProjectSize, registerProjects.size());

		// 刪除新增專案，並且判斷學生所註冊的課程是否也不存在
		assignmentService.deleteProjects(newProjectIds);
		registerProjects = assignmentService.getInterestedProjects(pNewStudent);
		assertEquals(0, registerProjects.size());

		// 刪除學生，並且判斷學生已刪除
		assignmentService.deleteStudent(pNewStudent);
		assertNull(assignmentService.getStudentById(pNewStudent.getId()));

	}

}
