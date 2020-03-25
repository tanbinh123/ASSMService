package com.aeon.mm.main.app.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class LoginInfoResDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1946752761842732829L;

	@Id
	@Column
	private int id;
	
	@Column
	private String loginId;

	@Column
	private String name;

	@Column 
	private Timestamp startDate;
	
	@Column 
	private Timestamp endDate;
	
	@Column 
	private int userValid;
	
	@Column 
	private int agencyValid;
	
	@Column
	private int agencyId;

	@Column
	private String agencyName;

	@Column
	private String location;
	
	@Column 
	private String mobileTeam;
	
	@Column 
	private String nonMobileTeam;

	@Column
	private int outletId;

	@Column
	private String agencyOutletId;

	@Column
	private String outletName;

	@Column
	private String roleIdList;
	
	@Column
	private String groupId;


	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getOutletId() {
		return outletId;
	}

	public void setOutletId(int outletId) {
		this.outletId = outletId;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getAgencyOutletId() {
		return agencyOutletId;
	}

	public void setAgencyOutletId(String agencyOutletId) {
		this.agencyOutletId = agencyOutletId;
	}

	public String getMobileTeam() {
		return mobileTeam;
	}

	public void setMobileTeam(String mobileTeam) {
		this.mobileTeam = mobileTeam;
	}

	public String getNonMobileTeam() {
		return nonMobileTeam;
	}

	public void setNonMobileTeam(String nonMobileTeam) {
		this.nonMobileTeam = nonMobileTeam;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
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

	public int getUserValid() {
		return userValid;
	}

	public void setUserValid(int userValid) {
		this.userValid = userValid;
	}

	public int getAgencyValid() {
		return agencyValid;
	}

	public void setAgencyValid(int agencyValid) {
		this.agencyValid = agencyValid;
	}

	public String getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(String roleIdList) {
		this.roleIdList = roleIdList;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
