package com.aeon.mm.main.app.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class NrcCodeInfoResDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1946752761842732829L;

	@Id
	@Column
	private int stateId;
	
	@Column
	private String townshipCodeList;
	
	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getTownshipCodeList() {
		return townshipCodeList;
	}

	public void setTownshipCodeList(String townshipCodeList) {
		this.townshipCodeList = townshipCodeList;
	}

}
