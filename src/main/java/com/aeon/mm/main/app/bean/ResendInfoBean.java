package com.aeon.mm.main.app.bean;

import java.sql.Timestamp;

//image_path, loanType, memberFilter, operation
public class ResendInfoBean {

    private int id;
    private int loanType;
    private int memberFilter;
    private int operation;
    private String imagePath;
    private String applyNrc;
    private int fileUploadStatus;
    private int fileId;
    private Timestamp finishedTime;
    private String updatedTime;
    private int typeFlag;
    
    public int getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(int typeFlag) {
		this.typeFlag = typeFlag;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Timestamp getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Timestamp finishedTime) {
		this.finishedTime = finishedTime;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoanType() {
        return loanType;
    }

    public void setLoanType(int loanType) {
        this.loanType = loanType;
    }

    public int getMemberFilter() {
        return memberFilter;
    }

    public void setMemberFilter(int memberFilter) {
        this.memberFilter = memberFilter;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getApplyNrc() {
        return applyNrc;
    }

    public void setApplyNrc(String applyNrc) {
        this.applyNrc = applyNrc;
    }

	public int getFileUploadStatus() {
		return fileUploadStatus;
	}

	public void setFileUploadStatus(int fileUploadStatus) {
		this.fileUploadStatus = fileUploadStatus;
	}
    
}
