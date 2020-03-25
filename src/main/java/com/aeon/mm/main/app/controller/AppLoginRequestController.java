package com.aeon.mm.main.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aeon.mm.main.app.bean.LoginInfoReqBean;
import com.aeon.mm.main.app.bean.LoginInfoResDataBean;
import com.aeon.mm.main.app.common.UrlRequestUtility;
import com.aeon.mm.main.app.dao.LoginInfoRepository;
import com.google.gson.Gson;


@RestController
@Component
public class AppLoginRequestController {

	@Autowired
	LoginInfoRepository appLoginRepository;

	static Logger logger = Logger.getLogger(AppLoginRequestController.class); 
	
	@RequestMapping("/AppLoginRequest")
	public String AppLoginInfoList(@RequestParam(value = "loginInfo", defaultValue = "") String loginInfo)
			throws MalformedURLException, IOException {
		
		logger.info("-------- req : /AppLoginRequest");
		logger.info("------- JSON : " + loginInfo);
		
		LoginInfoReqBean loginInfoForm = new LoginInfoReqBean();
		loginInfoForm.setLoginID("test");
		loginInfoForm.setLoginPassword("12345678");
		String login = new Gson().toJson(loginInfoForm);

		String result = UrlRequestUtility.createRequestBody("POST", new URL("http://localhost:81/AppLogin"),
				"application/json", UrlRequestUtility.createPostParams(login), null).toString();

		System.out.println("Request body" + result);
		
		LoginInfoResDataBean dataBean = new Gson().fromJson(result, LoginInfoResDataBean.class);
		
		System.out.println(dataBean.getMessage());
		System.out.println(dataBean.getDataBean().getId());
		
		logger.info("------ result : " + result);
		
		return result;
	}
	

	@RequestMapping("/ActivateRequest")
	public String activateRequest(@RequestParam(value = "loginInfo", defaultValue = "") String loginInfo)
			throws MalformedURLException, IOException {
		
		logger.info("------ Req : /ActivateRequest");
		logger.info("------ JSON : " + loginInfo);
		
		String code = new Gson().toJson("12345678");

		String result = UrlRequestUtility.createRequestBody("POST", new URL("http://localhost:81/AppActivate"),
				"application/json", UrlRequestUtility.createPostParams(code), null).toString();

		System.out.println("Request body :" + result);
		
		Boolean dataBean = new Gson().fromJson(result, Boolean.class);
		
		System.out.println(dataBean);
		
		logger.info("-------------- result : " + result);
		
		return result;
	}

}
