package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class LoginInfoResBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1946752761842732829L;

	private int id;
	
	private String loginId;

	private String name;

	private int agencyId;

	private String agencyName;

	private String location;

	private int outletId;

	private String outletName;
	
	private String agencyOutletId;
	
	private String mobileTeam;
	
	private String nonMobileTeam;
	
	private String roleIdList;
	
	private int groupId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
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

	public String getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(String roleIdList) {
		this.roleIdList = roleIdList;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

}
