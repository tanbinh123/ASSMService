package com.aeon.mm.main.app.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aeon.mm.main.app.common.ASSMCommonConstant;
import com.aeon.mm.main.app.dto.ApplicationInfoReqDto;

@Repository
public class CommonUtilsDao {
	
	@Autowired
	CheckNrcAppInfoRepository checkNrcAppInfoRepository;
	@Autowired
	InsertApplicationInfoRepository insertApplicationInfoRepository;
	@Autowired
	ApplicationInfoRepository applicationInfoRepository;

	//check nrc exist or not.
	public boolean checkNrc(String applyNrc) {
		if(checkNrcAppInfoRepository.countByNrcIgnoreCase(applyNrc) > ASSMCommonConstant.ZERO) {
			return true;
		}else {
			return false;
		}
	}
	
	//get applicationinfo by nrc.
	public ApplicationInfoReqDto findTopApplicationInfo(String applyNrc) {
		return checkNrcAppInfoRepository.findTopByNrcIgnoreCase(applyNrc);
	}
	
	//get application info record for resent purpose.
	public List<ApplicationInfoReqDto> findApplicationInfoForResend(String nrc,Timestamp updateTime){
		return applicationInfoRepository.findAllByNrcAndUpdateTime(nrc,updateTime);
	}
	
	//get application info record for resent purpose.
		public List<ApplicationInfoReqDto> findApplicationInfoForResend(String nrc){
			return applicationInfoRepository.findAllByNrc(nrc);
		}
	
	//get file upload status for resent purpose.
	public int getUploadedStatus(int applicationId, Timestamp updatedTimestamp) {
		return applicationInfoRepository.findFileIdToResend(applicationId, updatedTimestamp);
	}
}
