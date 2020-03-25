package com.aeon.mm.main.app.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class HistoryInfoResDto implements Serializable {

	private static final long serialVersionUID = 7428559395318189993L;

	@Id
	@Column(name = "id")
	private int fileId;

	@Column(name = "agency_id")
	private int agencyId;

	@Column(name = "name")
	private String name;

	@Column(name = "nrc")
	private String nrc;

	@Column(name = "finish")
	private String finishTime;

	@Column(name = "type")
	private int type;

	@Column(name = "is_valid")
	private int isValid;

	@Column(name = "judgement_status")
	private int judgementStatus;

	@Column(name = "agreement_no")
	private String agreementNo;

	@Column(name = "finance_term")
	private String financeTerm;

	@Column(name = "finance_amount")
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
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
