package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class SummaryReportInfoBean implements Serializable {

	private static final long serialVersionUID = 7428559395318189993L;

	private String uploadTime;
	
	private int totalCount;
	
	private int onGoingCount;
	
	private int approveCount;
	
	private int rejectCount;
	
	private int cancelCount;

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getOnGoingCount() {
		return onGoingCount;
	}

	public void setOnGoingCount(int onGoingCount) {
		this.onGoingCount = onGoingCount;
	}

	public int getApproveCount() {
		return approveCount;
	}

	public void setApproveCount(int approveCount) {
		this.approveCount = approveCount;
	}

	public int getRejectCount() {
		return rejectCount;
	}

	public void setRejectCount(int rejectCount) {
		this.rejectCount = rejectCount;
	}

	public int getCancelCount() {
		return cancelCount;
	}

	public void setCancelCount(int cancelCount) {
		this.cancelCount = cancelCount;
	}
	
}
