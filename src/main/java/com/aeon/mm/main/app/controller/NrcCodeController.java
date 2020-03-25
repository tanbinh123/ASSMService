package com.aeon.mm.main.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aeon.mm.main.app.common.ASSMCommonConstant;
import com.aeon.mm.main.app.common.ASSMCommonMessage;
import com.aeon.mm.main.app.common.CommonURL;
import com.aeon.mm.main.app.dao.NrcCodeInfoRepository;
import com.aeon.mm.main.app.dto.NrcCodeInfoResDto;
import com.google.gson.Gson;

@RestController
@Component
public class NrcCodeController {

	@Autowired
	NrcCodeInfoRepository nrcCodeInfoRepository;

	static Logger logger = Logger.getLogger(NrcCodeController.class); 
	
	@RequestMapping(CommonURL.NRC_CODE_URL)
	public String AppLoginInfoList(@RequestBody(required = false) String activeCode) throws Exception {

		logger.info("----- Req : /assm/nrcCodeRequest");
		logger.info("-------- JSON : " + activeCode);
		
		if (activeCode == null) {
			logger.info("------- Exception : REQUEST DATA NOT FOUND");
			throw new Exception("REQUEST DATA NOT FOUND");
		}
		String result = null;
		logger.info("Request Data :" + activeCode);

		if (activeCode == null || ASSMCommonConstant.BLANK.equals(activeCode)) {
			result = ASSMCommonMessage.ACCESS_DENIED;
			logger.info("------ result : " + result);

		} else {

			// Check valid or not
			List<NrcCodeInfoResDto> resData = nrcCodeInfoRepository.findByALL();

			Map<Integer, String> dataBean = new HashMap<>();

			for (NrcCodeInfoResDto resDto : resData) {
				if (resDto != null && resDto.getTownshipCodeList().trim().length() > 5) {
					String nrcList = resDto.getTownshipCodeList().substring(1,
							resDto.getTownshipCodeList().length() - 1);
					dataBean.put(resDto.getStateId(), new Gson().toJson(nrcList, String.class));
				}
			}

			result = new Gson().toJson(dataBean);
			logger.info("Res Result :" + result);

		}
		
		return result;
	}

}
