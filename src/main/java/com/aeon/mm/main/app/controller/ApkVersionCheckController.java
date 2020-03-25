package com.aeon.mm.main.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.log4j.Logger;

import com.aeon.mm.main.app.bean.ApkVersionReqBean;
import com.aeon.mm.main.app.bean.ApkVersionResBean;
import com.aeon.mm.main.app.common.ASSMCommonConstant;
import com.aeon.mm.main.app.common.ASSMCommonMessage;
import com.aeon.mm.main.app.common.ASSMCommonUtil;
import com.aeon.mm.main.app.common.CommonURL;
import com.aeon.mm.main.app.dao.ApkMaxVersionRepository;
import com.aeon.mm.main.app.dto.ApkVersionResDto;
import com.google.gson.Gson;

@RestController
@Component
public class ApkVersionCheckController {

	@Autowired
	ApkMaxVersionRepository apkMaxVersionRepository;
	
	static Logger logger = Logger.getLogger(ApkVersionCheckController.class); 

	@RequestMapping(CommonURL.APK_VERSION_CHECK_URL)
	public String apkVersionCheck(@RequestBody(required = false) String apkVersion) throws Exception {

		logger.info("----- Req : /assm/apkVersionCheck");
		logger.info("----- JSON : " + apkVersion);
		
		if (apkVersion == null) {
			throw new Exception("REQUEST DATA NOT FOUND");
		}
		
		String result = null;
		
		if (apkVersion == null || ASSMCommonConstant.BLANK.equals(apkVersion)) {
			result = ASSMCommonMessage.ACCESS_DENIED;
			logger.info("--------- result : " + result);
		} else {
			
			//get reqVersion Info.
			ApkVersionReqBean reqBean = new Gson().fromJson(apkVersion, ApkVersionReqBean.class);
			
			//get latestVersion Info.
			ApkVersionResDto resDataMaxId = apkMaxVersionRepository.findById();
			
			ApkVersionResBean resBean = new ApkVersionResBean();
			
			//compare reqVersion and latestVersion for update decision.
			if(ASSMCommonUtil.isUpgradeNeeded(reqBean.getVersionName().trim(), resDataMaxId.getVersion().trim())) {
				
				//get apk match request CPU instruction-set.
				ApkVersionResDto resData = apkMaxVersionRepository.findByInstructionSet(reqBean.getInstructionSet());
				
				if (resData != null) {
					resBean.setIsNewVersion(true);
					resBean.setVersionId(resData.getId());
					resBean.setVersion(resData.getVersion());
					resBean.setFileName(resData.getFileName());
					resBean.setFilePath(resData.getFilePath());
					resBean.setDownloadLink(CommonURL.APK_UPDATE_DOWNLOAD_URL);
				} else {
					//get universal-apk info.
					ApkVersionResDto resDataUniversalApk = apkMaxVersionRepository.findUniversalApk();
					if(resDataUniversalApk != null) {
						resBean.setIsNewVersion(true);
						resBean.setVersionId(resDataUniversalApk.getId());
						resBean.setVersion(resDataUniversalApk.getVersion());
						resBean.setFileName(resDataUniversalApk.getFileName());
						resBean.setFilePath(resDataUniversalApk.getFilePath());
						resBean.setDownloadLink(CommonURL.APK_UPDATE_DOWNLOAD_URL);
					 } else {
						resBean.setIsNewVersion(false);
					}
				}
			} else {
				resBean.setIsNewVersion(false);
			}
			result = new Gson().toJson(resBean);
			logger.info("--------- result : " + result);
		}
		return result;
	}
}

