package com.aeon.mm.main.app.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aeon.mm.main.app.bean.ResendInfoBean;
import com.aeon.mm.main.app.common.ASSMCommonUtil;
import com.aeon.mm.main.app.dto.FileInfoReqDto;
import com.aeon.mm.main.app.dto.StatusManagementReqDto;
import com.aeon.mm.main.app.dto.TimeManagementInfoReqDto;
import com.aeon.mm.main.app.exception.UploadResendInfoException;

@Repository
public class UploadResendInfoDao {
	
	static Logger logger = Logger.getLogger(UploadResendInfoDao.class); 
	
	@Autowired
	FileInfoRepository fileInfoRepository;
	
	@Autowired
	TimeMgntRepository timeMgntRepository;
	
	@Autowired
	StatusManagementRepository statusManagementRepository;

	@Transactional(propagation=Propagation.MANDATORY)
	public FileInfoReqDto findFileInfo(int fileId) {
		return fileInfoRepository.findById(fileId);
	}
	
	@Transactional(propagation=Propagation.MANDATORY)
	public void updateFileInfo(FileInfoReqDto fileInfoReqDto) {
		fileInfoRepository.save(fileInfoReqDto);
	}
	
	@Transactional(propagation=Propagation.MANDATORY)
	public void updateTimeMgnt(TimeManagementInfoReqDto timeManagementInfoReqDto) {
		timeMgntRepository.save(timeManagementInfoReqDto);
	}
	
	@Transactional(propagation=Propagation.MANDATORY)
	public TimeManagementInfoReqDto findTimeMgnt(int fileId) {
		return timeMgntRepository.findByFileId(fileId);
	}
	
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=UploadResendInfoException.class)
	public int persistResendInfo(ResendInfoBean resendInfoBean) throws UploadResendInfoException {
		
		/*
		 * (1) Update status in FILE_INFO.
		 */
		int fileId = resendInfoBean.getFileId();
		FileInfoReqDto fileInfoReqDto = findFileInfo(fileId);
		fileInfoReqDto.setUploadStatus(3);
		updateFileInfo(fileInfoReqDto);
		logger.info("------- update status in FILE_INFO -- file_id : " + fileId);
		
		/*
		 * (2) Update actual_sending, received_time in TIME_MANAGEMENT.
		 */
		TimeManagementInfoReqDto timeMgntInfoReqDto = findTimeMgnt(fileId);
		timeMgntInfoReqDto.setActualSendingTime(resendInfoBean.getFinishedTime());
		timeMgntInfoReqDto.setReceivedTime(ASSMCommonUtil.getCurrentTimeStamp());
		timeMgntInfoReqDto.setUpdateTime(ASSMCommonUtil.getCurrentTimeStamp());
		updateTimeMgnt(timeMgntInfoReqDto);
		logger.info("------- update TIME_MANAGEMENT_INFO.");
		
		/*
		 * (3) Add next two records to STATUS_MANAGEMENT
		 */
		List<Timestamp> timeMgntList = new ArrayList<>();
		timeMgntList.add(timeMgntInfoReqDto.getActualSendingTime());
		timeMgntList.add(timeMgntInfoReqDto.getReceivedTime());
		int statusFrom = 1;
		int statusTo = 2;
		for (Timestamp statusTime : timeMgntList) {
			StatusManagementReqDto statusManagementReqDto = new StatusManagementReqDto();
			statusManagementReqDto.setStatusFrom(statusFrom);
			statusManagementReqDto.setStatusTo(statusTo);
			statusManagementReqDto.setStatusTime(statusTime);
			statusManagementReqDto.setFileId(fileId);
			statusManagementReqDto.setUpdateTime(ASSMCommonUtil.getCurrentTimeStamp());
			statusManagementRepository.save(statusManagementReqDto);
			statusFrom = statusTo;
			statusTo = statusTo + 1;
		}
		
		logger.info("--------- update TIME_MANAGEMENT.");
		logger.info("------- res file_id : " + fileId);
		
		return fileId;
	}
}