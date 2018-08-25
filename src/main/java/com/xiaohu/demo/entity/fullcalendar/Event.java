package com.xiaohu.demo.entity.fullcalendar;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_event")
public class Event implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id")
	private String id;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "start_date")
	private String start;

	@Column(name = "title")
	private String title;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "all_day")
	private String allDay;
	
	@Column(name = "add_date")
	private String addDate;
	
	@Column(name = "add_time")
	private String addTime;
	
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

	public synchronized String getStart() {
		return start;
	}

	public synchronized void setStart(String start) {
		this.start = start;
	}

	public synchronized String getTitle() {
		return title;
	}

	public synchronized void setTitle(String title) {
		this.title = title;
	}

	public synchronized String getColor() {
		return color;
	}

	public synchronized void setColor(String color) {
		this.color = color;
	}

	public synchronized String getAllDay() {
		return allDay;
	}

	public synchronized void setAllDay(String allDay) {
		this.allDay = allDay;
	}

	public synchronized String getAddDate() {
		return addDate;
	}

	public synchronized void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public synchronized String getAddTime() {
		return addTime;
	}

	public synchronized void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", userId=" + userId + ", start=" + start + ", title=" + title + ", color=" + color
				+ ", allDay=" + allDay + ", addDate=" + addDate + ", addTime=" + addTime + "]";
	}
	
}
