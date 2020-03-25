package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class LoginInfoResDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1946752761842732829L;

	private String message;

	private LoginInfoResBean dataBean = new LoginInfoResBean();

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LoginInfoResBean getDataBean() {
		return dataBean;
	}

	public void setDataBean(LoginInfoResBean dataBean) {
		this.dataBean = dataBean;
	}

}
