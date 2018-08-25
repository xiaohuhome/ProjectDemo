package com.xiaohu.demo.entity.user;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id")
	private String id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;

	@Column(name = "gender")
	private String gender;
	
	@Column(name = "age")
	private String age;

	@Column(name = "telephone")
	private String telephone;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "birth_date")
	private String birthDate;
	
	@Column(name = "create_date")
	private String createDate;
	
	@Column(name = "create_time")
	private String createTime;
	
	@Column(name = "disabled")
	private String disabled;
	
	@Column(name = "uuid")
	private String uuid;
	
	

	public synchronized String getUuid() {
		return uuid;
	}

	public synchronized void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public synchronized String getId() {
		return id;
	}

	public synchronized void setId(String id) {
		this.id = id;
	}

	public synchronized String getUsername() {
		return username;
	}

	public synchronized void setUsername(String username) {
		this.username = username;
	}

	public synchronized String getPassword() {
		return password;
	}

	public synchronized void setPassword(String password) {
		this.password = password;
	}

	public synchronized String getGender() {
		return gender;
	}

	public synchronized void setGender(String gender) {
		this.gender = gender;
	}

	public synchronized String getAge() {
		return age;
	}

	public synchronized void setAge(String age) {
		this.age = age;
	}

	public synchronized String getTelephone() {
		return telephone;
	}

	public synchronized void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public synchronized String getAddress() {
		return address;
	}

	public synchronized void setAddress(String address) {
		this.address = address;
	}

	public synchronized String getBirthDate() {
		return birthDate;
	}

	public synchronized void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public synchronized String getCreateDate() {
		return createDate;
	}

	public synchronized void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public synchronized String getCreateTime() {
		return createTime;
	}

	public synchronized void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public synchronized String getDisabled() {
		return disabled;
	}

	public synchronized void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", gender=" + gender + ", age="
				+ age + ", telephone=" + telephone + ", address=" + address + ", birthDate=" + birthDate
				+ ", createDate=" + createDate + ", createTime=" + createTime + ", disabled=" + disabled + ", uuid="
				+ uuid + "]";
	}
}
