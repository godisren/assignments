package com.poseitech.assignment.pojo;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StudentStatistics {
	private String name;
	private Integer projectId;
	private String gradelevel;
	private BigInteger cnt;

	public StudentStatistics() {
		super();
	}

	public StudentStatistics(String name, Integer projectId, String gradelevel, BigInteger cnt) {
		super();
		this.name = name;
		this.projectId = projectId;
		this.gradelevel = gradelevel;
		this.cnt = cnt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getGradelevel() {
		return gradelevel;
	}

	public void setGradelevel(String gradelevel) {
		this.gradelevel = gradelevel;
	}

	public BigInteger getCnt() {
		return cnt;
	}

	public void setCnt(BigInteger cnt) {
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		return "StudentStatistics [name=" + name + ", projectId=" + projectId + ", gradelevel=" + gradelevel + ", cnt="
				+ cnt + "]";
	}

}
