package com.aeon.mm.main.app.common;

public class ASSMCommonConstant {

	public static final String BLANK = "";

	public static final String YYYYMMDD_SLASH = "yyyy/MM/dd";

	public static final String COMMA = ",";

	public static final String[] LOAN_TYPE = { "mobile", "non_mobile", "personal_loan", "motorcycle_loan" };

	public static final int[] LOAN_TYPE_INT = { 0, 1, 2, 3 };

	public static final String[] APPLY_TYPE = { "first_time", "follow_up" };

	public static final String UNDERSCORE = "_";

	public static final String SPACE = " ";

	public static final String DASH = "-";

	public static final String BACK_SLASH = "/";

	public static final String FOLLOW_UP_EXT = "-FU";

	public static final int[] FILE_INFO_VALID = { 0, 1 };

	public static final int[] FILE_INFO_TYPE = { 0, 1 };

	public static final String STR_ZERO = "0";

	public static final int ZERO = 0;

	public static final String UTF_8 = "UTF-8";

	public static final String ISO_8859_1 = "iso-8859-1";

	public static final String HTTP_CACHE_CONTROL = "Cache-Control";

	public static final String HTTP_NO_CACHE = "no-cache";

	public static final String HTTP_NO_STORE = "no-store";

	public static final String HTTP_REVALIDATE = "must-revalidate";

	public static final String HTTP_PRAGMA = "Pragma";

	public static final String HTTP_EXPIRE = "Expires";

	public static final String HTTP_ATTACHMENT = "attachment";
	
	public static final String OUT_HEAD_OFFICE = "head-office";
	
	public static final String OK = "OK";
	
	//external download.
	public static final String EXTERNAL_DOWNLOAD_CONTENT_TYPE = "application/zip"; //application/apk
	
	public static final String EXTERNAL_DOWNLOAD_SAVE_NAME = "attachment; filename=\"aeon_assm_service.zip\""; //AEON.apk
	
	public static final String EXTERNAL_DOWNLOAD_PATH = "/mnt/apk/universal/aeon_assm_service.zip";
	//public static final String EXTERNAL_DOWNLOAD_PATH = "/var/apk/universal/aeon_assm_service.zip";
	
	//universal apk download path.
	public static final String UNIVERSAL_DOWNLOAD_PATH = "/mnt/apk/universal/app-universal-release.apk";
	//public static final String UNIVERSAL_DOWNLOAD_PATH = "/var/apk/universal/app-universal-release.apk";
	
}
