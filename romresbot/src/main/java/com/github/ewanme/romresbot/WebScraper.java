package com.github.ewanme.romresbot;

import org.openqa.selenium.firefox.*;
import org.springframework.stereotype.Component;
import io.github.bonigarcia.wdm.WebDriverManager;


@Component
public class WebScraper {
	
	public String openWebPage() {
		WebDriverManager.firefoxdriver().setup();
		FirefoxDriver driver = new FirefoxDriver();
		driver.get("https://www.reddit.com");
		String title = driver.getTitle();
		driver.quit();
		
		return title;
	}
}
