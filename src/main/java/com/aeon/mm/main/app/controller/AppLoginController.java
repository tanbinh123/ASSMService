package com.aeon.mm.main.app.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aeon.mm.main.app.bean.LoginInfoReqBean;
import com.aeon.mm.main.app.bean.LoginInfoResBean;
import com.aeon.mm.main.app.bean.LoginInfoResDataBean;
import com.aeon.mm.main.app.common.ASSMCommonConstant;
import com.aeon.mm.main.app.common.ASSMCommonMessage;
import com.aeon.mm.main.app.common.ASSMCommonUtil;
import com.aeon.mm.main.app.common.ASSMPasswordEncoder;
import com.aeon.mm.main.app.common.CommonURL;
import com.aeon.mm.main.app.dao.LoginInfoRepository;
import com.aeon.mm.main.app.dto.LoginInfoReqDto;
import com.aeon.mm.main.app.dto.LoginInfoResDto;
import com.google.gson.Gson;

@RestController
@Component
public class AppLoginController {

	@Autowired
	LoginInfoRepository appLoginRepository;
	
	static Logger logger = Logger.getLogger(AppLoginController.class); 

	@RequestMapping(CommonURL.APP_LOGIN_URL)
	public String AppLoginInfoList(@RequestBody(required = false) String loginInfo) throws Exception {

		logger.info("--------- req : /assm/appLogin");
		logger.info("--------- JSON : " + loginInfo);
		
		if (loginInfo == null) {
			logger.info("-------- Exception : REQUEST DATA NOT FOUND");
			throw new Exception("REQUEST DATA NOT FOUND");
		}
		String result = null;
		System.out.println("Request Data :" + loginInfo);
		LoginInfoReqBean loginInfoForm = new LoginInfoReqBean();

		if (loginInfo == null || ASSMCommonConstant.BLANK.equals(loginInfo)) {
			result = ASSMCommonMessage.ACCESS_DENIED;
			logger.info("------------ result : " + result);
		} else {
			loginInfoForm = new Gson().fromJson(loginInfo, LoginInfoReqBean.class);

			LoginInfoReqDto reqDto = new LoginInfoReqDto();
			reqDto.setLoginID(loginInfoForm.getLoginID());
			reqDto.setLoginPassword(ASSMPasswordEncoder.encode(loginInfoForm.getLoginPassword()));
			System.out.println("parameter value : " + reqDto.getLoginID() + " and " + reqDto.getLoginPassword());

			LoginInfoResDataBean dataBean = new LoginInfoResDataBean();

			// Check valid or not
			LoginInfoResDto loginInfoResDto = appLoginRepository.findByLoginIdAndPassword(reqDto);

			if (loginInfoResDto == null) {
				dataBean.setMessage(ASSMCommonMessage.RECORD_NOT_FOUND_ERROR);
				result = new Gson().toJson(dataBean);
				return result;

			} else if (loginInfoResDto.getAgencyValid() == 0) {
				dataBean.setMessage(ASSMCommonMessage.AGENCY_NOT_VALID_ERROR);
				result = new Gson().toJson(dataBean);
				return result;

			} else if (loginInfoResDto.getUserValid() == 0) {
				dataBean.setMessage(ASSMCommonMessage.USER_NOT_VALID_ERROR);
				result = new Gson().toJson(dataBean);
				return result;

			} else if (!ASSMCommonUtil.isWithinPeriod(loginInfoResDto.getStartDate(), loginInfoResDto.getEndDate())) {
				dataBean.setMessage(ASSMCommonMessage.ACCOUNT_EXPIRED);
				result = new Gson().toJson(dataBean);
				return result;
			} else {
				
				logger.info("-------- Repo value :" + loginInfoResDto);

				LoginInfoResBean resBean = new LoginInfoResBean();
				resBean.setId(loginInfoResDto.getId());
				resBean.setLoginId(loginInfoResDto.getLoginId());
				resBean.setName(loginInfoResDto.getName());
				resBean.setAgencyId(loginInfoResDto.getAgencyId());
				resBean.setAgencyName(loginInfoResDto.getAgencyName());
				resBean.setAgencyOutletId(loginInfoResDto.getAgencyOutletId());
				resBean.setOutletId(loginInfoResDto.getOutletId());
				resBean.setOutletName(loginInfoResDto.getOutletName());
				resBean.setLocation(loginInfoResDto.getLocation());
				resBean.setMobileTeam(loginInfoResDto.getMobileTeam());
				resBean.setNonMobileTeam(loginInfoResDto.getNonMobileTeam());
				resBean.setRoleIdList(loginInfoResDto.getRoleIdList());
				if (loginInfoResDto.getGroupId() != null) {
					resBean.setGroupId(Integer.parseInt(loginInfoResDto.getGroupId()));
				}
				dataBean.setDataBean(resBean);
				dataBean.setMessage(ASSMCommonMessage.OK);
				result = new Gson().toJson(dataBean);

				result = new Gson().toJson(dataBean);
				System.out.println("Result :" + result);
				logger.info("-------- Repo Result :" + result);

			}
		}

		return result;
	}

}
