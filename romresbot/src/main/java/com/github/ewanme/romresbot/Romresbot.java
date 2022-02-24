package com.github.ewanme.romresbot;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Romresbot {
	private Reservation reservation;
	private FirefoxDriver driver;
	
	public Romresbot(Reservation reservation) {
		this.reservation = reservation;
		this.driver = new FirefoxDriver();
	}
	
	public void openWebPage(String url) {
		driver.get(url);
	}
	
	public String login(String username, String password) {
		WebDriverWait wait = new WebDriverWait(driver, 3);
		var institution = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("org_selector_filter")));
		institution.sendKeys("NTNU", Keys.ENTER, Keys.ENTER, Keys.ENTER);
		
		var username_input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
	    username_input.sendKeys(username);
	    driver.findElement(By.id("password")).sendKeys(password);
	    driver.findElement(By.className("button-primary")).click();
	    
	    String title = driver.getTitle();
	    
	    driver.quit();
	    
	    return title;
	}
}
