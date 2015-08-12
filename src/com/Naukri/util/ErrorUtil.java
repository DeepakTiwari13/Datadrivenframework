package com.Naukri.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.Reporter;

public class ErrorUtil {
	
	static Logger Naukri_Logs = Logger.getLogger("ErrorUtil");
	private static Map<ITestResult,List> verificationFailuresMap = new HashMap<ITestResult,List>();
	
	     public static void addVerificationFailure(Throwable e) {
	    	    Naukri_Logs.info("*************addVerificationFailure******************");
				List verificationFailures = getVerificationFailures();
				verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
				verificationFailures.add(e);
			}
		  
		  public static List getVerificationFailures() {
			    Naukri_Logs.info("*************getVerificationFailures******************");
				List verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
				return verificationFailures == null ? new ArrayList() : verificationFailures;
			}

}
