package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class ApkVersionReqBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5518071611648355147L;

	private int versionId;
	
	private String versionName;
	
	private String instructionSet;
	
	public String getInstructionSet() {
		return instructionSet;
	}

	public void setInstructionSet(String instructionSet) {
		this.instructionSet = instructionSet;
	}

	public int getVersionId() {
		return versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}	
}
