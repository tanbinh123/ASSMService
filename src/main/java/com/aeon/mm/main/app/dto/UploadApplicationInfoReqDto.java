package com.aeon.mm.main.app.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class UploadApplicationInfoReqDto implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
     * Folder information.
     * */
    private String applyType; //1st or follow-up
    private String memFilter; //member or non-member
    private int loanType; //Mobile or Non-Mobile
    private String location; //ygn or mdy
    private String teamName;

    /*
    * FILE_INFO insert.
    * */
    private String fileName; //insert zipped file name in WebServices.
    private String filePath; //insert uploaded file path in Webservices.
    private int fileUploadingStatus; //insert upload status in WebServices.
    private int updatedBy;

    /*
    * APPLICATION_INFO insert.
    * */
    private int loginId;
    private String applyNrc;
    private String applyName;
    private Timestamp firstApplyDate; //insert in webservices.
    private int followUpNum; //DocFollowUp //0 for first apply.
    private int isValid; //1
    private Timestamp updatedTime; // insert in webservices.

    /*
    * TIME_MANAGEMENT
    * */
    private Timestamp finishTime;
    private Timestamp actualSendingTime; 
    
    private int uploadId;
    private int applyId;
    private String agencyId;
    private String outletId;
    
	public Timestamp getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}
	public Timestamp getActualSendingTime() {
		return actualSendingTime;
	}
	public void setActualSendingTime(Timestamp actualSendingTime) {
		this.actualSendingTime = actualSendingTime;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getMemFilter() {
		return memFilter;
	}
	public void setMemFilter(String memFilter) {
		this.memFilter = memFilter;
	}
	public int getLoanType() {
		return loanType;
	}
	public void setLoanType(int loanType) {
		this.loanType = loanType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getFileUploadingStatus() {
		return fileUploadingStatus;
	}
	public void setFileUploadingStatus(int fileUploadingStatus) {
		this.fileUploadingStatus = fileUploadingStatus;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public int getLoginId() {
		return loginId;
	}
	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	public String getApplyNrc() {
		return applyNrc;
	}
	public void setApplyNrc(String applyNrc) {
		this.applyNrc = applyNrc;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public Timestamp getFirstApplyDate() {
		return firstApplyDate;
	}
	public void setFirstApplyDate(Timestamp firstApplyDate) {
		this.firstApplyDate = firstApplyDate;
	}
	public int getFollowUpNum() {
		return followUpNum;
	}
	public void setFollowUpNum(int followUpNum) {
		this.followUpNum = followUpNum;
	}
	public int getIsValid() {
		return isValid;
	}
	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}
	public Timestamp getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}
	public int getUploadId() {
		return uploadId;
	}
	public void setUploadId(int uploadId) {
		this.uploadId = uploadId;
	}
	public int getApplyId() {
		return applyId;
	}
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	public String getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
}
