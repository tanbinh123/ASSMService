package com.aeon.mm.main.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="DEVICE_RESOURCE_INFO")
public class DeviceResourcesInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_device_info")
	@SequenceGenerator(
			name="seq_device_info",
			sequenceName="SEQ_DEVICE_RESOURCE_INFO_ID",
			allocationSize=1)
	@Column(name="ID")
	private int id;
	@Column(name="ACTIVATE_CODE")
	private String activateCode;
	@Column(name="MODEL_CODE")
	private String deviceModelCode;
	@Column(name="MANUFACTURE")
	private String deviceManufacturer;
	@Column(name="CURRENT_SDK")
	private String deviceCurrentSdk;
	@Column(name="OS_VERSION")
	private String deviceOsVersion;
	@Column(name="DEVICE_RESOLUTION")
	private String deviceResolution;
	@Column(name="INSTRUCTION_SET")
	private String systemInstructionSet;
	@Column(name="CPU_ARCHITECTURE")
	private String systemCpuArchitecture;
	@Column(name="DEVICE_RAM")
	private String memoryRAM;
	@Column(name="AVAILABLE_STORAGE")
	private String memoryAvailableStorage;
	@Column(name="BACK_CAM_RESOLUTION")
	private String cameraResolution;
	@Column(name="FRONT_CAM_RESOLUTION")
	private String cameraSecondaryResolution;
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActivateCode() {
		return activateCode;
	}

	public void setActivateCode(String activateCode) {
		this.activateCode = activateCode;
	}

	public String getDeviceModelCode() {
        return deviceModelCode;
    }

    public void setDeviceModelCode(String deviceModelCode) {
        this.deviceModelCode = deviceModelCode;
    }

    public String getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public void setDeviceManufacturer(String deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

    public String getDeviceCurrentSdk() {
        return deviceCurrentSdk;
    }

    public void setDeviceCurrentSdk(String deviceCurrentSdk) {
        this.deviceCurrentSdk = deviceCurrentSdk;
    }

    public String getDeviceOsVersion() {
        return deviceOsVersion;
    }

    public void setDeviceOsVersion(String deviceOsVersion) {
        this.deviceOsVersion = deviceOsVersion;
    }

    public String getDeviceResolution() {
        return deviceResolution;
    }

    public void setDeviceResolution(String deviceResolution) {
        this.deviceResolution = deviceResolution;
    }

    public String getSystemInstructionSet() {
        return systemInstructionSet;
    }

    public void setSystemInstructionSet(String systemInstructionSet) {
        this.systemInstructionSet = systemInstructionSet;
    }

    public String getSystemCpuArchitecture() {
        return systemCpuArchitecture;
    }

    public void setSystemCpuArchitecture(String systemCpuArchitecture) {
        this.systemCpuArchitecture = systemCpuArchitecture;
    }

    public String getMemoryRAM() {
        return memoryRAM;
    }

    public void setMemoryRAM(String memoryRAM) {
        this.memoryRAM = memoryRAM;
    }

    public String getMemoryAvailableStorage() {
        return memoryAvailableStorage;
    }

    public void setMemoryAvailableStorage(String memoryAvailableStorage) {
        this.memoryAvailableStorage = memoryAvailableStorage;
    }

    public String getCameraResolution() {
        return cameraResolution;
    }

    public void setCameraResolution(String cameraResolution) {
        this.cameraResolution = cameraResolution;
    }

    public String getCameraSecondaryResolution() {
        return cameraSecondaryResolution;
    }

    public void setCameraSecondaryResolution(String cameraSecondaryResolution) {
        this.cameraSecondaryResolution = cameraSecondaryResolution;
    }
}
