package com.aeon.mm.main.app.common;

public class ASSMCommonSFTPInfo {
	
	public static final String CHANNEL_TYPE_SFTP = "sftp";
	public static final String CHANNEL_TYPE_EXEC = "exec";
	public static final int SFTP_SERVER_PORT = 22;
	
	public static final String SFTP_SERVER_ADDRESS = "10.1.9.70";
	public static final String SFTP_USERNAME = "root";
	public static final String SFTP_PASSWORD = "123@dat";
	public static final String BASE_PATH = "applicationreceivedphoto";
	
	/*public static final String SFTP_SERVER_ADDRESS = "172.16.112.35";
	public static final String SFTP_USERNAME = "appuser";
	public static final String SFTP_PASSWORD = "aeondat2018";
	public static final String BASE_PATH = "data";*/
	
	public static final String PHOTO_PATH = "photo";
	public static final String BACKUP_PATH = "backup";
	public static final String BASE_ROOT_PATH = "/"+BASE_PATH+"/photo/";
	public static final String BASE_BACKUP_PATH = "/"+BASE_PATH+"/backup/";
	public static final String ROOT_DIR = "/";
	public static final String ROOT = "./";
	
	public static final String MAKE_BACKUP_DIR = "mkdir -p /"+BASE_PATH+"/backup ";
	public static final String CMD_SEPARATOR = "&&";
	
	public static final String ACCESS_MODE_ALL = "777";
	public static final String ACCESS_MODE_READONLY = "444";
	
	public static final String COMPRESS_FILE_TYPE = ".zip";
	
	public static final String UPLOAD_SUCCESS = "SUCCESS";
	public static final String UPLOAD_FAILED = "FAILED";
	public static final String UPLOAD_FAILED_NO_RECORD = "UPLOAD_FAILED_NO_RECORD";
	public static final String NRC_DUPLICATION = "NRC_DUPLICATION";
	
	//Exceptions
	public static final String UPLOAD_FILE_EXCEPTION = "EXCEPTION";
	public static final String SOCKET_TIMEOUT_EXCEPTION = "SOCKET_TIMEOUT_EXCEPTION";
	public static final String NO_ROUTE_HOST_EXCEPTION = "NO_ROUTE_HOST_EXCEPTION";
	public static final String IO_EXCEPTION = "IO_EXCEPTION";
	public static final String SFTP_EXCEPTION = "SFTP_EXCEPTION";
	public static final String JSCH_EXCEPTION = "JSCH_EXCEPTION";
	public static final String RECORD_DUPLICATION = "RECORD_DUPLICATION";
	
}
