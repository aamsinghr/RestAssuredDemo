package Utility;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropLoader {
	
	public static String configPath =System.getProperty("user.dir")+"\\src\\main\\java\\utilities\\config.properties";
	Properties prop = new Properties();
	
	public ConfigPropLoader(){
	try {
			InputStream input = null;
			input = new FileInputStream(configPath);
			prop.load(input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getbrowser()
	{
		return prop.getProperty("browser");
	}
	
}
