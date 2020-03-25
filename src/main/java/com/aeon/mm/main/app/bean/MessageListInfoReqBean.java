package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class MessageListInfoReqBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5518071611648355147L;

	
	private int id;
	
	private int groupId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

}
