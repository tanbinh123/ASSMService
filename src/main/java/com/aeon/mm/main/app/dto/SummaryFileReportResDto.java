package com.aeon.mm.main.app.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class SummaryFileReportResDto implements Serializable {

	private static final long serialVersionUID = 7428559395318189993L;

	@Id
	@Column(name = "update_time")
	private String updateTime;

	@Column(name = "status_list")
	private String statusList;
	
	@Column(name = "total_count")
	private int totalCount;

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatusList() {
		return statusList;
	}

	public void setStatusList(String statusList) {
		this.statusList = statusList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
}
