package com.aeon.mm.main.app.dto;

import java.io.Serializable;

public class MessageGroupInfoReqDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 41930169973000557L;

	private int agencyUserId;

	private int groupId;

	public int getAgencyUserId() {
		return agencyUserId;
	}

	public void setAgencyUserId(int agencyUserId) {
		this.agencyUserId = agencyUserId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
}
