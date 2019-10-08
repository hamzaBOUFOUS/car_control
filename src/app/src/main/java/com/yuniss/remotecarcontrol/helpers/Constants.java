package com.yuniss.remotecarcontrol.helpers;

public final class Constants {
    public static final String DEVELOPER_CREDIT_URL = "http://yuniss.com";
    public static final String DATABASE_NAME = "car-control-yuniss-com";
    public static final int START_LOGGING = 0;
    public static final int ACTUAL_LOGIN = 1;
    public static final int START_REGISTER = 0;
    public static final int ACTUAL_REGISTER = 1;
    public static final int PHONE_NUMBER_LENGTH = 10;
    public static final int SUCCESS = 1;
    public static final int DANGER = -1;
    public static final int WARNING = 0;
    public static final int PASSWORD_LENGTH = 6;


    // CMDs of car control
    public static final String BEGIN_CODE = "begin";
    public static final String CHANGE_PASSWORD_CODE = "password";
    public static final String ADD_NUMBER_CODE = "admin";
    public static final String DELETE_NUMBER_CODE = "noadmin";
    public static final String MK_ADMIN_NUMBER_CODE = "centernum";
    public static final String STOP_CAR_CODE = "stop";
    public static final String START_CAR_CODE = "resume";
    public static final String START_EMERGENCY = "arm";
    public static final String STOP_EMERGENCY = "disarm";

}
