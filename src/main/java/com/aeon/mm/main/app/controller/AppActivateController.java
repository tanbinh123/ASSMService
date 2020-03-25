package com.aeon.mm.main.app.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aeon.mm.main.app.bean.DeviceResourcesInformation;
import com.aeon.mm.main.app.common.ASSMCommonConstant;
import com.aeon.mm.main.app.common.ASSMCommonMessage;
import com.aeon.mm.main.app.common.ASSMPasswordEncoder;
import com.aeon.mm.main.app.common.CommonURL;
import com.aeon.mm.main.app.dao.ActivateInfoRepository;
import com.aeon.mm.main.app.dao.DeviceResourceInfoRepository;
import com.aeon.mm.main.app.dto.ActivateInfoReqDto;
import com.aeon.mm.main.app.dto.ActivateInfoResDto;
import com.google.gson.Gson;

@RestController
@Component
public class AppActivateController {

	@Autowired
	ActivateInfoRepository activateRepository;
	
	@Autowired
	DeviceResourceInfoRepository deviceResourceInfoRepository;
	
	static Logger logger = Logger.getLogger(AppActivateController.class); 

	@RequestMapping(CommonURL.APP_ACTIVATE_URL)
	public String AppLoginInfoList(@RequestBody(required = false) String activateInfo) throws Exception {
		
		logger.info("------- Req : /assm/appActivate");
		logger.info("------- JSON : " + activateInfo);

		if (activateInfo == null) {
			logger.info("----- REQUEST DATA NOT FOUND Exception");
			throw new Exception("REQUEST DATA NOT FOUND");
		}
		String result = null;
		System.out.println("Request Data :" + activateInfo);

		if (activateInfo == null || ASSMCommonConstant.BLANK.equals(activateInfo)) {
			logger.info("------ result : " + result);
			result = ASSMCommonMessage.ACCESS_DENIED;
		} else {
			
			//String password = new Gson().fromJson(activateInfo, String.class);
			ActivateInfoReqDto reqDto = new ActivateInfoReqDto();
			
			reqDto = new Gson().fromJson(activateInfo, ActivateInfoReqDto.class);
			reqDto.setPassword(ASSMPasswordEncoder.encode(reqDto.getPassword()));
			
			//System.out.println("parameter value : " + reqDto.getPassword());

			// Check valid or not
			ActivateInfoResDto resData = activateRepository.findByPassword(reqDto);

			//result = new Gson().toJson(resData != null ? true : false);
			//System.out.println("Result :" + result);
			
			if(resData != null) {
				result = new Gson().toJson(true);
				DeviceResourcesInformation deviceResourcesInformation = new DeviceResourcesInformation();
				deviceResourcesInformation = reqDto.getDeviceResourcesInfo();
				deviceResourcesInformation.setActivateCode(reqDto.getPassword());
				deviceResourceInfoRepository.save(deviceResourcesInformation);
				logger.info("---------- response result : " + result);
				return result;
			} else {
				result = new Gson().toJson(false);
				logger.info("---------- response result : " + result);
				return result;
			}
		}
		return result;
	}

}






















