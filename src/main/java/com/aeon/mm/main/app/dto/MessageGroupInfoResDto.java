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
public class MessageGroupInfoResDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4337335633981751213L;

	@Id
	@Column
	private int messageId;

	@Column
	private String messageContent;

	@Column
	private String messageType;

	@Column
	private Timestamp sendTime;

	@Column
	private String sender;

	@Column
	private int opSendFlag;

	@Column
	private int readFlag;

	@Column
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

	public Timestamp getReadTime() {
		return readTime;
	}

	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}

	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}

}
