package com.aeon.mm.main.app.bean;

import java.io.Serializable;
import java.util.List;

import com.aeon.mm.main.app.dto.MessageGroupInfoResDto;

public class MessageListInfoResBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4003476704441723385L;

	private String messageInfo;

	private int messageCount;
	
	private List<MessageDetailResBean> messageList;

	public String getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}

	public List<MessageDetailResBean> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<MessageDetailResBean> messageList) {
		this.messageList = messageList;
	}

	public int getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

}
