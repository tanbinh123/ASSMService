package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class HistoryInfoResBean implements Serializable {

	private static final long serialVersionUID = 7428559395318189993L;

	private int fileId;
	
	private int agencyId;
	
	private String name;
	
	private String nrc;
	
	private String finishTime;
	
	private int type;
	
	private int isValid;
	
	private int judgementStatus;

	private String agreementNo;
	
	private String financeTerm;
	
	private String financeAmount;
	
	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getJudgementStatus() {
		return judgementStatus;
	}

	public void setJudgementStatus(int judgementStatus) {
		this.judgementStatus = judgementStatus;
	}

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	public String getFinanceTerm() {
		return financeTerm;
	}

	public void setFinanceTerm(String financeTerm) {
		this.financeTerm = financeTerm;
	}

	public String getFinanceAmount() {
		return financeAmount;
	}

	public void setFinanceAmount(String financeAmount) {
		this.financeAmount = financeAmount;
	}

}
