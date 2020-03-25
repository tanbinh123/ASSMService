package com.aeon.mm.main.app.bean;

import java.io.Serializable;
import java.util.Map;

public class SummaryReportInfoResBean implements Serializable {

	private static final long serialVersionUID = 7428559395318189993L;

	private String message;

	private Map<String, SummaryReportInfoBean> summaryReportMap;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, SummaryReportInfoBean> getSummaryReportMap() {
		return summaryReportMap;
	}

	public void setSummaryReportMap(Map<String, SummaryReportInfoBean> summaryReportMap) {
		this.summaryReportMap = summaryReportMap;
	}
}
