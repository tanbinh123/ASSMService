package com.aeon.mm.main.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aeon.mm.main.app.bean.MessageDetailResBean;
import com.aeon.mm.main.app.bean.MessageListInfoReqBean;
import com.aeon.mm.main.app.bean.MessageListInfoResBean;
import com.aeon.mm.main.app.bean.MessagePrivacyInfoReqBean;
import com.aeon.mm.main.app.bean.MessagePrivacyInfoResBean;
import com.aeon.mm.main.app.common.ASSMCommonConstant;
import com.aeon.mm.main.app.common.ASSMCommonMessage;
import com.aeon.mm.main.app.common.ASSMPasswordEncoder;
import com.aeon.mm.main.app.common.CommonURL;
import com.aeon.mm.main.app.dao.GroupMessageListRepository;
import com.aeon.mm.main.app.dao.PrivacyCheckRepository;
import com.aeon.mm.main.app.dto.MessageGroupInfoReqDto;
import com.aeon.mm.main.app.dto.MessageGroupInfoResDto;
import com.aeon.mm.main.app.dto.PrivacyInfoReqDto;
import com.aeon.mm.main.app.dto.PrivacyInfoResDto;
import com.google.gson.Gson;

@RestController
@Component
public class MessagingController {

	@Autowired
	PrivacyCheckRepository privacyCheckRepository;

	@Autowired
	GroupMessageListRepository groupMessageRepository;

	static Logger logger = Logger.getLogger(MessagingController.class); 
	
	@RequestMapping(CommonURL.MESSAGE_LIST_URL)
	public String getMessageList(@RequestBody(required = false) String messageInfo) throws Exception {

		logger.info("----------- Req : /assm/messageList");
		logger.info("---------- JSON : " + messageInfo);
		
		if (messageInfo == null) {
			logger.info("----- REQUEST DATA NOT FOUND.");
			throw new Exception("REQUEST DATA NOT FOUND");
		}
		
		String result = null;
		logger.info("Request Data :" + messageInfo);
		
		if (messageInfo == null || ASSMCommonConstant.BLANK.equals(messageInfo)) {
			result = ASSMCommonMessage.ACCESS_DENIED;
			logger.info("----- result : " + result);

		} else {
			
			MessageListInfoResBean resBean = new MessageListInfoResBean();
			
			MessageListInfoReqBean messageInfoBean = new MessageListInfoReqBean();
			messageInfoBean = new Gson().fromJson(messageInfo, MessageListInfoReqBean.class);
			
			MessageGroupInfoReqDto mesgReqDto = new MessageGroupInfoReqDto();
			mesgReqDto.setAgencyUserId(messageInfoBean.getId());
			mesgReqDto.setGroupId(messageInfoBean.getGroupId());

			List<MessageGroupInfoResDto> messageList = groupMessageRepository.findById(mesgReqDto);

			if (messageList != null && messageList.size() > 0) {
				resBean.setMessageInfo(ASSMCommonMessage.OK);
				List<MessageDetailResBean> messageDetailInfo = new ArrayList<MessageDetailResBean>();

				for (MessageGroupInfoResDto resDto : messageList) {
					MessageDetailResBean detailBean = new MessageDetailResBean();
					detailBean.setMessageId(resDto.getMessageId());
					detailBean.setMessageContent(resDto.getMessageContent());
					detailBean.setMessageType(resDto.getMessageType());
					detailBean.setSendTime(resDto.getSendTime());
					detailBean.setSender(resDto.getSender());
					detailBean.setOpSendFlag(resDto.getOpSendFlag());
					detailBean.setReadFlag(resDto.getReadFlag());
					detailBean.setReadTime(resDto.getReadTime());
					messageDetailInfo.add(detailBean);
				}
				resBean.setMessageCount(messageDetailInfo.size());
				resBean.setMessageList(messageDetailInfo);

			} else {
				resBean.setMessageInfo(ASSMCommonMessage.RECORD_NOT_FOUND_ERROR);
				resBean.setMessageList(new ArrayList<MessageDetailResBean>());
			}
			result = new Gson().toJson(resBean);
			logger.info("------- res result : " + result);
		}
		
		logger.info("------- res result : " + result);
		return result;
	}

	@RequestMapping(CommonURL.MESSAGE_PASS_URL)
	public String messagePravicyCheck(@RequestBody(required = false) String passInfo) throws Exception {

		logger.info("--------- Req : /assm/privacyCheck");
		
		if (passInfo == null) {
			logger.info("-------- Exception : REQUEST DATA NOT FOUND");
			throw new Exception("REQUEST DATA NOT FOUND");
		}
		
		String result = null;
		logger.info("----- Request Data :" + passInfo);
		MessagePrivacyInfoReqBean messageInfoBean = new MessagePrivacyInfoReqBean();

		if (passInfo == null || ASSMCommonConstant.BLANK.equals(passInfo)) {
			result = ASSMCommonMessage.ACCESS_DENIED;
			logger.info("------ result : " + result);
		} else {
			messageInfoBean = new Gson().fromJson(passInfo, MessagePrivacyInfoReqBean.class);

			PrivacyInfoReqDto reqDto = new PrivacyInfoReqDto();
			reqDto.setLoginID(messageInfoBean.getLoginId());
			reqDto.setLoginPassword(ASSMPasswordEncoder.encode(messageInfoBean.getLoginPassword()));
			logger.info("parameter value : " + reqDto.getLoginID() + " and " + reqDto.getLoginPassword());

			// Check valid or not
			PrivacyInfoResDto resData = privacyCheckRepository.findByLoginIdAndPassword(reqDto);

			MessagePrivacyInfoResBean resBean = new MessagePrivacyInfoResBean();
			if (resData != null) {
				resBean.setStatus(ASSMCommonMessage.OK);

			} else {
				resBean.setStatus(ASSMCommonMessage.USER_NOT_VALID_ERROR);
			}

			result = new Gson().toJson(resBean);
			logger.info("----- res result : " + result);

		}
		
		logger.info("------ res result : " + result);
		
		return result;
	}

}
