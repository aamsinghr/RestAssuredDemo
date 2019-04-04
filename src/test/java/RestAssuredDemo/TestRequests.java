package RestAssuredDemo;

import static io.restassured.RestAssured.*;

import org.apache.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Utility.ExtentReportUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

/*Test listener as listeners and it will pass the report to extent report
You can add listener to testng.xml file like
<listeners>
	<listener class-name="Utility.TestListener" />
</listeners>
OR you can add listener to class file like below*/
@Listeners({Utility.TestListener.class})
public class TestRequests {
	Logger logger = Logger.getLogger(TestRequests.class);
	ExtentTest extentLogger;
	@Test
	public void seriesTest()
	{
		extentLogger =ExtentReportUtil.getTest();
		logger.info("test 1 is started");
		extentLogger.log(Status.INFO,"test 1 is started");
		Response srcresp = given().get("http://ergast.com/api/f1/2017/circuits.json");
		//response body as string
		String srcbodyresponse = srcresp.asString();

		//create jsonpath for response: both the ways are correct
		//1. you can call jsonPath() using Response object
		//JsonPath jsonPath = srcresp.jsonPath();
		//     OR
		//2. can call JsonPath() constructor by passing response as string
		JsonPath jsonPath = new JsonPath(srcbodyresponse);

		System.out.println(srcbodyresponse);
		System.out.println(jsonPath.toString());
		System.out.println("Status:"+srcresp.getStatusCode());
		System.out.println("Total:"+jsonPath.get("MRData.total"));
		System.out.println("Series:"+jsonPath.get("MRData.series"));
		System.out.println("season:"+jsonPath.get("MRData.CircuitTable.season"));
		Assert.assertEquals(200, srcresp.getStatusCode());
		Assert.assertEquals("20", jsonPath.get("MRData.total"));
		Assert.assertEquals("f1", jsonPath.get("MRData.series"));
		Assert.assertEquals("2017", jsonPath.get("MRData.CircuitTable.season"));
		logger.info("test 1 ends");
		extentLogger.log(Status.INFO,"test 1 ends");
	}

	@Test
	public void roundTest()
	{
		extentLogger =ExtentReportUtil.getTest();
		logger.info("test 2 is started");
		extentLogger.log(Status.INFO,"test 2 is started");
		Response srcresp = given().get("http://ergast.com/api/f1/2017/2/circuits.json");
		String srcbodyresponse = srcresp.asString();
		JsonPath jsonPath = new JsonPath(srcbodyresponse);
		System.out.println(srcbodyresponse);
		System.out.println(jsonPath.toString());
		System.out.println("Status:"+srcresp.getStatusCode());
		System.out.println("Total:"+jsonPath.get("MRData.total"));
		System.out.println("Series:"+jsonPath.get("MRData.series"));
		System.out.println("season:"+jsonPath.get("MRData.CircuitTable.season"));
		System.out.println("round:"+jsonPath.get("MRData.CircuitTable.round"));

		Assert.assertEquals(200, srcresp.getStatusCode());
		Assert.assertEquals("1", jsonPath.get("MRData.total"));
		Assert.assertEquals("f1", jsonPath.get("MRData.series"));
		Assert.assertEquals("2017", jsonPath.get("MRData.CircuitTable.season"));
		Assert.assertEquals("2", jsonPath.get("MRData.CircuitTable.round"));
		logger.info("test 2 ends");
		extentLogger.log(Status.INFO,"test 2 ends");
	}

}
