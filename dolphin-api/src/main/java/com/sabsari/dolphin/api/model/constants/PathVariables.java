package com.sabsari.dolphin.api.model.constants;

public class PathVariables {
	public static final String GROUP_KEY = "{groupKey}";
	public static final String USER_KEY = "{" + AttributeValues.USER_KEY + "}";
	public static final String EMAIL = "{email:.+}";
	
	public static final String PAGE_NUMBER = "{pageNumber}";
	public static final String PAGE_SIZE = "{pageSize}";
	
	public static final String 	TOKEN_HISTORY_TYPE = "{tokenHistoryType}";
	public static final String 	PASSWORD_HISTORY_TYPE = "{passwordHistoryType}";
	public static final String 	PROFILE_HISTORY_TYPE = "{profileHistoryType}";
}
