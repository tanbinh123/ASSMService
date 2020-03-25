package com.aeon.mm.main.app.dto;

import java.io.Serializable;

import com.aeon.mm.main.app.bean.DeviceResourcesInformation;

public class ActivateInfoReqDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2894881843870384725L;
	
	private String password;

	private DeviceResourcesInformation deviceResourcesInfo;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DeviceResourcesInformation getDeviceResourcesInfo() {
		return deviceResourcesInfo;
	}

	public void setDeviceResourcesInfo(DeviceResourcesInformation deviceResourcesInfo) {
		this.deviceResourcesInfo = deviceResourcesInfo;
	}
	
}
