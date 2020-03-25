package com.aeon.mm.main.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.aeon.mm.main.app.bean.HistoryInfoResBean;
import com.aeon.mm.main.app.bean.HistorySuccessfulReqInfo;
import com.aeon.mm.main.app.bean.ResponseStatus;
import com.aeon.mm.main.app.common.ASSMCommonConstant;
import com.aeon.mm.main.app.common.ASSMCommonMessage;
import com.aeon.mm.main.app.common.CommonURL;
import com.aeon.mm.main.app.dao.CheckHeadOfficeRepository;
import com.aeon.mm.main.app.dao.HistoryInfoRepository;
import com.aeon.mm.main.app.dao.HistoryUpdateRepository;
import com.aeon.mm.main.app.dto.FileInfoResDto;
import com.aeon.mm.main.app.dto.HistoryInfoResDto;
import com.aeon.mm.main.app.dto.OutletInfoResDto;
import com.google.gson.Gson;

@RestController
@Component
public class HistoryInfoController {

	@Autowired
	HistoryInfoRepository historyInfoRepository;

	@Autowired
	HistoryUpdateRepository historyUpdateRepository;
	
	@Autowired
	CheckHeadOfficeRepository headOfficeRepository;
	
	static Logger logger = Logger.getLogger(HistoryInfoController.class); 

	@RequestMapping(CommonURL.HISTORY_URL)
	public String HistoryList(@RequestBody(required = false) String historyInfo)
			throws MalformedURLException, IOException {

		logger.info("--------- Req : /assm/historyList");
		logger.info("-------- JSON : " + historyInfo);
		
		String result = null;
		if (historyInfo == null || ASSMCommonConstant.BLANK.equals(historyInfo)) {
			result = ASSMCommonMessage.ACCESS_DENIED;
			logger.info("-------- result : " + result);

		} else {
			HistorySuccessfulReqInfo reqInfo = new Gson().fromJson(historyInfo, HistorySuccessfulReqInfo.class);
			//int agentId = Integer.parseInt(new Gson().fromJson(historyInfo, String.class));
			
			OutletInfoResDto outletInfo = headOfficeRepository.findByOutletId(Integer.parseInt(reqInfo.getOutletId()));
			
			List<HistoryInfoResDto> historyinfoList = new ArrayList<HistoryInfoResDto>();
			
			if(outletInfo != null && outletInfo.getOutletName().contains(ASSMCommonConstant.OUT_HEAD_OFFICE)) {
				historyinfoList = historyInfoRepository.findByAgencyIdForHeadOffice(Integer.parseInt(reqInfo.getAgencyId()));
				
			} else {
				historyinfoList = historyInfoRepository.findByAgencyIdForOutlet(Integer.parseInt(reqInfo.getOutletId()));
			}
			
			if (historyinfoList.size() != 0) {
	
				List<HistoryInfoResBean> historyResList = new ArrayList<HistoryInfoResBean>();
	
				if (historyinfoList != null) {
					for (HistoryInfoResDto resDto : historyinfoList) {
						HistoryInfoResBean resBean = new HistoryInfoResBean();
						resBean.setFileId(resDto.getFileId());
						resBean.setAgencyId(resDto.getAgencyId());
						resBean.setName(resDto.getName());
						resBean.setNrc(resDto.getNrc());
						resBean.setFinishTime(resDto.getFinishTime());
						resBean.setType(resDto.getType());
						resBean.setIsValid(resDto.getIsValid());
						resBean.setJudgementStatus(resDto.getJudgementStatus());
						resBean.setAgreementNo(resDto.getAgreementNo());
						resBean.setFinanceTerm(resDto.getFinanceTerm());
						resBean.setFinanceAmount(resDto.getFinanceAmount());
	
						historyResList.add(resBean);
					}
					
					result = new Gson().toJson(historyResList);
					logger.info("----- historyResList-resutl : " + result);
					if(historyResList!=null) {
						logger.info("----- historyResList-size : " + historyResList.size());
					}
				}
			}
		}
		return result;
	}

	@RequestMapping(value = CommonURL.UPD_HISTORY, headers = (CommonURL.CONTENT_TYPE_MULT_PATH), method = RequestMethod.POST)
	public ResponseStatus updateHistoryList(@RequestParam(value = CommonURL.PARAM_HISTORY_INFO) String historyInfo)
			throws MalformedURLException, IOException {
		
		logger.info("--------- Req : historyInfo");
		logger.info("--------- JSON : " + historyInfo);

		ResponseStatus responseStatus = new ResponseStatus();

		int fileId = Integer.parseInt(historyInfo);
		
		logger.info("------- fileId : " + fileId);

		FileInfoResDto fileInfoReqDto = historyUpdateRepository.findById(fileId);
		fileInfoReqDto.setIsValid(0);
		historyUpdateRepository.save(fileInfoReqDto);

		responseStatus.setStatus(historyInfo);
		return responseStatus;
	}

}