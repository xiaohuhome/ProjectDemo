package com.xiaohu.demo.entity.fullcalendar;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_install_date")
public class InstallDate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id")
	private String id;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "start_time")
	private String minTime;

	@Column(name = "end_time")
	private String maxTime;

	public synchronized String getId() {
		return id;
	}

	public synchronized void setId(String id) {
		this.id = id;
	}

	public synchronized String getUserId() {
		return userId;
	}

	public synchronized void setUserId(String userId) {
		this.userId = userId;
	}

	public synchronized String getMinTime() {
		return minTime;
	}

	public synchronized void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public synchronized String getMaxTime() {
		return maxTime;
	}

	public synchronized void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	@Override
	public String toString() {
		return "InstallDate [id=" + id + ", userId=" + userId + ", minTime=" + minTime + ", maxTime=" + maxTime + "]";
	}

	
}
