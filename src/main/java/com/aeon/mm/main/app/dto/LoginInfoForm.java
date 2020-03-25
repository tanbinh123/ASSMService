package com.aeon.mm.main.app.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LOGIN_INFO")
public class LoginInfoForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="AGENCY_ID")
	private int agencyId;
	
	@Column(name="AGENCY_OUTLET_ID")
	private int agencyOutletId;
	
	@Column(name="LOGIN_ID")
	private String loginId;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="START_DATE")
	private Timestamp startDate;
	
	@Column(name="END_DATE")
	private Timestamp endDate;
	
	@Column(name="PHONE_NO")
	private String phoneNo;
	
	@Column(name="EMAIL")
    private String email;
    
	@Column(name="ADDRESS")
    private String address;
	
	@Column(name="REMARK")
    private String remark;
	
	@Column(name="ISVALID")
    private int isvalid;
	
	@Column(name="CREATED_BY")
    private String createdBy;
	
	@Column(name="UPDATED_BY")
    private String updatedBy;
    
	@Column(name="DISABLED_BY")
    private String disabledBy;
	
	@Column(name="CREATED_TIME")
    private Timestamp createdTime;
	
	@Column(name="UPDATED_TIME")
    private Timestamp updatedTime;
	
	@Column(name="DISABLED_TIME")
    private Timestamp disabledTime;

	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAgencyOutletId() {
		return agencyOutletId;
	}

	public void setAgencyOutletId(int agencyOutletId) {
		this.agencyOutletId = agencyOutletId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(int isvalid) {
		this.isvalid = isvalid;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getDisabledBy() {
		return disabledBy;
	}

	public void setDisabledBy(String disabledBy) {
		this.disabledBy = disabledBy;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Timestamp getDisabledTime() {
		return disabledTime;
	}

	public void setDisabledTime(Timestamp disabledTime) {
		this.disabledTime = disabledTime;
	}
}
