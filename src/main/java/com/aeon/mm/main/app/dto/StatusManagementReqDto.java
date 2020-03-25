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
@Table(name="STATUS_MANAGEMENT")
public class StatusManagementReqDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_status_mang")
	@SequenceGenerator(
			name="seq_status_mang",
			sequenceName="seq_status_mang_id",
			allocationSize=1)
	@Column(name="id")
	private int id;
	
	@Column(name="status_from")
	private int statusFrom;
	
	@Column(name="status_to")
	private int statusTo;
	
	@Column(name="status_time")
	private Timestamp statusTime;
	
	@Column(name="file_id")
	private int fileId;
	
	@Column(name="update_time")
	private Timestamp updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatusFrom() {
		return statusFrom;
	}

	public void setStatusFrom(int statusFrom) {
		this.statusFrom = statusFrom;
	}

	public int getStatusTo() {
		return statusTo;
	}

	public void setStatusTo(int statusTo) {
		this.statusTo = statusTo;
	}

	public Timestamp getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
