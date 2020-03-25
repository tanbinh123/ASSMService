package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class ApkVersionResBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5518071611648355147L;

	private boolean isNewVersion;

	private int versionId;

	private String version;

	private String fileName;
	
	private String filePath;

	private String downloadLink;

	public boolean getIsNewVersion() {
		return isNewVersion;
	}

	public void setIsNewVersion(boolean isNewVersion) {
		this.isNewVersion = isNewVersion;
	}

	public int getVersionId() {
		return versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
