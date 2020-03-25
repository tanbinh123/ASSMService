package com.aeon.mm.main.app.controller;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aeon.mm.main.app.bean.ResendInfoBean;
import com.aeon.mm.main.app.bean.ResponseStatus;
import com.aeon.mm.main.app.bean.UploadApplicationInfoReqBean;
import com.aeon.mm.main.app.common.ASSMCommonConstant;
import com.aeon.mm.main.app.common.ASSMCommonMessage;
import com.aeon.mm.main.app.common.ASSMCommonSFTPInfo;
import com.aeon.mm.main.app.common.ASSMCommonUtil;
import com.aeon.mm.main.app.common.CommonURL;
import com.aeon.mm.main.app.dao.CommonUtilsDao;
import com.aeon.mm.main.app.dao.SFTPService;
import com.aeon.mm.main.app.dao.UploadDocFollowUpInfoDao;
import com.aeon.mm.main.app.dao.UploadFirstApplyInfoDao;
import com.aeon.mm.main.app.dao.UploadResendInfoDao;
import com.aeon.mm.main.app.dto.ApplicationInfoReqDto;
import com.google.gson.Gson;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

@RestController
public class PhotoUploadController {

	@Autowired
	private SFTPService sftpService;
	@Autowired
	UploadFirstApplyInfoDao uploadFirstApplyInfoDao;
	@Autowired
	UploadDocFollowUpInfoDao uploadDocFollowUpInfoDao;
	@Autowired
	UploadResendInfoDao uploadResendInfoDao;
	@Autowired
	CommonUtilsDao commonUtilsDao;

	static Logger logger = Logger.getLogger(PhotoUploadController.class);

	@RequestMapping(value = CommonURL.UPLOAD_CHECK_NRC)
	public ResponseStatus checkNrc(
			@RequestParam(value = CommonURL.PARAM_APPLY_NRC, defaultValue = ASSMCommonConstant.BLANK) String applyNrc) {
		logger.info("Request mapped - /checknrc");
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			logger.info("Request NRC : " + applyNrc);
			logger.info("Check NRC existed or not.");
			if (commonUtilsDao.checkNrc(applyNrc)) {
				ApplicationInfoReqDto applicationInfoReqDto = commonUtilsDao.findTopApplicationInfo(applyNrc);
				responseStatus.setFollowUpNum(applicationInfoReqDto.getFollouUpNum());
				responseStatus.setStatus(ASSMCommonMessage.NRC_EXISTED);
				logger.info("NRC does not exist.");
				return responseStatus; // Not Existed NRC.
			} else {
				responseStatus.setStatus(ASSMCommonMessage.OK);
				logger.info("NRC already existed.");
				return responseStatus; // Existed NRC.
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseStatus.setStatus(ASSMCommonMessage.ACCESS_DENIED);
			logger.info("Exception : " + e.getMessage());
			return responseStatus; // Existed NRC.
		}
	}

	@RequestMapping(value = CommonURL.UPLOAD_IMG_AND_INFO_DOCFU, method = RequestMethod.POST)
	public List<ResponseStatus> uploadImagAndInfoDocFu(
			@RequestParam(value = CommonURL.PARAM_UPDATE_INFO, defaultValue = ASSMCommonConstant.BLANK) String updateInfo,
			@RequestPart(name = CommonURL.PARAM_PATH_IMG) List<MultipartFile> imgList) {

		CompletableFuture<List<ResponseStatus>> responseStatusListAsync;
		List<ResponseStatus> responseStatusList = new ArrayList<ResponseStatus>();
		
		String[] commonDestPath = null;

		UploadApplicationInfoReqBean uploadApplicationInfoReqBean = new Gson().fromJson(updateInfo,
				UploadApplicationInfoReqBean.class);
		try {
			String applyType = uploadApplicationInfoReqBean.getApplyType().toLowerCase().trim();
			String memberFilter = uploadApplicationInfoReqBean.getMemFilter().toLowerCase().trim();
			String applyNrc = uploadApplicationInfoReqBean.getApplyNrc().trim();
			String location = uploadApplicationInfoReqBean.getLocation().toLowerCase().trim();
			String locationFolderName = uploadApplicationInfoReqBean.getLocation()
					.replaceAll(ASSMCommonConstant.SPACE, ASSMCommonConstant.UNDERSCORE).toUpperCase().trim();
			String teamName = null; // for personal loan user.
			if (uploadApplicationInfoReqBean.getTeamName() != null) {
				teamName = uploadApplicationInfoReqBean.getTeamName().toLowerCase().trim();
			}
			String folderName = uploadApplicationInfoReqBean.getFolderName().trim();
			String agencyName = uploadApplicationInfoReqBean.getAgencyName()
					.replaceAll(ASSMCommonConstant.SPACE, ASSMCommonConstant.UNDERSCORE).toUpperCase().trim();
			String outletName = uploadApplicationInfoReqBean.getOutletName()
					.replaceAll(ASSMCommonConstant.SPACE, ASSMCommonConstant.UNDERSCORE).toUpperCase().trim();
			String nrcName = uploadApplicationInfoReqBean.getNrcName().toUpperCase().trim();
			String dailyFolderName = folderName.substring(0, Math.min(folderName.length(), 8)); // for daily folderName.
			//folderName = folderName.substring(0, Math.min(folderName.length(), 12));
			String applyName = ASSMCommonConstant.BLANK;

			if (uploadApplicationInfoReqBean.getApplyName() == null) { // get apply name for doc_follow_up first.
				ApplicationInfoReqDto applicationInfoReqDto = commonUtilsDao.findTopApplicationInfo(applyNrc);
				applyName = applicationInfoReqDto.getName();
				applyName = applyName.replaceAll(ASSMCommonConstant.SPACE, ASSMCommonConstant.BLANK).toUpperCase();
			} else {
				applyName = uploadApplicationInfoReqBean.getApplyName()
						.replaceAll(ASSMCommonConstant.SPACE, ASSMCommonConstant.BLANK).toUpperCase();
			}

			String loanType = ASSMCommonConstant.BLANK;
			String imageFoldername = ASSMCommonConstant.BLANK;

			// define apply type.
			int numFollowUp = uploadApplicationInfoReqBean.getFollowUpNum() + 1;
			imageFoldername = folderName + ASSMCommonConstant.DASH + locationFolderName + ASSMCommonConstant.DASH
					+ applyName + ASSMCommonConstant.DASH + ASSMCommonConstant.BLANK + agencyName
					+ ASSMCommonConstant.DASH + outletName + ASSMCommonConstant.DASH + nrcName + "-FU" + numFollowUp;

			// define file save path.
			String[] destinationPath = { ASSMCommonSFTPInfo.BASE_PATH, ASSMCommonSFTPInfo.PHOTO_PATH, applyType,
					memberFilter, loanType, location, teamName, dailyFolderName, imageFoldername };

			String uploadedFileName = imageFoldername + ASSMCommonSFTPInfo.COMPRESS_FILE_TYPE;
			uploadApplicationInfoReqBean.setFileName(uploadedFileName);

			// define loan type.
			switch (uploadApplicationInfoReqBean.getLoanType()) {
			case 0:
				loanType = ASSMCommonConstant.LOAN_TYPE[0]; // mobile
				destinationPath[4] = loanType;
				logger.info("Go to uploadFile in sftpservice.mobile.");
				commonDestPath = destinationPath;
				responseStatusListAsync = sftpService.uploadFile(imgList, destinationPath);
				responseStatusList = responseStatusListAsync.get();
				uploadApplicationInfoReqBean.setUploadDir(convertArrayToStringPath(destinationPath));
				break;
			case 1:
				loanType = ASSMCommonConstant.LOAN_TYPE[1]; // non_mobile
				destinationPath[4] = loanType;
				logger.info("Go to uploadFile in sftpservice.non_mobile.");
				commonDestPath = destinationPath;
				responseStatusListAsync = sftpService.uploadFile(imgList, destinationPath);
				responseStatusList = responseStatusListAsync.get();
				uploadApplicationInfoReqBean.setUploadDir(convertArrayToStringPath(destinationPath));
				break;
			case 2:
				loanType = ASSMCommonConstant.LOAN_TYPE[2]; // personal_loan
				String[] destinationPathPl = { ASSMCommonSFTPInfo.BASE_PATH, ASSMCommonSFTPInfo.PHOTO_PATH, applyType,
						memberFilter, loanType, dailyFolderName, imageFoldername };
				logger.info("Go to uploadFile in sftpservice.personal_loan.");
				commonDestPath = destinationPathPl;
				responseStatusListAsync = sftpService.uploadFile(imgList, destinationPathPl);
				responseStatusList = responseStatusListAsync.get();
				uploadApplicationInfoReqBean.setUploadDir(convertArrayToStringPath(destinationPathPl));
				uploadApplicationInfoReqBean.setPlFlag(1); // pl_flag
				break;
			default:
				loanType = ASSMCommonConstant.LOAN_TYPE[3]; // motorcycle_loan
				String[] destinationPathMl = { ASSMCommonSFTPInfo.BASE_PATH, ASSMCommonSFTPInfo.PHOTO_PATH, applyType,
						memberFilter, loanType, location, dailyFolderName, imageFoldername };
				logger.info("Go to uploadFile in sftpservice.motorcycle_loan.");
				commonDestPath = destinationPathMl;
				responseStatusListAsync = sftpService.uploadFile(imgList, destinationPathMl);
				responseStatusList = responseStatusListAsync.get();
				uploadApplicationInfoReqBean.setUploadDir(convertArrayToStringPath(destinationPathMl));
				uploadApplicationInfoReqBean.setPlFlag(2); // ml_flag
				break;
			}

			uploadApplicationInfoReqBean.setFileUploadingStatus(setUploadStatus(responseStatusList));
			int fileId = uploadDocFollowUpInfoDao.persistDocFollowUpInfo(uploadApplicationInfoReqBean);

			// set inserted file_id to response status list.
			for (ResponseStatus status : responseStatusList) {
				status.setFileId(fileId);
			}

		} catch (NoRouteToHostException e0) {

			e0.printStackTrace();
			try {
				uploadApplicationInfoReqBean.setFileUploadingStatus(1); // sent
				int fileId = uploadDocFollowUpInfoDao.persistDocFollowUpInfo(uploadApplicationInfoReqBean);
				for (ResponseStatus status : responseStatusList) {
					status.setStatus(ASSMCommonSFTPInfo.NO_ROUTE_HOST_EXCEPTION);
					status.setFileId(fileId);
					status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
					status.setFolderPath(commonDestPath);
				}
				int index = 0;
				for (MultipartFile file : imgList) {
					responseStatusList.get(index).setFileName(file.getOriginalFilename());
					index++;
				}
			} catch (Exception e) {
				return null;
			}

		} catch (JSchException e1) {

			e1.printStackTrace();
			try {
				uploadApplicationInfoReqBean.setFileUploadingStatus(1); // sent
				int fileId = uploadDocFollowUpInfoDao.persistDocFollowUpInfo(uploadApplicationInfoReqBean);
				for (ResponseStatus status : responseStatusList) {
					status.setStatus(ASSMCommonSFTPInfo.JSCH_EXCEPTION);
					status.setFileId(fileId);
					status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
					status.setFolderPath(commonDestPath);
				}
				int index = 0;
				for (MultipartFile file : imgList) {
					responseStatusList.get(index).setFileName(file.getOriginalFilename());
					index++;
				}
			} catch (Exception e) {
				return null;
			}

		} catch (SftpException e2) {

			e2.printStackTrace();

			try {
				uploadApplicationInfoReqBean.setFileUploadingStatus(1); // sent
				int fileId = uploadDocFollowUpInfoDao.persistDocFollowUpInfo(uploadApplicationInfoReqBean);
				for (ResponseStatus status : responseStatusList) {
					status.setStatus(ASSMCommonSFTPInfo.SFTP_EXCEPTION);
					status.setFileId(fileId);
					status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
					status.setFolderPath(commonDestPath);
				}
				int index = 0;
				for (MultipartFile file : imgList) {
					responseStatusList.get(index).setFileName(file.getOriginalFilename());
					index++;
				}
			} catch (Exception e) {
				return null;
			}

		} catch (IOException e3) { // #unable_to_connect_to_10.1.9.71

			e3.printStackTrace();
			try {
				uploadApplicationInfoReqBean.setFileUploadingStatus(1); // sent
				int fileId = uploadDocFollowUpInfoDao.persistDocFollowUpInfo(uploadApplicationInfoReqBean);
				for (ResponseStatus status : responseStatusList) {
					status.setStatus(ASSMCommonSFTPInfo.IO_EXCEPTION);
					status.setFileId(fileId);
					status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
					status.setFolderPath(commonDestPath);
				}
				int index = 0;
				for (MultipartFile file : imgList) {
					responseStatusList.get(index).setFileName(file.getOriginalFilename());
					index++;
				}
			} catch (Exception e) {
				return null;
			}

		} catch (Exception e) {

			e.printStackTrace();
			try {
				uploadApplicationInfoReqBean.setFileUploadingStatus(1); // sent
				int fileId = uploadDocFollowUpInfoDao.persistDocFollowUpInfo(uploadApplicationInfoReqBean);
				for (ResponseStatus status : responseStatusList) {
					status.setStatus(ASSMCommonSFTPInfo.UPLOAD_FILE_EXCEPTION);
					status.setFileId(fileId);
					status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
					status.setFolderPath(commonDestPath);
				}
				int index = 0;
				for (MultipartFile file : imgList) {
					responseStatusList.get(index).setFileName(file.getOriginalFilename());
					index++;
				}
			} catch (Exception ex) {
				return null;
			}
		}
		return responseStatusList;
	}

	@RequestMapping(value = CommonURL.UPLOAD_IMG_AND_INFO_FIRST, headers = (CommonURL.CONTENT_TYPE_MULT_PATH), method = RequestMethod.POST)
	public List<ResponseStatus> uploadImgAndInfoFirst(
			@RequestParam(value = CommonURL.PARAM_UPLOAD_OBJ, defaultValue = ASSMCommonConstant.BLANK) String uploadObj,
			@RequestPart(name = CommonURL.PARAM_PATH_IMG) List<MultipartFile> imgList) {

		CompletableFuture<List<ResponseStatus>> responseStatusListAsync;
		List<ResponseStatus> responseStatusList = new ArrayList<ResponseStatus>();
		String[] commonDestPath = null;

		logger.info("JSON : " + uploadObj);
		
		UploadApplicationInfoReqBean uploadApplicationInfoReqBean = new Gson().fromJson(uploadObj,
				UploadApplicationInfoReqBean.class);
		try {
			
			logger.info("in uploadImgAndInfoFirst.");
			String applyType = uploadApplicationInfoReqBean.getApplyType().toLowerCase().trim();
			String memberFilter = uploadApplicationInfoReqBean.getMemFilter().toLowerCase().trim();
			String applyNrc = uploadApplicationInfoReqBean.getApplyNrc().trim();
			String location = uploadApplicationInfoReqBean.getLocation().toLowerCase().trim();
			String locationFolderName = uploadApplicationInfoReqBean.getLocation()
					.replaceAll(ASSMCommonConstant.SPACE, ASSMCommonConstant.UNDERSCORE).toUpperCase().trim();
			String teamName = null; // for personal loan user.
			if (uploadApplicationInfoReqBean.getTeamName() != null) {
				teamName = uploadApplicationInfoReqBean.getTeamName().toLowerCase().trim();
			}
			String folderName = uploadApplicationInfoReqBean.getFolderName().trim();
			String agencyName = uploadApplicationInfoReqBean.getAgencyName()
					.replaceAll(ASSMCommonConstant.SPACE, ASSMCommonConstant.UNDERSCORE).toUpperCase().trim();
			String outletName = uploadApplicationInfoReqBean.getOutletName()
					.replaceAll(ASSMCommonConstant.SPACE, ASSMCommonConstant.UNDERSCORE).toUpperCase().trim();
			String nrcName = uploadApplicationInfoReqBean.getNrcName().toUpperCase().trim();
			String dailyFolderName = folderName.substring(0, Math.min(folderName.length(), 8)); // for daily folderName.
			//folderName = folderName.substring(0, Math.min(folderName.length(), 12));
			String applyName = uploadApplicationInfoReqBean.getApplyName()
					.replaceAll(ASSMCommonConstant.SPACE, ASSMCommonConstant.BLANK).toUpperCase();

			if (applyName == null) { // get apply name for doc_follow_up first.
				ApplicationInfoReqDto applicationInfoReqDto = commonUtilsDao.findTopApplicationInfo(applyNrc);
				applyName = applicationInfoReqDto.getName();
			}

			String loanType = ASSMCommonConstant.BLANK;
			String imageFoldername = ASSMCommonConstant.BLANK;
			String uploadedFileName = ASSMCommonConstant.BLANK;

			imageFoldername = folderName + ASSMCommonConstant.DASH + locationFolderName + ASSMCommonConstant.DASH
					+ applyName + ASSMCommonConstant.DASH + ASSMCommonConstant.BLANK + agencyName
					+ ASSMCommonConstant.DASH + outletName + ASSMCommonConstant.DASH + nrcName;

			// define path for save file.
			String[] destinationPath = { ASSMCommonSFTPInfo.BASE_PATH, ASSMCommonSFTPInfo.PHOTO_PATH, applyType,
					memberFilter, loanType, location, teamName, dailyFolderName, imageFoldername };

			uploadedFileName = imageFoldername + ASSMCommonSFTPInfo.COMPRESS_FILE_TYPE;
			uploadApplicationInfoReqBean.setFileName(uploadedFileName);

			// define loan type.
			switch (uploadApplicationInfoReqBean.getLoanType()) {
			case 0:
				loanType = ASSMCommonConstant.LOAN_TYPE[0]; // mobile
				destinationPath[4] = loanType;
				commonDestPath = destinationPath;
				logger.info("in uploadImgAndInfoFirst -------- mobile");
				responseStatusListAsync = sftpService.uploadFile(imgList, destinationPath);
				responseStatusList = responseStatusListAsync.get();
				uploadApplicationInfoReqBean.setUploadDir(convertArrayToStringPath(destinationPath));
				break;
			case 1:
				loanType = ASSMCommonConstant.LOAN_TYPE[1]; // non_mobile
				destinationPath[4] = loanType;
				commonDestPath = destinationPath;
				logger.info("in uploadImgAndInfoFirst -------- non_mobile");
				responseStatusListAsync = sftpService.uploadFile(imgList, destinationPath);
				responseStatusList = responseStatusListAsync.get();
				uploadApplicationInfoReqBean.setUploadDir(convertArrayToStringPath(destinationPath));
				break;
			case 2:
				loanType = ASSMCommonConstant.LOAN_TYPE[2]; // personal_loan
				String[] destinationPathPl = { ASSMCommonSFTPInfo.BASE_PATH, ASSMCommonSFTPInfo.PHOTO_PATH, applyType,
						memberFilter, loanType, dailyFolderName, imageFoldername };
				commonDestPath = destinationPathPl;
				logger.info("in uploadImgAndInfoFirst -------- personal_loan");
				responseStatusListAsync = sftpService.uploadFile(imgList, destinationPathPl);
				responseStatusList = responseStatusListAsync.get();
				uploadApplicationInfoReqBean.setUploadDir(convertArrayToStringPath(destinationPathPl));
				uploadApplicationInfoReqBean.setPlFlag(1); // pl_flag
				break;
			default:
				loanType = ASSMCommonConstant.LOAN_TYPE[3]; // motorcycle_loan
				String[] destinationPathMl = { ASSMCommonSFTPInfo.BASE_PATH, ASSMCommonSFTPInfo.PHOTO_PATH, applyType,
						memberFilter, loanType, location, dailyFolderName, imageFoldername };
				commonDestPath = destinationPathMl;
				logger.info("in uploadImgAndInfoFirst -------- motorcycle_loan");
				responseStatusListAsync = sftpService.uploadFile(imgList, destinationPathMl);
				responseStatusList = responseStatusListAsync.get();
				uploadApplicationInfoReqBean.setUploadDir(convertArrayToStringPath(destinationPathMl));
				uploadApplicationInfoReqBean.setPlFlag(2); // ml_flag
				break;
			}

			uploadApplicationInfoReqBean.setFileUploadingStatus(setUploadStatus(responseStatusList));
			int fileId = uploadFirstApplyInfoDao.persistUploadFirstApplyInfo(uploadApplicationInfoReqBean);

			// set inserted file_id to response status list.
			for (ResponseStatus status : responseStatusList) {
				status.setFileId(fileId);
			}

		} catch (SocketTimeoutException e0) {

			e0.printStackTrace();
			for (ResponseStatus status : responseStatusList) {
				status.setStatus(ASSMCommonSFTPInfo.SOCKET_TIMEOUT_EXCEPTION);
				status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
				status.setFolderPath(commonDestPath);
			}
			int index = 0;
			for (MultipartFile file : imgList) {
				responseStatusList.get(index).setFileName(file.getOriginalFilename());
				index++;
			}

		} catch (NoRouteToHostException e0) {
			logger.info("NoRouteToHostException");
			// e0.printStackTrace();
			try {
				uploadApplicationInfoReqBean.setFileUploadingStatus(1); // sent
				int fileId = uploadFirstApplyInfoDao.persistUploadFirstApplyInfo(uploadApplicationInfoReqBean);
				for (ResponseStatus status : responseStatusList) {
					status.setStatus(ASSMCommonSFTPInfo.NO_ROUTE_HOST_EXCEPTION);
					status.setFileId(fileId);
					status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
					status.setFolderPath(commonDestPath);
				}
				int index = 0;
				for (MultipartFile file : imgList) {
					responseStatusList.get(index).setFileName(file.getOriginalFilename());
					index++;
				}
			} catch (Exception e) {
				return null;
			}

		} catch (JSchException e1) {
			logger.info("JSchException");
			// e1.printStackTrace();
			try {
				uploadApplicationInfoReqBean.setFileUploadingStatus(1); // sent
				int fileId = uploadFirstApplyInfoDao.persistUploadFirstApplyInfo(uploadApplicationInfoReqBean);
				for (ResponseStatus status : responseStatusList) {
					status.setStatus(ASSMCommonSFTPInfo.JSCH_EXCEPTION);
					status.setFileId(fileId);
					status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
					status.setFolderPath(commonDestPath);
				}
				int index = 0;
				for (MultipartFile file : imgList) {
					responseStatusList.get(index).setFileName(file.getOriginalFilename());
					index++;
				}
			} catch (Exception e) {
				return null;
			}

		} catch (SftpException e2) {
			logger.info("SftpException");
			// e2.printStackTrace();
			try {
				uploadApplicationInfoReqBean.setFileUploadingStatus(1); // sent
				int fileId = uploadFirstApplyInfoDao.persistUploadFirstApplyInfo(uploadApplicationInfoReqBean);
				for (ResponseStatus status : responseStatusList) {
					status.setStatus(ASSMCommonSFTPInfo.SFTP_EXCEPTION);
					status.setFileId(fileId);
					status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
					status.setFolderPath(commonDestPath);
				}
				int index = 0;
				for (MultipartFile file : imgList) {
					responseStatusList.get(index).setFileName(file.getOriginalFilename());
					index++;
				}
			} catch (Exception e) {
				return null;
			}

		} catch (IOException e3) { // #unable_to_connect_to_10.1.9.71
			// e3.printStackTrace();
			logger.info("IOException");
			try {
				uploadApplicationInfoReqBean.setFileUploadingStatus(1); // sent
				int fileId = uploadFirstApplyInfoDao.persistUploadFirstApplyInfo(uploadApplicationInfoReqBean);
				for (ResponseStatus status : responseStatusList) {
					status.setStatus(ASSMCommonSFTPInfo.IO_EXCEPTION);
					status.setFileId(fileId);
					status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
					status.setFolderPath(commonDestPath);
				}
				int index = 0;
				for (MultipartFile file : imgList) {
					responseStatusList.get(index).setFileName(file.getOriginalFilename());
					index++;
				}
			} catch (Exception e) {
				return null;
			}
			
		} catch (Exception e) {
			
			// e.printStackTrace();
			logger.info("Exception in PhotoUploadController");
			
			/*try {
				uploadApplicationInfoReqBean.setFileUploadingStatus(1); // sent
				int fileId = uploadFirstApplyInfoDao.persistUploadFirstApplyInfo(uploadApplicationInfoReqBean);
				for (ResponseStatus status : responseStatusList) {
					status.setStatus(ASSMCommonSFTPInfo.UPLOAD_FILE_EXCEPTION);
					status.setFileId(fileId);
					status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
					status.setFolderPath(commonDestPath);
				}
				int index = 0;
				for (MultipartFile file : imgList) {
					responseStatusList.get(index).setFileName(file.getOriginalFilename());
					index++;
				}
			} catch (Exception ex) {
				return null;
			}*/
			
			try {
				
				sftpService.clearUploadedFile(commonDestPath);
				
				uploadApplicationInfoReqBean.setFileUploadingStatus(1); // sent
				
				for (ResponseStatus status : responseStatusList) {
					status.setStatus(ASSMCommonSFTPInfo.RECORD_DUPLICATION);
					//status.setFileId(fileId);
					status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
					status.setFolderPath(commonDestPath);
				}
				
				int index = 0;
				for (MultipartFile file : imgList) {
					responseStatusList.get(index).setFileName(file.getOriginalFilename());
					index++;
				}
				
			} catch (Exception ex) {
				// TODO: handle exception
				return null;
			}
		}
		
		return responseStatusList;
	}

	@RequestMapping(value = CommonURL.UPLOAD_RESEND_AND_UPD_INFO, method = RequestMethod.POST)
	public List<ResponseStatus> resendImagAndUpdateInfo(
			@RequestParam(value = CommonURL.PARAM_RESEND_INFO, defaultValue = ASSMCommonConstant.BLANK) String resendInfo,
			@RequestParam(value = CommonURL.PARAM_UPLOAD_OBJ, defaultValue = ASSMCommonConstant.BLANK) String uploadObj,
			@RequestPart(name = CommonURL.PARAM_PATH_IMG) List<MultipartFile> imgList) {

		CompletableFuture<List<ResponseStatus>> responseStatusListAsync;
		List<ResponseStatus> responseStatusList = new ArrayList<ResponseStatus>();
		String[] commonDestPath = null;
		ResendInfoBean resendInfoBean = new ResendInfoBean();
		UploadApplicationInfoReqBean uploadApplicationInfoReqBean = null;

		List<ApplicationInfoReqDto> appInfoList2 = null;

		try {
			resendInfoBean = new Gson().fromJson(resendInfo, ResendInfoBean.class);
			uploadApplicationInfoReqBean = new Gson().fromJson(uploadObj, UploadApplicationInfoReqBean.class);

			logger.info("Resend information for nrc : " + resendInfoBean.getApplyNrc());

			if (resendInfoBean.getFileId() == 0) {

				String applyNrc = resendInfoBean.getApplyNrc().trim();
				String updatedTime = resendInfoBean.getUpdatedTime();
				Timestamp updateTime = ASSMCommonUtil.getChangeStringToTimeStamp(updatedTime);
				List<ApplicationInfoReqDto> appInfoList = (List<ApplicationInfoReqDto>) commonUtilsDao
						.findApplicationInfoForResend(applyNrc, updateTime);

				if (appInfoList == null || appInfoList.size() == 0) { // no file uploaded yet.

					logger.info("Resend information for new upload.");

					appInfoList2 = (List<ApplicationInfoReqDto>) commonUtilsDao.findApplicationInfoForResend(applyNrc);
					if (appInfoList2 == null) {
						for (int i = 0; i < imgList.size(); i++) {
							ResponseStatus status = new ResponseStatus();
							status.setStatus(ASSMCommonSFTPInfo.NRC_DUPLICATION);
							status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
							status.setFolderPath(commonDestPath);
							responseStatusList.add(status);
						}
					} else {
						for (int i = 0; i < imgList.size(); i++) {
							ResponseStatus status = new ResponseStatus();
							status.setStatus(ASSMCommonSFTPInfo.UPLOAD_FAILED_NO_RECORD);
							status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
							status.setFolderPath(commonDestPath);
							responseStatusList.add(status);
						}
					}

					int index = 0;
					for (MultipartFile file : imgList) {
						responseStatusList.get(index).setFileName(file.getOriginalFilename());
						index++;
					}
					return responseStatusList;

				} else { // file or data already save with sent status.

					int appInfoId = appInfoList.get(0).getId();
					// if status is 3. return success.
					int mStatus = commonUtilsDao.getUploadedStatus(appInfoId, updateTime);
					if (mStatus >= 3) {
						for (int i = 0; i < imgList.size(); i++) {
							ResponseStatus status = new ResponseStatus();
							status.setStatus(ASSMCommonSFTPInfo.UPLOAD_SUCCESS);
							status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
							status.setFolderPath(commonDestPath);
							responseStatusList.add(status);
							logger.info("Resend success.");
						}
						int index = 0;
						for (MultipartFile file : imgList) {
							responseStatusList.get(index).setFileName(file.getOriginalFilename());
							index++;
						}
						return responseStatusList;
					}
				}
			}

			String applyType = uploadApplicationInfoReqBean.getApplyType().toLowerCase().trim();
			String memberFilter = uploadApplicationInfoReqBean.getMemFilter().toLowerCase().trim();
			String applyNrc = uploadApplicationInfoReqBean.getApplyNrc().trim();
			String location = uploadApplicationInfoReqBean.getLocation().toLowerCase().trim();
			String locationFolderName = uploadApplicationInfoReqBean.getLocation()
					.replaceAll(ASSMCommonConstant.SPACE, ASSMCommonConstant.UNDERSCORE).toUpperCase().trim();
			String teamName = null; // for personal loan user.
			if (uploadApplicationInfoReqBean.getTeamName() != null) {
				teamName = uploadApplicationInfoReqBean.getTeamName().toLowerCase().trim();
			}
			String folderName = uploadApplicationInfoReqBean.getFolderName().trim();
			String agencyName = uploadApplicationInfoReqBean.getAgencyName()
					.replaceAll(ASSMCommonConstant.SPACE, ASSMCommonConstant.UNDERSCORE).toUpperCase().trim();
			String outletName = uploadApplicationInfoReqBean.getOutletName()
					.replaceAll(ASSMCommonConstant.SPACE, ASSMCommonConstant.UNDERSCORE).toUpperCase().trim();
			String nrcName = uploadApplicationInfoReqBean.getNrcName().toUpperCase().trim();
			String dailyFolderName = folderName.substring(0, Math.min(folderName.length(), 8)); // for daily folderName.
			//folderName = folderName.substring(0, Math.min(folderName.length(), 12));
			String applyName = uploadApplicationInfoReqBean.getApplyName()
					.replaceAll(ASSMCommonConstant.SPACE, ASSMCommonConstant.BLANK).toUpperCase();

			if (applyName == null) { // get apply name for doc_follow_up first.
				ApplicationInfoReqDto applicationInfoReqDto = commonUtilsDao.findTopApplicationInfo(applyNrc);
				applyName = applicationInfoReqDto.getName();
			}

			String loanType = ASSMCommonConstant.BLANK;
			String imageFoldername = ASSMCommonConstant.BLANK;
			String uploadedFileName = ASSMCommonConstant.BLANK;

			imageFoldername = folderName + ASSMCommonConstant.DASH + locationFolderName + ASSMCommonConstant.DASH
					+ applyName + ASSMCommonConstant.DASH + ASSMCommonConstant.BLANK + agencyName
					+ ASSMCommonConstant.DASH + outletName + ASSMCommonConstant.DASH + nrcName;
			
			//imageFoldername = imageFoldername.trim();

			// define file save path.
			String[] destinationPath = { ASSMCommonSFTPInfo.BASE_PATH, ASSMCommonSFTPInfo.PHOTO_PATH, applyType,
					memberFilter, loanType, location, teamName, dailyFolderName, imageFoldername };

			uploadedFileName = imageFoldername + ASSMCommonSFTPInfo.COMPRESS_FILE_TYPE;

			uploadApplicationInfoReqBean.setFileName(uploadedFileName);

			// define loan type.
			switch (uploadApplicationInfoReqBean.getLoanType()) {
			case 0:
				loanType = ASSMCommonConstant.LOAN_TYPE[0]; // mobile
				destinationPath[4] = loanType;
				commonDestPath = destinationPath;
				responseStatusListAsync = sftpService.uploadFile(imgList, destinationPath);
				responseStatusList = responseStatusListAsync.get();
				uploadApplicationInfoReqBean.setUploadDir(convertArrayToStringPath(destinationPath));
				break;
			case 1:
				loanType = ASSMCommonConstant.LOAN_TYPE[1]; // non_mobile
				destinationPath[4] = loanType;
				commonDestPath = destinationPath;
				responseStatusListAsync = sftpService.uploadFile(imgList, destinationPath);
				responseStatusList = responseStatusListAsync.get();
				uploadApplicationInfoReqBean.setUploadDir(convertArrayToStringPath(destinationPath));
				break;
			case 2:
				loanType = ASSMCommonConstant.LOAN_TYPE[2]; // personal_loan
				String[] destinationPathPl = { ASSMCommonSFTPInfo.BASE_PATH, ASSMCommonSFTPInfo.PHOTO_PATH, applyType,
						memberFilter, loanType, dailyFolderName, imageFoldername };
				commonDestPath = destinationPathPl;
				responseStatusListAsync = sftpService.uploadFile(imgList, destinationPathPl);
				responseStatusList = responseStatusListAsync.get();
				uploadApplicationInfoReqBean.setUploadDir(convertArrayToStringPath(destinationPathPl));
				break;
			default:
				loanType = ASSMCommonConstant.LOAN_TYPE[3]; // motorcycle_loan
				String[] destinationPathMl = { ASSMCommonSFTPInfo.BASE_PATH, ASSMCommonSFTPInfo.PHOTO_PATH, applyType,
						memberFilter, loanType, location, dailyFolderName, imageFoldername };
				commonDestPath = destinationPathMl;
				responseStatusListAsync = sftpService.uploadFile(imgList, destinationPathMl);
				responseStatusList = responseStatusListAsync.get();
				uploadApplicationInfoReqBean.setUploadDir(convertArrayToStringPath(destinationPathMl));
				break;
			}

			uploadApplicationInfoReqBean.setFileUploadingStatus(setUploadStatus(responseStatusList));

			if (uploadApplicationInfoReqBean.getFileUploadingStatus() == 3) {
				int fileId = uploadResendInfoDao.persistResendInfo(resendInfoBean);
				// set inserted file_id to response status list.
				for (ResponseStatus status : responseStatusList) {
					status.setFileId(fileId);
				}
			}

		} catch (SocketTimeoutException e0) {

			e0.printStackTrace();
			for (ResponseStatus status : responseStatusList) {
				status.setStatus(ASSMCommonSFTPInfo.SOCKET_TIMEOUT_EXCEPTION);
				status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
				status.setFolderPath(commonDestPath);
			}
			int index = 0;
			for (MultipartFile file : imgList) {
				responseStatusList.get(index).setFileName(file.getOriginalFilename());
				index++;
			}

		} catch (NoRouteToHostException e1) {

			e1.printStackTrace();
			for (ResponseStatus status : responseStatusList) {
				status.setStatus(ASSMCommonSFTPInfo.NO_ROUTE_HOST_EXCEPTION);
				status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
				status.setFolderPath(commonDestPath);
			}
			int index = 0;
			for (MultipartFile file : imgList) {
				responseStatusList.get(index).setFileName(file.getOriginalFilename());
				index++;
			}

		} catch (IOException e2) {

			e2.printStackTrace();
			for (ResponseStatus status : responseStatusList) {
				status.setStatus(ASSMCommonSFTPInfo.IO_EXCEPTION);
				status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
				status.setFolderPath(commonDestPath);
			}
			int index = 0;
			for (MultipartFile file : imgList) {
				responseStatusList.get(index).setFileName(file.getOriginalFilename());
				index++;
			}

		} catch (SftpException e3) {

			e3.printStackTrace();
			for (ResponseStatus status : responseStatusList) {
				status.setStatus(ASSMCommonSFTPInfo.SFTP_EXCEPTION);
				status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
				status.setFolderPath(commonDestPath);
			}
			int index = 0;
			for (MultipartFile file : imgList) {
				responseStatusList.get(index).setFileName(file.getOriginalFilename());
				index++;
			}

		} catch (JSchException e4) {

			e4.printStackTrace();
			for (ResponseStatus status : responseStatusList) {
				status.setStatus(ASSMCommonSFTPInfo.JSCH_EXCEPTION);
				status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
				status.setFolderPath(commonDestPath);
			}
			int index = 0;
			for (MultipartFile file : imgList) {
				responseStatusList.get(index).setFileName(file.getOriginalFilename());
				index++;
			}

		} catch (Exception e5) {

			/*e5.printStackTrace();
			for (ResponseStatus status : responseStatusList) {
				status.setStatus(ASSMCommonSFTPInfo.UPLOAD_FILE_EXCEPTION);
				status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
				status.setFolderPath(commonDestPath);
			}
			int mIndex = 0;
			for (MultipartFile file : imgList) {
				responseStatusList.get(mIndex).setFileName(file.getOriginalFilename());
				mIndex++;
			}*/
			
			try {
				
				sftpService.clearUploadedFile(commonDestPath);
				
				uploadApplicationInfoReqBean.setFileUploadingStatus(1); // sent
				
				for (ResponseStatus status : responseStatusList) {
					status.setStatus(ASSMCommonSFTPInfo.RECORD_DUPLICATION);
					//status.setFileId(fileId);
					status.setImgFolderName(uploadApplicationInfoReqBean.getImgFolderName());
					status.setFolderPath(commonDestPath);
				}
				
				int index = 0;
				for (MultipartFile file : imgList) {
					responseStatusList.get(index).setFileName(file.getOriginalFilename());
					index++;
				}
				
			} catch (Exception ex) {
				// TODO: handle exception
				return null;
			}

		}
		return responseStatusList;
	}

	/*
	 * @destinationPath : String[] to String.
	 */
	public String convertArrayToStringPath(String[] destinationPath) {
		String path = ASSMCommonConstant.BLANK;
		for (String directory : destinationPath) {
			path = path + ASSMCommonConstant.BACK_SLASH + directory;
		}
		return path;
	}

	/*
	 * @responseStatusList : response list from sftp_service.
	 */
	public int setUploadStatus(List<ResponseStatus> responseStatusList) {
		int uploadStatus = 3; // received.
		if (responseStatusList == null) {
			uploadStatus = 1; // sent.
			return uploadStatus;
		}
		for (ResponseStatus responseStatus : responseStatusList) {
			if (responseStatus.getStatus().equals(ASSMCommonSFTPInfo.UPLOAD_FAILED)
					|| responseStatus.getStatus().equals(ASSMCommonSFTPInfo.UPLOAD_FILE_EXCEPTION)) {
				uploadStatus = 1; // sent.
				return uploadStatus;
			}
		}
		return uploadStatus;
	}
}
