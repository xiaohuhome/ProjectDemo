package com.xiaohu.demo.entity.user;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_bed_no")
public class BedNo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "bed_no_flag")
	private String flag;
	
	@Column(name = "bed_no_num")
	private String num;
	
	public synchronized String getId() {
		return id;
	}

	public synchronized void setId(String id) {
		this.id = id;
	}

	public synchronized String getFlag() {
		return flag;
	}

	public synchronized void setFlag(String flag) {
		this.flag = flag;
	}

	public synchronized String getNum() {
		return num;
	}

	public synchronized void setNum(String num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "BedNo [id=" + id + ", flag=" + flag + ", num=" + num + "]";
	}
	
	
}
