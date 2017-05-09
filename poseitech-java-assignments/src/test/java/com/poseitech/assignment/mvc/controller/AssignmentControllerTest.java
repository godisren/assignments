package com.poseitech.assignment.mvc.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poseitech.assignment.MyApplication;
import com.poseitech.assignment.dto.NewStudentSuccessDto;
import com.poseitech.assignment.dto.StudentDto;
import com.poseitech.assignment.pojo.StudentStatistics;
import com.poseitech.assignment.service.AssignmentService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyApplication.class)
@WebAppConfiguration
public class AssignmentControllerTest {

	public static final String prefixUrl = "/assignments/api/v1/students";

	private MockMvc mockMvc;

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();
	@InjectMocks
	private AssignmentController target;
	@Mock
	private AssignmentService assignmentService;
	@Autowired
	private ObjectMapper mapper;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}

	// a.查詢特定的學生
	@Test
	public void testGetStudentById() throws Exception {
		StudentDto exceptedStdDto = new StudentDto("stone", new Date(), "20170509", "nice guy");

		when(assignmentService.getStudentById(1)).thenReturn(exceptedStdDto);

		this.mockMvc.perform(get(prefixUrl + "/1/").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content()
						.json(mapper.writeValueAsString(new AssignmentController.ResponseContent(exceptedStdDto))));
	}

	// b.依條件查詢學生
	@Test
	public void testGetStudentByExample() throws Exception {
		String postContent = "{\"remark\": \"handsome\"}";
		List<StudentDto> resultList = Collections
				.singletonList(new StudentDto("stone", new Date(), "20170509", "handsome"));

		when(assignmentService.getStudentsByExample(Matchers.<StudentDto> any())).thenReturn(resultList);

		this.mockMvc
				.perform(post(prefixUrl + "?_method=r").content(postContent)
						.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content()
						.json(mapper.writeValueAsString(new AssignmentController.ResponseContent(resultList))));
	}

	// c.查詢所有學生
	@Test
	public void testGetStudentByPager() throws Exception {
		List<StudentDto> resultList = Collections
				.singletonList(new StudentDto("stone", new Date(), "20170509", "handsome"));

		int start = 0;
		int limit = 5;
		when(assignmentService.getAllStudents(start, limit)).thenReturn(resultList);

		this.mockMvc
				.perform(get(prefixUrl + "?start=0&limit=5")
						.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content()
						.json(mapper.writeValueAsString(new AssignmentController.ResponseContent(resultList))));
	}

	// d.新增一個學生
	@Test
	public void testCreateStudent() throws Exception {
		StudentDto newStdDto = new StudentDto("new man", null, "20170101", "nich guy");

		String postContent = mapper.writeValueAsString(newStdDto);

		when(assignmentService.createStudent(Matchers.<StudentDto> any())).thenReturn(newStdDto);

		this.mockMvc
				.perform(post(prefixUrl + "?_method=c").content(postContent)
						.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().json(mapper.writeValueAsString(
						new AssignmentController.ResponseContent(new NewStudentSuccessDto(newStdDto)))));
	}

	// e.查詢各科成績的學生人數
	@Test
	public void testCalculateGrades() throws Exception {

		List<StudentStatistics> statList = new ArrayList<StudentStatistics>();
		statList.add(new StudentStatistics("math", 1, "A", BigInteger.valueOf(4)));
		statList.add(new StudentStatistics("math", 2, "B", BigInteger.valueOf(4)));
		statList.add(new StudentStatistics("history", 5, "A", BigInteger.valueOf(4)));
		when(assignmentService.calculateStudentStatistics()).thenReturn(statList);

		this.mockMvc
				.perform(get(prefixUrl + "/grades").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(
						content().json(mapper.writeValueAsString(new AssignmentController.ResponseContent(statList))));
	}

}
