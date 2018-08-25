package com.xiaohu.demo.utils.result;

public class RequestResult {
	
	private String msg;
	
	private String status;
	
	private Object data;
	
	private boolean success;
	
	public RequestResult() {
		
	}

	public RequestResult(String msg) {
		super();
		this.msg = msg;
	}
	
	public RequestResult(String msg, String status, Object data, boolean success) {
		super();
		this.msg = msg;
		this.status = status;
		this.data = data;
		this.success = success;
	}



	public synchronized String getMsg() {
		return msg;
	}
	public synchronized void setMsg(String msg) {
		this.msg = msg;
	}
	public synchronized String getStatus() {
		return status;
	}
	public synchronized void setStatus(String status) {
		this.status = status;
	}
	public synchronized Object getData() {
		return data;
	}
	public synchronized void setData(Object data) {
		this.data = data;
	}
	public synchronized boolean isSuccess() {
		return success;
	}
	public synchronized void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}
