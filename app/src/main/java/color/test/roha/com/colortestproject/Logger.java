package color.test.roha.com.colortestproject;

import android.util.Log;

import java.util.Arrays;

public class Logger {
	public static final boolean isDebugMode = true;
	public static String createLogMsg(String arg_mesg) {
		String returnValue = null;

		try {
			StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();

			// String className = stackTraceElement[4].getClassName();
			String methodName = stackTraceElement[4].getMethodName();
			int lineNum = stackTraceElement[4].getLineNumber();
			String fileName = stackTraceElement[4].getFileName();
			fileName = fileName.replaceAll(".java", "");

			returnValue = lineNum + " [" + fileName + ":" + methodName + "] " + arg_mesg;

		} catch (Exception e) {
			// e.printStackTrace();
		}

		return returnValue;
	}

	public static void log(String arg_mesg) {
		if(!isDebugMode)
			return;
		Log.d("KTH", "	" + createLogMsg(arg_mesg));
	}
	
	public static void log(int arg_mesg) {
		if(!isDebugMode)
			return;
		Log.d("KTH", "	" + createLogMsg(arg_mesg + ""));
	}
	public static void log(String[] datas){
		if(!isDebugMode)
			return;
		if(datas!=null){
			Log.d("KTH",Arrays.toString(datas));
		}
	}
}
