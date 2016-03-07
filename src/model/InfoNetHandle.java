package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The {@link InfoNetHandle} class is the most basic object in which his data is
 * enough to login to the
 * <i><a href="https://wwwi.colman.ac.il/yedion/fireflyweb.aspx?prgname=login">
 * InfoNet</a></i> interface.
 *
 * @author Asaf Karavani
 *
 */

public  class InfoNetHandle implements Model {
	protected String username;
	protected String password;
	protected WebDriver browser;
	protected WebDriverWait wait;

	/**
	 * This constructor initiate the username and password variables and
	 * creates a {@link WebDriver}.
	 * 
	 * 
	 * @param username
	 * The username that will be used to login to a InfoNet account.
	 * @param password
	 * The password that will be used to login to a InfoNet account.
	 * 
	 */
	public InfoNetHandle(String username, String password) {
		// Initializing the class's variables.
		this.username = username;
		this.password = password;
		browser = new FirefoxDriver();
		wait = new WebDriverWait(browser, 10);
	}

	/**
	 * Starting the browser and trying to login with the given username and passwords.
	 * 
	 * @return
	 * If the InfoNet interface opened successfully it will return true, else it will return false.
	 */	
	public boolean login() {
		try {
			// Opening the InfoNet interface.
			System.out.println("Opening the InfoNet interface...");
			browser.get("https://wwwi.colman.ac.il/yedion/fireflyweb.aspx?prgname=login");

			// Waiting for the password text-box the load.
			// (I am assuming that when it will load the whole page is ready for
			// action)
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("R1C2")));
			System.out.println("InfoNet opened successfully!");

			// Trying to insert the given username and password the the correct
			// text-boxes.
			System.out.println("Trying to login...");
			browser.findElement(By.id("R1C1")).sendKeys(username);
			browser.findElement(By.id("R1C2")).sendKeys(password);
			browser.findElement(By.className("enter")).click();

			// Waiting for the password text-box to disappear.
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("R1C2")));
			 browser.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			if (browser.findElements(By.id("loginError")).size() > 0) {
				System.out.println("Could not login, wrong username/password.");
				return false;				
			} else {
				System.out.println("Logged in successfully!");
				return true;
			}

				

		} catch (Exception e) {
			System.out.println("Could not open the InfoNet interface.");
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * This function will fetch all the links in the menu.
	 * 
	 * @return
	 * {@link List} of {@link WebElement WebElements} which all are link in the side menu of the InfoNet.
	 */
	public List<WebElement> getMenuLinks(){
		//Creating a list of web elements (which will all  be links this time).
		List<WebElement> links = new ArrayList<WebElement>();
		//Switching the scope of the WebDriver to the side menu iFrame.
		browser.switchTo().frame(browser.findElement(By.id("ContextMenuFrame")));
		//inserting all the links in the menu to a list.
		links = browser.findElements(By.tagName("a"));
		//changing the WebDriver scope back to normal.
		return links;
	}
	
	/*---------------GETTERS_TOP----------------*/

	protected String getUserName() {
		return username;
	}

	protected String getPassword() {
		return password;
	}

	public WebDriver getBrowser() {
		return browser;
	}

	public WebDriverWait getWait() {
		return wait;
	}

	/*--------------GETTERS_BOTTOM--------------*/

}
