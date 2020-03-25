package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class LoanInfoResDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5843480888200416958L;

	private String status;
	private String messageCode;
	private String message;
	private String error;
	private LoanCalculatorResBean dataBean = new LoanCalculatorResBean();

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LoanCalculatorResBean getDataBean() {
		return dataBean;
	}

	public void setDataBean(LoanCalculatorResBean dataBean) {
		this.dataBean = dataBean;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
