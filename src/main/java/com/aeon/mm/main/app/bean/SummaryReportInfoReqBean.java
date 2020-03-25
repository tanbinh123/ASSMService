package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class SummaryReportInfoReqBean implements Serializable {

	private static final long serialVersionUID = 7428559395318189993L;

	private int loginId;

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

}
