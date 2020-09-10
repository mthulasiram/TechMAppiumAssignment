package com.appium.assignment;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;


/**
 * 
 * @author Thulasiram Muppala
 * Email Id: MR00680722@techmahindra.com
 * Date: 09-09-2020 
 */
public class LoginActivityTest {
	
	AppiumDriver driver;
	GlobalVariables globalVariables;
	DesiredCapabilities capabilities = new DesiredCapabilities();
	WebDriverWait wait;
	
  @BeforeClass
  public void setUp() throws MalformedURLException {
	  		// Set up desired capabilities and pass the Android app-activity and app-package to Appium
			System.err.println("setUp.......");
			capabilities.setCapability("platformName","Android");
			capabilities.setCapability("platformVersion","9.0");
			capabilities.setCapability("deviceName","vivo 1723");
			capabilities.setCapability("automationName","UiAutomator2");
		    capabilities.setCapability("appPackage", "com.amazon.mShop.android.shopping");
		    // This package name of your app (you can get it from apk info app)
			capabilities.setCapability("appActivity","com.amazon.mShop.home.HomeActivity"); // This is Launcher activity of your app (you can get it from apk info app)
		    // Create RemoteWebDriver instance and connect to the Appium server
		    // It will launch the Calculator App in Android Device using the configurations specified in Desired Capabilities
			capabilities.setCapability("unicodeKeyboard", true);
			driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			globalVariables = new GlobalVariables(driver);
			wait = new WebDriverWait(driver,20);
  }
  
  /**
   * This method will perform login test cases, if the user already exists, it will click on conitnue button, 
   * Otherwise, will send username and password to login
   * 
   * @throws InterruptedException
   */
  
  @Test(priority=1)
  public void login() throws InterruptedException {
	  Thread.sleep(5000);
	  
	  boolean isPresent = driver.findElements(By.xpath("//android.widget.Button[@text='Already a customer? Sign in']")).size() > 0;
			  
	  if (isPresent) {
		  WebElement submitBtn=driver.findElement(By.xpath("//android.widget.Button[@text='Already a customer? Sign in']"));
		  wait.until(ExpectedConditions.visibilityOf(submitBtn));
		  submitBtn.click();
		  
		  WebElement addEmail=driver.findElement(By.xpath("//android.widget.EditText[@resource-id='ap_email_login']"));
		  wait.until(ExpectedConditions.visibilityOf(addEmail));
		  addEmail.sendKeys("Please username");
		  
		  WebElement continueBtn=driver.findElement(By.xpath("//android.widget.Button[@resource-id='continue']"));
		  wait.until(ExpectedConditions.visibilityOf(continueBtn)); 
		  continueBtn.click();
	
		  WebElement passwordField=driver.findElement(By.xpath("//android.widget.EditText[@resource-id='ap_password']"));
		  //Please enteryour password
		  wait.until(ExpectedConditions.visibilityOf(passwordField)); 
		  passwordField.sendKeys("");
		  
		  WebElement loginBtn=driver.findElement(By.xpath("//android.widget.Button[@resource-id='signInSubmit']"));
		  loginBtn.click();
	  }else {
		  WebElement continueBtn=driver.findElement(By.xpath("//android.widget.Button[@resource-id='com.amazon.mShop.android.shopping:id/sso_continue']"));
		  wait.until(ExpectedConditions.visibilityOf(continueBtn)); 
		  continueBtn.click();
	  }
	  	  
  }
  
  /**
   * This Method will search the products in serch box with "65 Inches TV" text
   * @throws InterruptedException
   */
  @SuppressWarnings("deprecation")
@Test(priority=2)
  public void searchProduct() throws InterruptedException {

	  WebElement searchFeild=driver.findElement(By.xpath("//android.widget.EditText[@text='Search']"));
	  wait.until(ExpectedConditions.visibilityOf(searchFeild)); 
	  searchFeild.click();	  
	  
	  WebElement searchFeild2=driver.findElement(By.xpath("//android.widget.EditText[@text='Search']"));
	  wait.until(ExpectedConditions.visibilityOf(searchFeild2)); 
	  searchFeild2.sendKeys("65 inch TV");
	  
	  
	  driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "Go"));
	  
	  Thread.sleep(3000);
	  
  }
  
  /**
   * This method will search for the product in resulted list and will click on it 
   * @throws InterruptedException
   */
  @Test(priority=3)
  public void chooseProduct() throws InterruptedException {
	 
	  List<WebElement> ulElement = driver.findElements(By.xpath("//android.widget.ListView[@index='0']/android.widget.LinearLayout"));

	  int sz = ulElement.size();
	  System.out.println("size..... "+ sz);
	  if (sz > 1 ) {
		  Random rand = new Random();
		  int index = rand.nextInt(sz-1); // -1 because index will start from 0
		  AndroidElement list = (AndroidElement) driver.findElement(By.id("com.amazon.mShop.android.shopping:id/rs_vertical_stack_view"));
	        MobileElement listGroup = list
	                .findElement(MobileBy
	                        .AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
	                                + "new UiSelector().textContains(\"Hisense 108 cm\"));"));
	        assertNotNull(listGroup.getLocation());
	        listGroup.click();
		   Thread.sleep(5000);
	  }else {
		  System.out.println("nothing...");
	  }
	  
	  
	  
  }
  
  
  /**
   * This method will add the product to cart 
   * @throws InterruptedException
   */
  @Test(priority = 4)
  public void addToCartCase() throws InterruptedException {
	  Thread.sleep(5000);
	  storeProductDetails();
	  Thread.sleep(3000);
	  new TouchAction(driver).press(PointOption.point(550, 640)).waitAction().moveTo(PointOption.point(550,20)).release().perform();
	  //Scroll Again to get ID
	  new TouchAction(driver).press(PointOption.point(550, 640)).waitAction().moveTo(PointOption.point(550,20)).release().perform();
	  //Scroll Again to get ID
	  new TouchAction(driver).press(PointOption.point(550, 640)).waitAction().moveTo(PointOption.point(550,20)).release().perform();
	//Scroll Again to get ID
	  new TouchAction(driver).press(PointOption.point(550, 640)).waitAction().moveTo(PointOption.point(550,20)).release().perform();
	  driver.findElement(By.xpath("//android.widget.Button[@resource-id='add-to-cart-button']")).click();;
  }
  
  
  /**
   * This method will compare the required product before checkout 
   */
 @Test(priority=5)
 public void checkOutPage()
 {	
	 //click on cart
	 WebElement cartButton=driver.findElement(By.id("a-autoid-0-announce"));
	 cartButton.click();
	 
	 WebElement proodDesc=driver.findElement(By.xpath("//android.view.View[contains(@content-desc,'Hisense')]/android.widget.TextView"));
	 assertEquals(proodDesc.getText(), globalVariables.getProduct_desc());
	 
	 WebElement proodTitle=driver.findElement(By.xpath("//android.view.View[contains(@content-desc,'Hisense')]/android.widget.TextView"));
	 assertEquals(proodTitle.getText(), globalVariables.getProduct_desc());
	 
	 WebElement proodPrice=driver.findElement(By.xpath("//android.view.View[contains(@content-desc,'rupees')]/android.widget.TextView"));
	 assertEquals(proodPrice.getText(), globalVariables.getProduct_desc());
	 
	 
	 //click on Checkout
	 WebElement checkoutButton=driver.findElement(By.id("a-autoid-0-check-out"));
	 checkoutButton.click();
	 
	 
 }
 
  public void storeProductDetails()
  {  
	  globalVariables.setProduct_desc("//android.widget.Button[contains(text(),'65 Inches')]");
	  globalVariables.setProduct_price("//android.widget.Button[contains(text(),'rupees')]");
	  globalVariables.setProduct_title("//android.view.View[contains(text(),'Hisense')]");
  }
 
  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

 
}
