package com.poseitech.assignment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project implements Serializable {

	private static final long serialVersionUID = 1862690403944384704L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 60)
	private String name;
	@Column(nullable = false)
	private Date createDate;
	@Column(length = 100)
	private String remark;

	public Project() {
		super();
	}

	public Project(String name, Date createDate, String remark) {
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
		return "Project [id=" + id + ", name=" + name + ", createDate=" + createDate + ", remark=" + remark + "]";
	}

}
