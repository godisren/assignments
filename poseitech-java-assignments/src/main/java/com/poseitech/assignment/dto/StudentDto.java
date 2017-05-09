package com.poseitech.assignment.dto;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poseitech.assignment.entity.Student;

public class StudentDto implements Serializable {
	final public static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

	private static final long serialVersionUID = 3209888691671533902L;
	private Integer id;
	private String name;

	private Date birthday;
	private String registerDate;
	private String remark;

	private List<ProjectDto> interestedProjects;

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JsonIgnore
	public List<ProjectDto> getInterestedProjects() {
		return interestedProjects;
	}

	public void setInterestedProjects(List<ProjectDto> pInterestedProjects) {
		interestedProjects = pInterestedProjects;
	}

	@Override
	public String toString() {
		return "StudentDto [id=" + id + ", name=" + name + ", birthday=" + birthday + ", registerDate=" + registerDate
				+ ", remark=" + remark + ", interestedProjects=" + interestedProjects + "]";
	}

	public Student toStudent() {
		Student stu = new Student();

		try {
			stu.setId(this.getId());
			stu.setBirthday(this.getBirthday());
			stu.setName(this.getName());
			stu.setRegisterDate(this.getRegisterDate() != null ? df.parse(this.getRegisterDate()) : null);
			stu.setRemark(this.getRemark());
		} catch (ParseException e) {
			throw new RuntimeException("StudentDto convert to Student failed.", e);
		}

		return stu;
	}

	static public StudentDto valueOf(Student s) {
		if (s == null)
			return null;

		StudentDto stuDto = new StudentDto();

		stuDto.setId(s.getId());
		stuDto.setBirthday(s.getBirthday());
		stuDto.setName(s.getName());
		stuDto.setRegisterDate(s.getRegisterDate() != null ? df.format(s.getRegisterDate()) : StringUtils.EMPTY);
		stuDto.setRemark(s.getRemark());

		return stuDto;
	}

	public static StudentDto valueOf(String body) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			StudentDto stuDto = mapper.readValue(body, StudentDto.class);
			return stuDto;
		} catch (IOException e) {
			throw new RuntimeException("json string to StudentDto failed.", e);
		}
	}

}
