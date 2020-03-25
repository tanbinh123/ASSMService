package com.aeon.mm.main.app.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aeon.mm.main.app.bean.UploadApplicationInfoReqBean;
import com.aeon.mm.main.app.common.ASSMCommonConstant;
import com.aeon.mm.main.app.common.ASSMCommonUtil;
import com.aeon.mm.main.app.dto.ApplicationFileReqDto;
import com.aeon.mm.main.app.dto.ApplicationInfoReqDto;
import com.aeon.mm.main.app.dto.FileInfoReqDto;
import com.aeon.mm.main.app.dto.StatusManagementReqDto;
import com.aeon.mm.main.app.dto.TimeManagementInfoReqDto;
import com.aeon.mm.main.app.exception.UploadFirstApplyInfoException;

@Repository
public class UploadFirstApplyInfoDao {
	
	@Autowired
	InsertApplicationInfoRepository insertApplicationInfoRepository;
	@Autowired
	FileInfoRepository fileInfoRepository;
	@Autowired
	ApplicationFileRepository applicationFileRepository;
	@Autowired
	TimeMgntRepository timeMgntRepository;
	@Autowired
	StatusManagementRepository statusManagementRepository;
	
	static Logger logger = Logger.getLogger(UploadFirstApplyInfoDao.class); 

	public boolean checkApplicationNrc(UploadApplicationInfoReqBean uploadApplicationInfoReqBean){
		//return true;
		if(insertApplicationInfoRepository.countByNrc(uploadApplicationInfoReqBean.getApplyNrc()) > ASSMCommonConstant.ZERO) {
			//return it is duplicate.
			return true;} else {return false;}
	}
	
	/*
	 * (1) insert to APPLICATION_INFO
	 */
	@Transactional(propagation=Propagation.MANDATORY)
	public int saveApplicationInfo(ApplicationInfoReqDto applicationInfoReqDto) throws UploadFirstApplyInfoException{
		insertApplicationInfoRepository.save(applicationInfoReqDto);
		return applicationInfoReqDto.getId();
	}
	
	/*
	 * (2) insert to FILE_INFO
	 */
	@Transactional(propagation=Propagation.MANDATORY)
	public int saveFileInfo(FileInfoReqDto fileInfoReqDto) throws UploadFirstApplyInfoException{
		fileInfoRepository.save(fileInfoReqDto);
		return fileInfoReqDto.getId();
	}
	
	/*
	 * (3) insert to APPLICATION_FILE
	 */
	@Transactional(propagation=Propagation.MANDATORY)
	public void saveApplicationFileInfo(ApplicationFileReqDto applicationFileReqDto) throws UploadFirstApplyInfoException{
		applicationFileRepository.save(applicationFileReqDto);
	}
	
	/*
	 * (4) insert to TIME_MANAGEMENT
	 */
	@Transactional(propagation=Propagation.MANDATORY)
	public List<Timestamp> saveTimeManagementInfo(TimeManagementInfoReqDto timeManagementInfoReqDto, int uploadStatus) throws UploadFirstApplyInfoException{
		List<Timestamp> timeMgntList = new ArrayList<>();
		timeMgntRepository.save(timeManagementInfoReqDto);
		if(uploadStatus==3) { //received.
			timeMgntList.add(timeManagementInfoReqDto.getApplyTime());
			timeMgntList.add(timeManagementInfoReqDto.getFinishTime());
			timeMgntList.add(timeManagementInfoReqDto.getActualSendingTime());
			timeMgntList.add(timeManagementInfoReqDto.getReceivedTime());
		} else { //sent. but not received.
			timeMgntList.add(timeManagementInfoReqDto.getApplyTime());
			timeMgntList.add(timeManagementInfoReqDto.getFinishTime());
		}
		return timeMgntList;
	}
	
	/*
	 * (5) insert to STATUS_MANAGEMENT
	 */
	@Transactional(propagation=Propagation.MANDATORY)
	public void saveStatusManagementInfo(StatusManagementReqDto statusManagementReqDto) {
		statusManagementRepository.save(statusManagementReqDto);
	}
	
	/*
	 * Upload First Apply Info.
	 */
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=UploadFirstApplyInfoException.class)
	public int persistUploadFirstApplyInfo(UploadApplicationInfoReqBean uploadApplicationInfoReqBean) throws UploadFirstApplyInfoException{
		
		ApplicationInfoReqDto applicationInfoReqDto = new ApplicationInfoReqDto();
		applicationInfoReqDto.setLoginId(uploadApplicationInfoReqBean.getLoginId());
		applicationInfoReqDto.setNrc(uploadApplicationInfoReqBean.getApplyNrc());
		applicationInfoReqDto.setName(uploadApplicationInfoReqBean.getApplyName());
		applicationInfoReqDto.setFollouUpNum(uploadApplicationInfoReqBean.getFollowUpNum());
		applicationInfoReqDto.setPlFlag(uploadApplicationInfoReqBean.getPlFlag());
		applicationInfoReqDto.setUpdateTime(ASSMCommonUtil.getCurrentTimeStamp());
		applicationInfoReqDto.setIsValid(uploadApplicationInfoReqBean.getIsValid());
		applicationInfoReqDto.setApplicationNo(uploadApplicationInfoReqBean.getApplicationNo());
		applicationInfoReqDto.setUpdateTime(uploadApplicationInfoReqBean.getActualSendingTime());
		int applicationInfoId = saveApplicationInfo(applicationInfoReqDto);
		logger.info("------- inserted into APPLICATION_INFO -- id : " + applicationInfoId);
		
		FileInfoReqDto fileInfoReqDto = new FileInfoReqDto();
		fileInfoReqDto.setFileName(uploadApplicationInfoReqBean.getFileName());
		fileInfoReqDto.setFilePath(uploadApplicationInfoReqBean.getUploadDir());
		fileInfoReqDto.setUploadStatus(uploadApplicationInfoReqBean.getFileUploadingStatus());
		fileInfoReqDto.setType(ASSMCommonConstant.FILE_INFO_VALID[0]); //first : 0
		fileInfoReqDto.setLoginId(uploadApplicationInfoReqBean.getLoginId());
		fileInfoReqDto.setIsValid(ASSMCommonConstant.FILE_INFO_VALID[1]);  //valid : 1
		fileInfoRepository.save(fileInfoReqDto);
		int fileId = fileInfoReqDto.getId();
		logger.info("------ inserted into FILE_INFO -- id " + fileId);
		
		ApplicationFileReqDto applicationFileReqDto = new ApplicationFileReqDto();
		applicationFileReqDto.setApplicationId(applicationInfoId);
		applicationFileReqDto.setFileId(fileId);
		applicationFileReqDto.setUpdatedTime(uploadApplicationInfoReqBean.getUpdatedTime());
		saveApplicationFileInfo(applicationFileReqDto);
		logger.info("------ inserted into APPLICATION_FILE");
		
		TimeManagementInfoReqDto timeMgntInfoReqDto = new TimeManagementInfoReqDto();
		timeMgntInfoReqDto.setApplyTime(uploadApplicationInfoReqBean.getFirstApplyDate());
		timeMgntInfoReqDto.setFinishTime(uploadApplicationInfoReqBean.getFinishTime());
		timeMgntInfoReqDto.setActualSendingTime(uploadApplicationInfoReqBean.getFinishTime());
		timeMgntInfoReqDto.setFileId(fileId);
		timeMgntInfoReqDto.setReceivedTime(ASSMCommonUtil.getCurrentTimeStamp());
		timeMgntInfoReqDto.setUpdateTime(ASSMCommonUtil.getCurrentTimeStamp());
		List<Timestamp> timeMgntList = new ArrayList<>();
		timeMgntList = saveTimeManagementInfo(timeMgntInfoReqDto, uploadApplicationInfoReqBean.getFileUploadingStatus());
		logger.info("-------- inserted into TIME_MANAGEMENT_INFO");
		
		int statusFrom = 0;
		int statusTo = 0;
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
		
		logger.info("------- inserted into STATUS_MANAGEMENT");
		logger.info("-------- return file_id : " + fileId);
		
		return fileId;
	}
}
