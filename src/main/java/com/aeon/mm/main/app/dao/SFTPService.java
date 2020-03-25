package com.aeon.mm.main.app.dao;

import java.io.BufferedReader;
//import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aeon.mm.main.app.bean.ResponseStatus;
import com.aeon.mm.main.app.common.ASSMCommonSFTPInfo;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

@Service
public class SFTPService {

	static String serverAddress = ASSMCommonSFTPInfo.SFTP_SERVER_ADDRESS;
	static int serverPort = ASSMCommonSFTPInfo.SFTP_SERVER_PORT;
	static String username = ASSMCommonSFTPInfo.SFTP_USERNAME;
	static String password = ASSMCommonSFTPInfo.SFTP_PASSWORD;

	static Logger logger = Logger.getLogger(SFTPService.class);

	public static void main(String[] args) {
		String[] destinationPath = { "photoImage", "DigitalApplicationImage", "301898", "1912000001" };
		Session session = null;
		ChannelSftp channelSftp = null;
		// (1) Connect to Host Command Prompt.
		JSch jsch = new JSch();
		try {
			logger.info("Username : " + "root");
			logger.info("Host : " + "10.1.9.69");
			logger.info("Port : " + "22");

			session = jsch.getSession("root", "10.1.9.69", 22);

			logger.info("Session is created.");
			session.setPassword(password);
			logger.info("Session password set.");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			logger.info("Session is configured.");
			session.connect();
			logger.info("Session is connected.");
			channelSftp = (ChannelSftp) session.openChannel(ASSMCommonSFTPInfo.CHANNEL_TYPE_SFTP);
			logger.info("SFTP Channel is opened");
			channelSftp.connect();
			logger.info("SFTP Channel is connected.");
			channelSftp.cd(ASSMCommonSFTPInfo.ROOT_DIR);

			String imgFolderName = destinationPath[destinationPath.length - 1];
			String dailyFolderName = destinationPath[destinationPath.length - 2];
			for (String directory : destinationPath) {
				// get current working directory.
				String currentDir = channelSftp.pwd();
				logger.info("command sftp : pwd");
				SftpATTRS sftpATTRS = null;

				// check directory already existed.
				try {

					if (currentDir.equals(ASSMCommonSFTPInfo.ROOT_DIR)) {
						sftpATTRS = channelSftp.stat(directory);
						logger.info("command sftp : stat " + directory);// #modify
					} else {
						sftpATTRS = channelSftp.stat(currentDir + "/" + directory);
						logger.info("command sftp : stat " + currentDir + "/" + directory);
					}

				} catch (Exception e) {

					// TODO: handle exception
					// directory not found.
					logger.info("Exception : New Directory Created.");
					logger.info("Exception : " + e.getMessage());
					continue;

				} finally {

					// make directory or change to directory.
					if (sftpATTRS != null) { // if directory existed.
						channelSftp.chmod(Integer.parseInt(ASSMCommonSFTPInfo.ACCESS_MODE_ALL, 8), directory); // give
																												// all
																												// users
																												// (rwx)
																												// mode.
						logger.info("command sftp : chmod 777 " + directory);
						channelSftp.cd(directory);
						logger.info("command sftp : cd " + directory);
					} else { // if directory does not exist.
						channelSftp.mkdir(directory);
						logger.info("command sftp : mkdir " + directory);
						channelSftp.chmod(Integer.parseInt(ASSMCommonSFTPInfo.ACCESS_MODE_ALL, 8), directory); // give
																												// all
																												// users
																												// (rwx)
																												// mode.
						logger.info("command sftp : chmod 777 " + directory);
						channelSftp.cd(directory);
						logger.info("command sftp : cd " + directory);
					}
				}
			}
		} catch (JSchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Async("asyncExecutor")
	public CompletableFuture<List<ResponseStatus>> uploadFile(List<MultipartFile> imgList, String[] destinationPath)
			throws JSchException, SftpException, IOException, SocketTimeoutException, NoRouteToHostException {

		Session session = null;
		ChannelSftp channelSftp = null;
		int imgCount = 0;

		List<ResponseStatus> responselist = new ArrayList<>();

		try {
			// (1) Connect to Host Command Prompt.
			JSch jsch = new JSch();
			logger.info("Username : " + username);
			logger.info("Host : " + serverAddress);
			logger.info("Port : " + serverPort);
			session = jsch.getSession(username, serverAddress, serverPort);
			logger.info("Session is created.");
			session.setPassword(password);
			logger.info("Session password set.");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			logger.info("Session is configured.");
			session.connect();
			logger.info("Session is connected.");
			channelSftp = (ChannelSftp) session.openChannel(ASSMCommonSFTPInfo.CHANNEL_TYPE_SFTP);
			logger.info("SFTP Channel is opened");
			channelSftp.connect();
			logger.info("SFTP Channel is connected.");
			channelSftp.cd(ASSMCommonSFTPInfo.ROOT_DIR);

			String imgFolderName = destinationPath[destinationPath.length - 1];
			String dailyFolderName = destinationPath[destinationPath.length - 2];

			// (2) Check and Create Directory to file save destination.
			for (String directory : destinationPath) {
				// get current working directory.
				String currentDir = channelSftp.pwd();
				logger.info("command sftp : pwd");
				SftpATTRS sftpATTRS = null;

				// check directory already existed.
				try {

					if (currentDir.equals(ASSMCommonSFTPInfo.ROOT_DIR)) {
						sftpATTRS = channelSftp.stat(directory);
						logger.info("command sftp : stat " + directory);// #modify
					} else {
						sftpATTRS = channelSftp.stat(currentDir + "/" + directory);
						logger.info("command sftp : stat " + currentDir + "/" + directory);
					}

				} catch (Exception e) {

					// TODO: handle exception
					// directory not found.
					logger.info("Exception : New Directory Created.");
					logger.info("Exception : " + e.getMessage());
					continue;

				} finally {

					// make directory or change to directory.
					if (sftpATTRS != null) { // if directory existed.
						channelSftp.chmod(Integer.parseInt(ASSMCommonSFTPInfo.ACCESS_MODE_ALL, 8), directory); // give
																												// all
																												// users
																												// (rwx)
																												// mode.
						logger.info("command sftp : chmod 777 " + directory);
						channelSftp.cd(directory);
						logger.info("command sftp : cd " + directory);
					} else { // if directory does not exist.
						channelSftp.mkdir(directory);
						logger.info("command sftp : mkdir " + directory);
						channelSftp.chmod(Integer.parseInt(ASSMCommonSFTPInfo.ACCESS_MODE_ALL, 8), directory); // give
																												// all
																												// users
																												// (rwx)
																												// mode.
						logger.info("command sftp : chmod 777 " + directory);
						channelSftp.cd(directory);
						logger.info("command sftp : cd " + directory);
					}
				}
			}

			// Thread sleep (1s) for other process load.
			try {
				logger.info("Thread Sleeping .....");
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// (3) Save each file to destination point.
			for (MultipartFile file : imgList) {
				ResponseStatus responseStatus = new ResponseStatus();
				try {

					channelSftp.put(file.getInputStream(), file.getOriginalFilename(), ChannelSftp.APPEND);
					logger.info("save image [" + file.getOriginalFilename() + "]");
					channelSftp.chmod(Integer.parseInt(ASSMCommonSFTPInfo.ACCESS_MODE_READONLY, 8), file.getOriginalFilename());
					logger.info("command : chmod 444 " + file.getOriginalFilename());
					responseStatus.setFileName(file.getOriginalFilename());
					responseStatus.setImgFolderName(imgFolderName);
					responseStatus.setStatus(ASSMCommonSFTPInfo.UPLOAD_SUCCESS);
					logger.info("upload image [" + file.getOriginalFilename() + "] success.");

				} catch (Exception e) {

					// TODO: handle exception
					responseStatus.setFileName(file.getOriginalFilename());
					responseStatus.setStatus(ASSMCommonSFTPInfo.UPLOAD_FAILED);
					logger.info("upload image [" + file.getOriginalFilename() + "] failed.");
					// continue;

					// delete failed folder path.
					deleteFailedPath(dailyFolderName, imgFolderName, session, destinationPath);

					return CompletableFuture.completedFuture(responselist);

				} finally {

					responseStatus.setFolderPath(destinationPath);
					responselist.add(responseStatus);

				}
			}

			// (4) Compress and move folder (*.zip).
			try {
				logger.info("Compress image folder : " + imgFolderName);
				compressDir(dailyFolderName, imgFolderName, session, destinationPath);
			} catch (Exception e) {
				// return
				// CompletableFuture.completedFuture(setExceptionStatus(responselist));
				logger.info("Compress process Exception.");

				for (ResponseStatus responseStatus : responselist) {
					responseStatus.setStatus(ASSMCommonSFTPInfo.UPLOAD_FAILED);
				}

				if (channelSftp != null && channelSftp.isConnected()) {
					channelSftp.disconnect();
					logger.info("SFTP Channel disconnected.");
				}

				if (session != null && session.isConnected()) {
					session.disconnect();
					logger.info("Session disconnected.");
				}

				return CompletableFuture.completedFuture(responselist);
			}

			// (5) Change 777 mode for all saved file.
			for (ResponseStatus responseStatus : responselist) {
				if (responseStatus.getStatus().equals(ASSMCommonSFTPInfo.UPLOAD_SUCCESS)) {
					imgCount = imgCount + 1;
					channelSftp.chmod(Integer.parseInt(ASSMCommonSFTPInfo.ACCESS_MODE_ALL, 8), responseStatus.getFileName()); // give
																																// all
																																// users
																																// (rwx)
																																// mode.
					logger.info("command sftp : chmod 777 " + responseStatus.getFileName());
				}
			}

			channelSftp.disconnect();
			logger.info("SFTP Channel disconnected.");

			session.disconnect();
			logger.info("Session disconnected.");

			if (imgCount > 1) {
				logger.info("[" + imgCount + "] images successfully uploaded.");
			} else {
				logger.info("[" + imgCount + "] image successfully uploaded.");
			}

			// (6) Return List.
			return CompletableFuture.completedFuture(responselist);

		} finally {

			if (channelSftp != null && channelSftp.isConnected()) {
				channelSftp.disconnect();
				logger.info("SFTP Channel disconnected.");
			}

			if (session != null && session.isConnected()) {
				session.disconnect();
				logger.info("Session disconnected.");
			}

			logger.info("Finished.");
		}
	}

	public void compressDir(String dailyFolderName, String imgFolderName, Session session, String[] destinationPath) {

		ChannelExec channelExec = null;

		String imgPath = "";
		boolean isFolderExist = false;
		String imgZipFolderName = imgFolderName + ASSMCommonSFTPInfo.COMPRESS_FILE_TYPE;
		for (String directory : destinationPath) {
			imgPath = imgPath + "/" + directory;
			if (directory.equals(dailyFolderName)) {
				isFolderExist = true;
				break;
			}
		}

		try {
			channelExec = (ChannelExec) session.openChannel(ASSMCommonSFTPInfo.CHANNEL_TYPE_EXEC);
			logger.info("Exec Channel is opened.");
			InputStream in = channelExec.getInputStream();
			/*
			 * String command =
			 * "(cd /"+ASSMCommonSFTPInfo.BASE_PATH+"/backup && cd "
			 * +imgPath+" && zip -r "+imgPath+"/"+imgZipFolderName+" "
			 * +imgFolderName+"" +
			 * " && chmod 777 "+imgZipFolderName+" && mv "+imgZipFolderName+" "
			 * +ASSMCommonSFTPInfo.BASE_BACKUP_PATH+")";
			 */
			String command = "(cd \"/" + ASSMCommonSFTPInfo.BASE_PATH + "/backup\" && cd \"" + imgPath + "\" && zip -r \"" + imgPath + "/" + imgZipFolderName + "\" \""
					+ imgFolderName + "\"" + " && chmod 777 \"" + imgZipFolderName + "\" && mv \"" + imgZipFolderName + "\" \"" + ASSMCommonSFTPInfo.BASE_BACKUP_PATH + "\")";
			channelExec.setCommand(command);
			channelExec.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			int index = 0;
			while ((line = reader.readLine()) != null) {
				// System.out.println(++index + " : " + line);
				logger.info(++index + " : " + line);
			}
			logger.info("command exec : " + command);
			int exitStatus = channelExec.getExitStatus();
			// channelExec.disconnect();
			// logger.info("Exec Channel disconnected.");
			/*
			 * if(exitStatus < 0){
			 * logger.info("Compress Done, but exit status not set!"); } else
			 * if(exitStatus > 0){
			 * logger.info("Compress Done, but with error!"); throw new
			 * Exception(); } else{ logger.info("Compress Done!"); }
			 */

			if (exitStatus != 0) {
				logger.info("Compress Done, but with error! | exit-status : " + exitStatus);
				throw new Exception();
			} else {
				logger.info("Compress Done! | exit-status : " + exitStatus);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// remove current directory.
			if (channelExec != null && isFolderExist) {
				String deleteCommand = "cd / && cd \"" + imgPath + "\" && rm -rf \"" + imgFolderName + "\"";
				try {
					InputStream in2 = channelExec.getInputStream();
					channelExec.setCommand(deleteCommand);
					channelExec.connect();
					BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
					String line;
					int index = 0;
					while ((line = reader2.readLine()) != null) {
						// System.out.println(++index + " : " + line);
						logger.info(++index + " : " + line);
					}
					int exitStatus = channelExec.getExitStatus();
					/*
					 * if(exitStatus < 0) {
					 * logger.info("Delete Done, but exit status not set!"); }
					 * else if(exitStatus > 0) {
					 * logger.info("Delete Done, but with error!"); } else {
					 * logger.info("Delete Done!"); }
					 */
					logger.info("command exec : " + deleteCommand);
					if (exitStatus != 0) {
						logger.info("Delete Done, but with error! | exit-status : " + exitStatus);
					} else {
						logger.info("Delete Done! | exit-status : " + exitStatus);
					}

				} catch (Exception ex) {
					// Exception.
					ex.printStackTrace();
				}
			}
		} finally {
			if (channelExec.isConnected() || channelExec != null) {
				channelExec.disconnect();
				logger.info("Connection Disconnected.");
			}
		}
	}

	public void deleteFailedPath(String dailyFolderName, String imgFolderName, Session session, String[] destinationPath) {

		ChannelExec channelExec = null;

		String imgPath = "";
		boolean isFolderExist = false;
		for (String directory : destinationPath) {
			imgPath = imgPath + "/" + directory;
			if (directory.equals(dailyFolderName)) {
				isFolderExist = true;
				break;
			}
		}

		try {

			if (isFolderExist) {

				channelExec = (ChannelExec) session.openChannel(ASSMCommonSFTPInfo.CHANNEL_TYPE_EXEC);
				InputStream in = channelExec.getInputStream();
				String deleteCommand = "cd \"" + imgPath + "\" && rm -rf \"" + imgFolderName + "\"";
				channelExec.setCommand(deleteCommand);
				channelExec.connect();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				logger.info("Delete Command : " + deleteCommand);
				String line;

				int index = 0;
				while ((line = reader.readLine()) != null) {
					System.out.println(++index + " : " + line);
				}

				int exitStatus = channelExec.getExitStatus();

				if (exitStatus < 0) {
					logger.info("Delete Done, but exit status not set!");
				} else if (exitStatus > 0) {
					logger.info("Delete Done, but with error!");
				} else {
					logger.info("Delete Done!");
				}
			}

		} catch (Exception e) {
			logger.info("Delete dir exception!");
		} finally {
			channelExec.disconnect();
		}
	}

	public List<ResponseStatus> setExceptionStatus(List<ResponseStatus> responseStatusList) {
		for (ResponseStatus responseStatus : responseStatusList) {
			responseStatus.setStatus(ASSMCommonSFTPInfo.UPLOAD_FILE_EXCEPTION);
		}
		return responseStatusList;
	}

	public void deleteBackupZipFile(String zipFileName, Session session) {

		ChannelExec channelExec = null;
		final String backupDir = ASSMCommonSFTPInfo.BASE_BACKUP_PATH;

		try {

			channelExec = (ChannelExec) session.openChannel(ASSMCommonSFTPInfo.CHANNEL_TYPE_EXEC);
			InputStream in = channelExec.getInputStream();
			String deleteCommand = "cd \"" + backupDir + "\" && rm -rf \"" + zipFileName + "\"";
			channelExec.setCommand(deleteCommand);
			channelExec.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			logger.info("Delete Command : " + deleteCommand);
			String line;

			int index = 0;
			while ((line = reader.readLine()) != null) {
				System.out.println(++index + " : " + line);
			}

			int exitStatus = channelExec.getExitStatus();

			if (exitStatus < 0) {
				logger.info("Backup Delete Done, but exit status not set!");
			} else if (exitStatus > 0) {
				logger.info("Backup Delete Done, but with error!");
			} else {
				logger.info("Backup Delete Done!");
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Delete dir exception!");
		} finally {
			channelExec.disconnect();
		}

	}

	// clear file.
	@Async("asyncExecutor")
	public void clearUploadedFile(String[] destinationPath) throws JSchException, SftpException {

		Session session = null;
		ChannelSftp channelSftp = null;

		try {

			// (1) Connect to Host Command Prompt.
			JSch jsch = new JSch();
			logger.info("Username : " + username);
			logger.info("Host : " + serverAddress);
			logger.info("Port : " + serverPort);
			session = jsch.getSession(username, serverAddress, serverPort);
			logger.info("Session is created.");
			session.setPassword(password);
			logger.info("Session password set.");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			logger.info("Session is configured.");
			session.connect();
			logger.info("Session is connected.");
			channelSftp = (ChannelSftp) session.openChannel(ASSMCommonSFTPInfo.CHANNEL_TYPE_SFTP);
			logger.info("SFTP Channel is opened");
			channelSftp.connect();
			logger.info("SFTP Channel is connected.");
			channelSftp.cd(ASSMCommonSFTPInfo.ROOT_DIR);

			String imgFolderName = destinationPath[destinationPath.length - 1];
			String dailyFolderName = destinationPath[destinationPath.length - 2];
			final String zipFileName = imgFolderName + ".zip";

			deleteFailedPath(dailyFolderName, imgFolderName, session, destinationPath);
			logger.info("Upload file is deleted.");
			deleteBackupZipFile(zipFileName, session);

		} finally {

			if (channelSftp != null || channelSftp.isConnected()) {
				channelSftp.disconnect();
				logger.info("SFTP Channel disconnected.");
			}

			if (session != null || session.isConnected()) {
				session.disconnect();
				logger.info("Session disconnected.");
			}

			logger.info("Finished.");

		}

	}

}
