package com.poseitech.assignment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grade")
public class Grade implements Serializable {

	@Id
	private String level;
	@Column(length = 100)
	private String remark;

	public Grade() {
		super();
	}

	public Grade(String level, String remark) {
		super();
		this.level = level;
		this.remark = remark;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Grade [level=" + level + ", remark=" + remark + "]";
	}

}
