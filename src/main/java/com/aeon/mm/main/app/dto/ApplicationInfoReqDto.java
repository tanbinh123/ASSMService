/*
 * Insert into APPLICATION_INFO table.
 * */
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
@Table(name="APPLICATION_INFO")
public class ApplicationInfoReqDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_app_info")
	@SequenceGenerator(
			name="seq_app_info",
			sequenceName="seq_app_info_id",
			allocationSize=1)
	@Column(name="id")
	private int id;
	@Column(name="login_id")
	private int loginId;
	@Column(name="nrc")
	private String nrc;
	@Column(name="name")
	private String name;
	//private String firstApplyDate;
	@Column(name="number_follow_up")
	private int followUpNum;
	@Column(name="isvalid")
	private int isValid;
	@Column(name="pl_flag")
	private int plFlag;
	@Column(name="update_time")
	private Timestamp updateTime;
	@Column(name="application_no")
	private String applicationNo;
	//private String updateTime;
	
	public int getId() {
		return id;
	}
	public int getFollowUpNum() {
		return followUpNum;
	}
	public void setFollowUpNum(int followUpNum) {
		this.followUpNum = followUpNum;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNrc() {
		return nrc;
	}
	public void setNrc(String nrc) {
		this.nrc = nrc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFollouUpNum() {
		return followUpNum;
	}
	public void setFollouUpNum(int follouUpNum) {
		this.followUpNum = follouUpNum;
	}
	public int getIsValid() {
		return isValid;
	}
	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}
	public int getLoginId() {
		return loginId;
	}
	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	public int getPlFlag() {
		return plFlag;
	}
	public void setPlFlag(int plFlag) {
		this.plFlag = plFlag;
	}
	
	/*@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("ApplicationInfo[Id=%d, LoginId=%d, Nrc='%s', Name='%s', ApplyDate='%s', NumFU=%d, Valid=%d, Update='%s']", 
				id,loginId, nrc,name,firstApplyDate,follouUpNum,isValid,updateTime);
	}*/
}
