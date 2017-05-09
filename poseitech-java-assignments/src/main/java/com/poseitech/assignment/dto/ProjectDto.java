package com.poseitech.assignment.dto;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

import com.poseitech.assignment.entity.Project;

public class ProjectDto implements Serializable {
	//
	private static final long serialVersionUID = -8001753971454616296L;

	private Integer id;
	private String name;
	private Date createDate;
	private String remark;

	public ProjectDto() {
		super();
	}

	public ProjectDto(String name, Date createDate, String remark) {
		super();
		this.name = name;
		this.createDate = createDate;
		this.remark = remark;
	}

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "ProjectDto [id=" + id + ", name=" + name + ", createDate=" + createDate + ", remark=" + remark + "]";
	}

	public static ProjectDto valueOf(Project p) {
		ProjectDto proDto = new ProjectDto();
		try {
			PropertyUtils.copyProperties(proDto, p);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException("ProjectDto to Project failed.", e);
		}
		return proDto;
	}

	public Project toProject() {
		Project pro = new Project();
		try {
			PropertyUtils.copyProperties(pro, this);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException("Project to ProjectDto failed.", e);
		}
		return pro;
	}

}
