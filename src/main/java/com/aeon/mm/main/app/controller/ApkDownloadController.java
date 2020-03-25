package com.aeon.mm.main.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aeon.mm.main.app.common.ASSMCommonConstant;
import com.aeon.mm.main.app.common.ASSMCommonMessage;
import com.aeon.mm.main.app.common.CommonURL;
import com.aeon.mm.main.app.dao.ApkMaxVersionRepository;
import com.aeon.mm.main.app.dao.ExternalApkDownloadService;
import com.aeon.mm.main.app.dao.SFTPService;

@RestController
@Component
public class ApkDownloadController {

	@Autowired
	ApkMaxVersionRepository apkMaxVersionRepository;
	
	@Autowired
	ExternalApkDownloadService externalApkDownloadService;
	
	static Logger logger = Logger.getLogger(ApkDownloadController.class); 

	@RequestMapping(value = CommonURL.APK_DOWNLOAD_URL, method = RequestMethod.POST, produces = "application/apk")
	public ResponseEntity<InputStreamResource> download(@RequestBody(required = false) String apkFilePath)
			throws IOException {
		
		logger.info("------------ application/apk");
		logger.info("------------ JSON : "+ apkFilePath);

		if (apkFilePath == null || ASSMCommonConstant.BLANK.equals(apkFilePath)) {
				apkFilePath = ASSMCommonConstant.UNIVERSAL_DOWNLOAD_PATH;
			}

			File file = new File(apkFilePath);
			if (!file.exists()) {
				logger.info("------------ No. download file exception : " + apkFilePath);
				throw new FileNotFoundException(ASSMCommonMessage.FILE_NOT_FOUND_ERROR);
			}
			
			//increase download count.
			apkMaxVersionRepository.updateDownloadCount(apkFilePath);

			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			FileSystemResource fileSystemResource = new FileSystemResource(file);
			String fileName = FilenameUtils.getName(file.getAbsolutePath());
			fileName = new String(fileName.getBytes(ASSMCommonConstant.UTF_8), ASSMCommonConstant.ISO_8859_1);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.add(ASSMCommonConstant.HTTP_CACHE_CONTROL,
					ASSMCommonConstant.HTTP_NO_CACHE + ASSMCommonConstant.COMMA + ASSMCommonConstant.HTTP_NO_STORE
							+ ASSMCommonConstant.COMMA + ASSMCommonConstant.HTTP_REVALIDATE + ASSMCommonConstant.COMMA);
			headers.add(ASSMCommonConstant.HTTP_PRAGMA, ASSMCommonConstant.HTTP_NO_CACHE);
			headers.add(ASSMCommonConstant.HTTP_EXPIRE, ASSMCommonConstant.STR_ZERO);
			headers.setContentLength(fileSystemResource.contentLength());
			headers.setContentDispositionFormData(ASSMCommonConstant.HTTP_ATTACHMENT, fileName);
			logger.info("------- response download file.");
			return new ResponseEntity<InputStreamResource>(resource, headers, HttpStatus.OK);
			
	}
	
	//external download api.
	@RequestMapping(value=CommonURL.APK_EXTERNAL_DOWNLOAD_URL, method=RequestMethod.GET)
	public InputStreamResource externalDownload(HttpServletResponse response) throws IOException {
		
		logger.info("------------ /assm/extDownload");
		
		File file = new File(ASSMCommonConstant.EXTERNAL_DOWNLOAD_PATH);
		if (!file.exists()) {
			logger.info("------------ No. download file exception under /mnt/apk/universal/");
			throw new FileNotFoundException(ASSMCommonMessage.FILE_NOT_FOUND_ERROR);
		}
		
		response.setContentType(ASSMCommonConstant.EXTERNAL_DOWNLOAD_CONTENT_TYPE);
		response.setStatus(200); //status OK.
		response.setHeader(ASSMCommonConstant.HTTP_CACHE_CONTROL,
				ASSMCommonConstant.HTTP_NO_CACHE + ASSMCommonConstant.COMMA + ASSMCommonConstant.HTTP_NO_STORE
				+ ASSMCommonConstant.COMMA + ASSMCommonConstant.HTTP_REVALIDATE + ASSMCommonConstant.COMMA);
		response.setHeader(ASSMCommonConstant.HTTP_PRAGMA, ASSMCommonConstant.HTTP_NO_CACHE);
		response.setHeader(ASSMCommonConstant.HTTP_EXPIRE, ASSMCommonConstant.STR_ZERO);
		response.setHeader("Content-Disposition", ASSMCommonConstant.EXTERNAL_DOWNLOAD_SAVE_NAME);
		response.setContentLength((int) file.length());
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		logger.info("----- external file downloaded.");
		return resource;
	}
}
