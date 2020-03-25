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
import com.aeon.mm.main.app.exception.UploadDocFollowUpInfoException;

@Repository
public class UploadDocFollowUpInfoDao {

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
	
	static Logger logger = Logger.getLogger(UploadDocFollowUpInfoDao.class); 

	public boolean checkApplicationNrc(UploadApplicationInfoReqBean uploadApplicationInfoReqBean){
		//return true;
		if(insertApplicationInfoRepository.countByNrcIgnoreCase(uploadApplicationInfoReqBean.getApplyNrc()) > ASSMCommonConstant.ZERO) {
			//return it is duplicate.
			return true;} else {return false;}
	}
	
	//find object by nrc.
	public ApplicationInfoReqDto findApplicationInfo(String applyNrc) throws UploadDocFollowUpInfoException{
		return insertApplicationInfoRepository.findByNrcIgnoreCase(applyNrc);
	}
	
	/*
	 * (1) update APPLICATION_INFO
	 */
	@Transactional(propagation=Propagation.MANDATORY)
	public void updateApplicationInfo(ApplicationInfoReqDto applicationInfoReqDto) throws UploadDocFollowUpInfoException{
		insertApplicationInfoRepository.save(applicationInfoReqDto);
	}
	
	/*
	 * (2) insert to FILE_INFO
	 */
	@Transactional(propagation=Propagation.MANDATORY)
	public int saveFileInfo(FileInfoReqDto fileInfoReqDto) throws UploadDocFollowUpInfoException{
		fileInfoRepository.save(fileInfoReqDto);
		return fileInfoReqDto.getId();
	}
	
	/*
	 * (3) insert to APPLICATION_FILE
	 */
	@Transactional(propagation=Propagation.MANDATORY)
	public void saveApplicationFileInfo(ApplicationFileReqDto applicationFileReqDto) throws UploadDocFollowUpInfoException{
		applicationFileRepository.save(applicationFileReqDto);
	}
	
	/*
	 * (4) insert to TIME_MANAGEMENT
	 */
	@Transactional(propagation=Propagation.MANDATORY)
	public List<Timestamp> saveTimeManagementInfo(TimeManagementInfoReqDto timeManagementInfoReqDto, int uploadStatus) throws UploadDocFollowUpInfoException{
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
	public void saveStatusManagementInfo(StatusManagementReqDto statusManagementReqDto) throws UploadDocFollowUpInfoException{
		statusManagementRepository.save(statusManagementReqDto);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=UploadDocFollowUpInfoException.class)
	public int persistDocFollowUpInfo(UploadApplicationInfoReqBean uploadApplicationInfoReqBean) throws UploadDocFollowUpInfoException{
		
		ApplicationInfoReqDto applicationInfoReqDto = findApplicationInfo(uploadApplicationInfoReqBean.getApplyNrc());
		int followUpNum = applicationInfoReqDto.getFollouUpNum() + 1;
		int applicationInfoId = applicationInfoReqDto.getId();
		applicationInfoReqDto.setFollouUpNum(followUpNum);
		applicationInfoReqDto.setUpdateTime(ASSMCommonUtil.getCurrentTimeStamp());
		applicationInfoReqDto.setUpdateTime(uploadApplicationInfoReqBean.getActualSendingTime());
		updateApplicationInfo(applicationInfoReqDto);
		logger.info("--fu-- update APPLICATION_INFO --- id : " + applicationInfoId); 
		
		FileInfoReqDto fileInfoReqDto = new FileInfoReqDto();
		fileInfoReqDto.setFileName(uploadApplicationInfoReqBean.getFileName());
		fileInfoReqDto.setFilePath(uploadApplicationInfoReqBean.getUploadDir());
		fileInfoReqDto.setUploadStatus(uploadApplicationInfoReqBean.getFileUploadingStatus());
		fileInfoReqDto.setType(ASSMCommonConstant.FILE_INFO_VALID[1]); //follow_up : 1
		fileInfoReqDto.setLoginId(uploadApplicationInfoReqBean.getLoginId());
		fileInfoReqDto.setIsValid(ASSMCommonConstant.FILE_INFO_VALID[1]);  //valid : 1
		int fileId = saveFileInfo(fileInfoReqDto);
		logger.info("--fu-- insert into FILE_INFO -- id : " + fileId);
		
		ApplicationFileReqDto applicationFileReqDto = new ApplicationFileReqDto();
		applicationFileReqDto.setApplicationId(applicationInfoId);
		applicationFileReqDto.setFileId(fileId);
		applicationFileReqDto.setUpdatedTime(uploadApplicationInfoReqBean.getUpdatedTime());
		saveApplicationFileInfo(applicationFileReqDto);
		logger.info("--fu-- insert into APPLICATION_FILE");
		
		TimeManagementInfoReqDto timeMgntInfoReqDto = new TimeManagementInfoReqDto();
		timeMgntInfoReqDto.setApplyTime(uploadApplicationInfoReqBean.getFirstApplyDate());
		timeMgntInfoReqDto.setFinishTime(uploadApplicationInfoReqBean.getFinishTime());
		timeMgntInfoReqDto.setActualSendingTime(uploadApplicationInfoReqBean.getFinishTime());
		timeMgntInfoReqDto.setFileId(fileId);
		timeMgntInfoReqDto.setReceivedTime(ASSMCommonUtil.getCurrentTimeStamp());
		timeMgntInfoReqDto.setUpdateTime(ASSMCommonUtil.getCurrentTimeStamp());
		List<Timestamp> timeMgntList = new ArrayList<>();
		timeMgntList = saveTimeManagementInfo(timeMgntInfoReqDto, uploadApplicationInfoReqBean.getFileUploadingStatus());
		logger.info("--fu-- insert into TIME_MANAGEMENT_INFO");
		
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
		
		logger.info("--fu-- insert into STATUS_MANAGEMENT");
		logger.info("---- res file_id : " +fileId);
		
		return fileId;
	}
}

