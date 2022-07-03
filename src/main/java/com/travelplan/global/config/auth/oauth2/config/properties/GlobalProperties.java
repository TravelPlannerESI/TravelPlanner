package com.travelplan.global.config.auth.oauth2.config.properties;

public class GlobalProperties {
    public static String FRONT_URL = "http://localhost:8000";
    public static String LOGIN_SUCCESS_URL = FRONT_URL+"?success=true";
    public static String LOGIN_FAIL_URL = FRONT_URL+"/oauth/fail";
    public static String ALLOWED_METHODS = "GET,POST,PUT,DELETE,OPTIONS";
    public static String ALLOWED_HEADERS = "*";
    //maxAge 메소드를 이용해서 원하는 시간만큼 pre-flight 리퀘스트를 캐싱가능
    public static int MAX_AGE= 3600;

}
