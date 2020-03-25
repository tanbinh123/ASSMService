package com.aeon.mm.main.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aeon.mm.main.app.bean.SummaryReportInfoReqBean;
import com.aeon.mm.main.app.bean.SummaryReportInfoResBean;
import com.aeon.mm.main.app.bean.SummaryReportInfoBean;
import com.aeon.mm.main.app.common.ASSMCommonConstant;
import com.aeon.mm.main.app.common.ASSMCommonMessage;
import com.aeon.mm.main.app.common.CommonURL;
import com.aeon.mm.main.app.dao.SummaryFIleReportRepository;
import com.aeon.mm.main.app.dto.SummaryFileReportResDto;
import com.google.gson.Gson;

@RestController
@Component
public class SummaryFileReportController {

	@Autowired
	SummaryFIleReportRepository summaryReportRepository;

	static Logger logger = Logger.getLogger(SummaryFileReportController.class);

	@RequestMapping(CommonURL.SUMMARY_REPORT_URL)
	public String SummaryReport(@RequestBody(required = false) String loginId)
			throws MalformedURLException, IOException {

		logger.info("--------- Req : /assm/summaryReport");
		logger.info("-------- JSON : " + loginId);

		String result = null;
		if (loginId == null || ASSMCommonConstant.BLANK.equals(loginId)) {
			result = ASSMCommonMessage.ACCESS_DENIED;
			logger.info("-------- result : " + result);

		} else {

			SummaryReportInfoReqBean reqBean = new Gson().fromJson(loginId, SummaryReportInfoReqBean.class);

			List<SummaryFileReportResDto> resDtoList = summaryReportRepository.findByLoginId(reqBean.getLoginId());
			if (resDtoList != null && resDtoList.size() != 0) {

				Map<String, SummaryReportInfoBean> summaryReportInfoBeanMap = new HashMap<String, SummaryReportInfoBean>();

				for (SummaryFileReportResDto resDto : resDtoList) {
					SummaryReportInfoBean resBean = new SummaryReportInfoBean();

					resBean.setUploadTime(resDto.getUpdateTime());

					String[] statusList = resDto.getStatusList().split(ASSMCommonConstant.COMMA);
					int index = 0;
					for (String status : statusList) {
						// logger.info("-------- status : " + status);
						switch (status) {

						case "ongoing":
							resBean.setOnGoingCount(Integer.parseInt(statusList[index - 1]));
							break;
						case "approve":
							resBean.setApproveCount(Integer.parseInt(statusList[index - 1]));
							break;
						case "reject":
							resBean.setRejectCount(Integer.parseInt(statusList[index - 1]));
							break;
						case "cancel":
							resBean.setCancelCount(Integer.parseInt(statusList[index - 1]));
							break;

						}
						index++;
					}

					resBean.setTotalCount(resDto.getTotalCount());

					summaryReportInfoBeanMap.put(resBean.getUploadTime(), resBean);
					// logger.info("-------- status list : " + resBean.getOnGoingCount() + "," +
					// resBean.getApproveCount()
					// + "," + resBean.getRejectCount() + "," + resBean.getCancelCount());

				}
				SummaryReportInfoResBean infoResBean = new SummaryReportInfoResBean();
				infoResBean.setMessage(ASSMCommonConstant.OK);
				infoResBean.setSummaryReportMap(summaryReportInfoBeanMap);
				result = new Gson().toJson(infoResBean);

				if (summaryReportInfoBeanMap != null) {
					logger.info("----- summary report result : " + result);
					// logger.info("----- summary report-size : " +
					// summaryReportInfoBeanList.size());
				}
			}
		}
		return result;
	}

}