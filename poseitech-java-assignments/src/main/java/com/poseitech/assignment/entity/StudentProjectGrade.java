package com.poseitech.assignment.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "strudent_project_grade")
public class StudentProjectGrade implements Serializable {

	@EmbeddedId
	private Key id;
	private String gradelevel;

	public StudentProjectGrade() {
		super();
	}

	public StudentProjectGrade(Key id) {
		super();
		this.id = id;
	}
	
	public StudentProjectGrade(Key id, String gradelevel) {
		super();
		this.id = id;
		this.gradelevel = gradelevel;
	}

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getGradelevel() {
		return gradelevel;
	}

	public void setGradelevel(String gradelevel) {
		this.gradelevel = gradelevel;
	}

	@Override
	public String toString() {
		return "StudentProjectGrade [id=" + id + ", gradelevel=" + gradelevel + "]";
	}

	@Embeddable
	public static class Key implements Serializable {
		private Integer studentId;
		private Integer projectId;

		public Key() {
			super();
		}

		public Key(Integer studentId, Integer projectId) {
			super();
			this.studentId = studentId;
			this.projectId = projectId;
		}

		public Integer getStudentId() {
			return studentId;
		}

		public void setStudentId(Integer studentId) {
			this.studentId = studentId;
		}

		public Integer getProjectId() {
			return projectId;
		}

		public void setProjectId(Integer projectId) {
			this.projectId = projectId;
		}

		@Override
		public String toString() {
			return "Key [studentId=" + studentId + ", projectId=" + projectId + "]";
		}

	}
}
