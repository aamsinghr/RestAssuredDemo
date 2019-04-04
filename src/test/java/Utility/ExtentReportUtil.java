package Utility;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import RestAssuredDemo.TestRequests;



public class ExtentReportUtil 
{
	 public static ExtentReports report; 
	 public static ExtentTest extentLogger;
	 
	 void setExtentReports()
	 {
		 String extentReportDirName=System.getProperty("user.dir")+"\\extentTestReport";
		 File directory = new File(extentReportDirName);
		 if (! directory.exists())
		        directory.mkdir();
		 ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(extentReportDirName+"\\extentUIRegressionTestReport-"+getDateTime()+".html");
		 htmlReporter.setAppendExisting(false);
		 htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"\\extentReportConfig\\extent-config.xml"));
		   report=new ExtentReports();
		   report.attachReporter(htmlReporter); 
	 	   report.setSystemInfo("User Name", "Aam Singh");
	 	   report.setSystemInfo("Application", "Rest API");
	 	   report.setSystemInfo("Version", "v");
	 	   report.setSystemInfo("Module", "M1");
	 	   report.setSystemInfo("Environment", "QC");
	 	   report.setSystemInfo("Host Name", "hostname");
	 	   //report.setSystemInfo("Profile Name", "")
	 	   //report.setSystemInfo("Region", "us-east-1:US East (N. Virginia)");
	 }
	 void log4jConfigs()
	 {
		 BasicConfigurator.configure();
		 PropertyConfigurator.configure(System.getProperty("user.dir")+"\\Log4j\\log4j.properties");
	 }
	 void startTestMethod(ITestResult result)
	 {
		    if(extentLogger!=null)
		    	report.flush();
		    
		    String testClassName=result.getTestClass().getRealClass().getSimpleName();
			String testMethodName=result.getMethod().getMethodName();
	        extentLogger=report.createTest(testClassName+" :: "+testMethodName,"Here the test class name is :'"+testClassName+"' and test method name is :'"+testMethodName+"'");
	        setAuthorNameAndTestCategory("aam_qa", testClassName);
			extentLogger.log(Status.INFO, "Test method '"+result.getMethod().getMethodName()+"' has <b>Started</b>...");
	 }
	 void passedTestToReport(ITestResult result)
	 {
		 extentLogger.log(Status.PASS, "Test method '"+result.getMethod().getMethodName()+"' has <b>Passed</b>...");
	 }
	 void skippedTestToReport(ITestResult result)
	 {
		extentLogger.log(Status.SKIP, "Test method '"+result.getMethod().getMethodName()+"' has <b>Skipped</b>...");
	 }
	 void failedTestToReport(ITestResult result)
	 {
		 System.out.println("***** Error "+result.getName()+" test has failed *****");
	     String methodName=result.getName().toString().trim();
	     //takeScreenShot(methodName);
	     extentLogger.log(Status.FAIL, result.getThrowable());
	 }
	 void endLoggerAndReport()
	 { 
		 report.flush();
	 }
	 
	public static void setAuthorNameAndTestCategory(String authorName, String testCategory)
    {
		if(extentLogger!=null)
		{
		 if(!authorName.isEmpty())
			 extentLogger.assignAuthor(authorName);
		 
		 extentLogger.assignCategory("Whole-Suite");//Default category
		 if(!testCategory.isEmpty())
			 extentLogger.assignCategory(testCategory);
		}
	 }
	 
	 
	    //User Defined method to get current date and time
	    String getDateTime()
	    {
	    	DateFormat df = new SimpleDateFormat("dd.MM.yyyy_hh-mm-ss");
		    df.setTimeZone(TimeZone.getTimeZone("IST"));
		    return df.format(new Date());

	    }
	    /*
	    public void takeScreenShot(String methodName)
	    {
	     String filePath =System.getProperty("user.dir")+"\\SCREENSHOTS";
	     File scrFile = ((TakesScreenshot)TestRequests.driver).getScreenshotAs(OutputType.FILE);
	         //The below method will save the screen shot in ScreenShots and SCREENSHOTS folder with test method name and current date-time
	        try {
	             String screenShotPath=filePath+"\\"+methodName+"-"+getDateTime()+".png";
		FileUtils.copyFile(scrFile, new File(screenShotPath));
		MediaEntityModelProvider mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build();
		extentLogger.fail("Test method '"+methodName+"' has <b>Failed</b>...(Placed screenshot in '"+filePath+"' location)", mediaModel); 
		// extentLogger.fail("adding screenshots to test").addScreenCaptureFromPath(screenShotPath);
		//extentLogger.log(Status.FAIL, "Test method '"+methodName+"' has <b>Failed</b>...(Placed screenshot in '"+filePath+"' location)"+extentLogger.addScreenCapture(screenShotPath));
		System.out.println("***Placed screen shot in "+filePath+" and ScreenShot ***");
	           } catch(Exception e){e.printStackTrace();}
	    }*/
    
    
    public static ExtentReports getInstance() {
    	if (report == null)
    		new ExtentReportUtil().setExtentReports();
        return report;
    }
    
    public static ExtentTest getTest() {
		return extentLogger;
	}
  
}