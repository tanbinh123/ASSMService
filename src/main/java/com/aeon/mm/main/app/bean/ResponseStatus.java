package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class ResponseStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String status;
	private String fileName;
	private String absolutePath;
	private int followUpNum;
	private String imgFolderName;
	private String[] folderPath;
	private int fileId;
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String[] getFolderPath() {
		return folderPath;
	}
	public void setFolderPath(String[] folderPath) {
		this.folderPath = folderPath;
	}
	public String getImgFolderName() {
		return imgFolderName;
	}
	public void setImgFolderName(String imgFolderName) {
		this.imgFolderName = imgFolderName;
	}
	public int getFollowUpNum() {
		return followUpNum;
	}
	public void setFollowUpNum(int followUpNum) {
		this.followUpNum = followUpNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	
	
}
