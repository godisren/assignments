package com.poseitech.assignment.mvc.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poseitech.assignment.dto.NewStudentSuccessDto;
import com.poseitech.assignment.dto.StudentDto;
import com.poseitech.assignment.service.AssignmentService;

@RestController
@RequestMapping("/assignments/api/v1/students")
public class AssignmentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentController.class);

	@Autowired
	private Environment environment;
	@Autowired
	private AssignmentService assignmentService;

	// a.查詢特定的學生(GET)
	@RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
	public @ResponseBody ResponseContent getStudentById(@PathVariable("studentId") Integer id) {
		try {
			return new ResponseContent(assignmentService.getStudentById(id), null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContent(StringUtils.EMPTY, e.getMessage());
		}
	}

	// c. 查詢所有學生
	@RequestMapping(method = { RequestMethod.GET })
	public @ResponseBody ResponseContent getStudentByPager(
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit) {

		if (start == null || limit == null)
			return ResponseContent.ERROR_INVALID_PARAMETER;

		try {
			return new ResponseContent(assignmentService.getAllStudents(start, limit));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContent(StringUtils.EMPTY, e.getMessage());
		}
	}

	@RequestMapping(method = { RequestMethod.POST })
	public @ResponseBody ResponseContent getStudentByQueryOrCreate(
			@RequestParam(value = "_method", required = false) String method, @RequestBody String body) {

		if (StringUtils.isBlank(body))
			return ResponseContent.ERROR_INVALID_PARAMETER;

		try {

			if (StringUtils.isNotBlank(method)) {
				StudentDto studentDto = StudentDto.valueOf(body);

				switch (method) {
				case "r":
					// b.依條件查詢學生
					return new ResponseContent(assignmentService.getStudentsByExample(studentDto));
				case "c":
					// d. 新增一個學生
					NewStudentSuccessDto successStdDto = new NewStudentSuccessDto(
							assignmentService.createStudent(studentDto));
					System.out.println(successStdDto.getUrl());
					
					return new ResponseContent(successStdDto);
				}
			}

			// c. 查詢所有學生(post)
			PagerBean pager = PagerBean.valueOf(body);
			if (pager.getStart() != null && pager.getLimit() != null) {
				return new ResponseContent(assignmentService.getAllStudents(pager.getStart(), pager.getLimit()));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContent(StringUtils.EMPTY, e.getMessage());
		}

		return ResponseContent.ERROR_INVALID_PARAMETER;
	}

	// e. 查詢各科成績的學生人數
	@RequestMapping(value = "/grades", method = { RequestMethod.GET })
	public @ResponseBody ResponseContent calculateGrades() {
		try {
			return new ResponseContent(this.assignmentService.calculateStudentStatistics());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContent(StringUtils.EMPTY, e.getMessage());
		}
	}

	public String getHostUrl() {
		try {
			return "http://" + InetAddress.getLocalHost().getHostAddress() + ":"
					+ environment.getProperty("server.port");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "http://127.0.0.1:8080";
		}
	}

	public static class ResponseContent {
		public static final ResponseContent ERROR_INVALID_PARAMETER = new ResponseContent(null, "無效的參數");

		private Object data;
		private String error;

		public ResponseContent() {
			super();
		}

		public ResponseContent(Object data) {
			super();
			this.data = data;
		}

		public ResponseContent(Object data, String error) {
			super();
			this.data = data;
			this.error = error;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

	}

	public static class PagerBean {
		private Integer start;
		private Integer limit;

		public Integer getStart() {
			return start;
		}

		public void setStart(Integer start) {
			this.start = start;
		}

		public Integer getLimit() {
			return limit;
		}

		public void setLimit(Integer limit) {
			this.limit = limit;
		}

		public static PagerBean valueOf(String body) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				PagerBean pagerBean = mapper.readValue(body, PagerBean.class);
				return pagerBean;
			} catch (IOException e) {
				throw new RuntimeException("json string to PagerBean failed.", e);
			}
		}
	}
}
