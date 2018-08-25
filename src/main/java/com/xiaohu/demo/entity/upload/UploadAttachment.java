package com.xiaohu.demo.entity.upload;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_upload_attachment")
public class UploadAttachment implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id")
	private String id;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "file_type")
	private String fileType;//文件类型：I：图片；V:视频；A:音频；P:pdf
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "add_date")
	private String addDate;
	
	@Column(name = "add_time")
	private String addTime;
	
	@Column(name = "disabled")
	private String disabled;
	
	@Column(name = "type")
	private String type;//业务数据类型：H:头像

	public synchronized String getId() {
		return id;
	}

	public synchronized void setId(String id) {
		this.id = id;
	}

	public synchronized String getUuid() {
		return uuid;
	}

	public synchronized void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public synchronized String getFileType() {
		return fileType;
	}

	public synchronized void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public synchronized String getUrl() {
		return url;
	}

	public synchronized void setUrl(String url) {
		this.url = url;
	}

	public synchronized String getFileName() {
		return fileName;
	}

	public synchronized void setFileName(String fileName) {
		this.fileName = fileName;
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

	public synchronized String getDisabled() {
		return disabled;
	}

	public synchronized void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public synchronized String getType() {
		return type;
	}

	public synchronized void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "UploadAttachment [id=" + id + ", uuid=" + uuid + ", fileType=" + fileType + ", url=" + url
				+ ", fileName=" + fileName + ", addDate=" + addDate + ", addTime=" + addTime + ", disabled=" + disabled
				+ ", type=" + type + "]";
	}
}
