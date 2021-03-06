package com.ssafy.bjbj.common.util;

public class LogUtil {

    public static String getClassName() {
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }

    public static String getClassAndMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[2].getClassName();
        String methodName = stackTrace[1].getMethodName();
        return className + " > " + methodName;
    }

}
