package com.aeon.mm.main.app.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.aeon.mm.main.app.common.ASSMCommonConstant;
import com.aeon.mm.main.app.common.ASSMCommonMessage;


@Service
public class ExternalApkDownloadService {

	@Async("asyncExecutor")
	public InputStreamResource downloadApk(HttpServletResponse response) throws IOException {
		
		final String APK_FILE_PATH = "/mnt/apk/universal/app-universal-debug.apk";
		
		File file = new File(APK_FILE_PATH);
		if (!file.exists()) {
			throw new FileNotFoundException(ASSMCommonMessage.FILE_NOT_FOUND_ERROR);
		}
			
		response.setContentType("application/apk");
		response.setStatus(200); //status OK.
		response.setHeader(ASSMCommonConstant.HTTP_CACHE_CONTROL,
				ASSMCommonConstant.HTTP_NO_CACHE + ASSMCommonConstant.COMMA + ASSMCommonConstant.HTTP_NO_STORE
				+ ASSMCommonConstant.COMMA + ASSMCommonConstant.HTTP_REVALIDATE + ASSMCommonConstant.COMMA);
		response.setHeader(ASSMCommonConstant.HTTP_PRAGMA, ASSMCommonConstant.HTTP_NO_CACHE);
		response.setHeader(ASSMCommonConstant.HTTP_EXPIRE, ASSMCommonConstant.STR_ZERO);
		response.setHeader("Content-Disposition", "attachment; filename=\"app-universal-debug.apk\"");
		response.setContentLength((int) file.length());
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		
		return resource;
	}
}
