package com.github.ewanme.romresbot;

import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.ewanme.romresbot.entity.Equipment;
import com.github.ewanme.romresbot.entity.LocationFilter;
import com.github.ewanme.romresbot.entity.Reservation;

public class Romresbot {
	private Reservation reservation = null;
	private FirefoxDriver driver;
	private final int longTimeout = 10;
	private final int shortTimout = 3;
	
	public Romresbot() {
		this.driver = new FirefoxDriver();
	}
	
	/**
	 * Opens a web page in the current web driver.
	 * 
	 * @param url URL for web page to be opened.
	 */
	public void openWebPage(String url) {
		driver.get(url);
	}
	
	/**
	 * Logs in to room reservation page with Feide.
	 * 
	 * @param username Feide user name for login
	 * @param password Feide password for login
	 */
	public void login(String username, String password) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, shortTimout);
			WebElement institution = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("org_selector_filter")));
			institution.sendKeys("NTNU", Keys.ENTER, Keys.ENTER, Keys.ENTER);
			
			WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		    usernameInput.sendKeys(username);
		    driver.findElement(By.id("password")).sendKeys(password);
		    driver.findElement(By.className("button-primary")).click();
		}
		catch (TimeoutException e) {
			throw new TimeoutException("WebElement not found due to timeout.\n" + e.getMessage());
		}
	}
	
	/**
	 * Creates a new Reservation object based on the input, replacing the local Reservation member.
	 * 
	 * @param area Area or building.
	 * @param building Campus building.
	 * @param type Room type.
	 * @param size Number of people the room accommodates.
	 * @param equipment The equipment present in the room.
	 * @param date Date of reservation start.
	 * @param duration Duration of reservation.
	 * @param description Description of what the room will be used for.
	 * @param notes Additional notes.
	 */
	public void createReservation(String area, String building, String type, int size, List<Equipment> equipment, Calendar date, int duration, String description, String notes) {
		LocationFilter location = new LocationFilter(area, building, type, size, equipment);
		reservation = new Reservation(location, date, duration, description, notes);
	}
	
	public Boolean reserveRoom() throws IllegalStateException {
		if (reservation == null) {
			throw new IllegalStateException("Reservation has not been created.");
		}
		
		try {
		
			Calendar date = reservation.getDate();
			int year = date.get(Calendar.YEAR);
			int month = date.get(Calendar.MONTH);
			int day = date.get(Calendar.DATE);
			int hour = date.get(Calendar.HOUR);
			int minute = date.get(Calendar.MINUTE);
			
			WebDriverWait wait = new WebDriverWait(driver, shortTimout);
			
			// Input start time.
			WebElement startTime = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("startblock")));
			startTime.findElement(By.id("select2-start-container")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("select2-search__field")))
				.sendKeys("Hello", String.format("%d:%d", hour, minute));
			
			// Input end time.
		    driver.findElement(By.id("durationblock")).click();
		    WebElement endTimeInput = wait.until(
		    		ExpectedConditions.visibilityOfElementLocated(By.className("select2-search__field")));
		    endTimeInput.sendKeys(String.format("%d:%d", hour + reservation.getDuration(), minute), Keys.ENTER);
		    
		    // Input location of reservation.
		    LocationFilter location = reservation.getLocation();
	
		    if (location.getArea() != null && !location.getArea().isBlank()) {
		        driver.findElement(By.id("select2-area-container")).click();
		        driver.findElement(By.className("select2-search__field")).sendKeys(location.getArea(), Keys.ENTER);
		    }
		    
		    if (location.getBulding() != null && !location.getBulding().isBlank()) {
		        driver.findElement(By.id("select2-building-container")).click();
		        driver.findElement(By.className("select2-search__field")).sendKeys(location.getBulding(), Keys.ENTER);
		    }
		    
		    if (location.getType() != null && !location.getType().isBlank()) {
		        driver.findElement(By.id("select2-roomtype-container")).click();
		        WebElement roomTypeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("select2-search__field")));
		        roomTypeInput.sendKeys(location.getType(), Keys.ENTER);
		    }
		    
		    // Input minimum room size (number of persons)
		    if (location.getRoomSize() > 0) {
		        driver.findElement(By.id("size")).sendKeys(location.getRoomSize().toString());
		    }
		    
		    // Input all specified equipment requirements.
		    if (location.getEquipment() != null && !location.getEquipment().isEmpty()) {
		        for (Equipment eq : location.getEquipment()) {
		            driver.findElement(By.id("select2-new_equipment-container")).click();
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("select2-search__field"))).sendKeys(eq.getName(), Keys.ENTER);
		        }
		    }
		    
		    // Input date of reservation.
		    WebElement datePicker = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("datepicker")));
		    datePicker.clear();
		    datePicker.sendKeys(String.format("%d.%d.%d", day, month, year));
	
		    // Need to click the highlighted date in the calendar because the web site does not accept Enter for some reason.
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ui-datepicker-div"))).findElement(By.className("ui-state-active")).click();
	
		    driver.findElement(By.id("preformsubmit")).click();
	
		    Boolean select =  selectRoom();
		    System.out.println(select);
		    return select;
		}
		catch (Exception e) {
			//driver.close();
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	private Boolean selectRoom() {
		WebDriverWait wait = new WebDriverWait(driver, shortTimout);
		
		WebElement roomChoice = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("roomChoice")));

	    if (roomChoice.getText() == "Ingen mulige rom") {
	        return false;
	    }
	    else {
	        // Selects the first available room in the list of rooms.
	        wait.until(
	        	ExpectedConditions.visibilityOfElementLocated(By.id("room_table"))).findElement(By.tagName("input")).click();

	        driver.findElement(By.id("rb-bestill")).click();

	        // Provide required description
	        wait.until(
	        	ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(reservation.getDescription());

	        // Provide optional notes
	        if (reservation.getNotes() != null && !reservation.getNotes().isBlank()) {
	        	driver.findElement(By.id("notes")).sendKeys(reservation.getNotes());
	            driver.findElement(By.className("button--primary-green")).click();
	        }

	        String confirmText = driver.findElement(By.className("button--primary-green")).getText();
	        return confirmText.equals("Bekreft") ? true : false;
	        // driver.findElement(By.className("button--primary-green")).click();
	    }
	}
}
