package com.aeon.mm.main.app.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageDetailResBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2994346950681710187L;

	private int messageId;

	private String messageContent;

	private String messageType;

	private Timestamp sendTime;

	private String sender;

	private int opSendFlag;

	private int readFlag;

	private Timestamp readTime;

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public int getOpSendFlag() {
		return opSendFlag;
	}

	public void setOpSendFlag(int opSendFlag) {
		this.opSendFlag = opSendFlag;
	}

	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}

	public Timestamp getReadTime() {
		return readTime;
	}

	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}

}
