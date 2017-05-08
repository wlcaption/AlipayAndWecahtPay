package com.holagames.xcds.net;

import java.io.Serializable;

public class RespModel implements Serializable {
	
	/**
	 * ���ݸ�ʽ
	 * {"data" : {"update" : 0, "kf" : 0}, "errinfo" : "", "errno" : 200}
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String data;
	private String errinfo;
	private int errno;
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getErrinfo() {
		return errinfo;
	}
	
	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
	
	public int getErrno() {
		return errno;
	}
	
	public void setErrno(int errno) {
		this.errno = errno;
	}
	
}
