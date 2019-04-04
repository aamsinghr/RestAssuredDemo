package Utility;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends ExtentReportUtil implements ITestListener 
{

	public void onStart(ITestContext context)
    {
		setExtentReports();
		log4jConfigs();
    }
	
	public void onTestStart(ITestResult result) 
    { 
		startTestMethod(result);
    }
  
    public void onTestSuccess(ITestResult result) 
    {   
    	passedTestToReport(result);
    }

    public void onTestSkipped(ITestResult result) 
    {   
    	skippedTestToReport(result);
    }
	
    public void onTestFailure(ITestResult result)
    {
    	failedTestToReport(result);
    }
    
    public void onFinish(ITestContext context)
	{
    
    	endLoggerAndReport();
    	//String email=context.getCurrentXmlTest().getParameter("email");
    	//System.out.println("email:"+email);
    	/*if(email!=null && email.equalsIgnoreCase("yes"))
			try {EmailReport.sendSimpleMail();} catch (Exception e) {e.printStackTrace();}*/
	}
    
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {   }
   
}  