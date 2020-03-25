/*
 * Insert into TIME_MANAGEMENT table.
 */
package com.aeon.mm.main.app.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TIME_MANAGEMENT")
public class TimeManagementInfoReqDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_time_mang")
	@SequenceGenerator(
			name="seq_time_mang",
			sequenceName="seq_time_mang_id",
			allocationSize=1)
	@Column(name="ID")
	int id;
	@Column(name="APPLY")
	Timestamp applyTime;
	@Column(name="FINISH")
	Timestamp finishTime;
	@Column(name="ACTUAL_SENDING")
	Timestamp actualSendingTime;
	@Column(name="FILE_ID")
	int fileId;
	@Column(name="RECEIVED_TIME")
	Timestamp receivedTime;
	@Column(name="UPDATE_TIME")
	Timestamp updateTime;
	
	public Timestamp getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(Timestamp receivedTime) {
		this.receivedTime = receivedTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	public Timestamp getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}

	public Timestamp getActualSendingTime() {
		return actualSendingTime;
	}

	public void setActualSendingTime(Timestamp actualSendingTime) {
		this.actualSendingTime = actualSendingTime;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
}
