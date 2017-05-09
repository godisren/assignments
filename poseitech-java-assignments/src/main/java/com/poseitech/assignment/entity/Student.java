package com.poseitech.assignment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = "findStudentByName", query = "from Student s where s.name = :name") })
@Entity
@Table(name = "student")
public class Student implements Serializable {

	private static final long serialVersionUID = 4012534442879056855L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 60)
	private String name;

	private Date birthday;

	@Column(nullable = false)
	private Date registerDate;

	@Column(length = 100)
	private String remark;

	public Student() {
		super();
	}

	public Student(String name, Date birthday, Date registerDate, String remark) {
		super();
		this.name = name;
		this.birthday = birthday;
		this.registerDate = registerDate;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", birthday=" + birthday + ", registerDate=" + registerDate
				+ ", remark=" + remark + "]";
	}

}
