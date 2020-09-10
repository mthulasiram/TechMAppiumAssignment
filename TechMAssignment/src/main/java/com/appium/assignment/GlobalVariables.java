package com.appium.assignment;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;

public class GlobalVariables {
	private AppiumDriver driver;
	public GlobalVariables( AppiumDriver driver) {
		this.driver = driver;
	}
	
	public  String product_title;
	public  String product_desc;
	public  String product_price;
	
	public String getProduct_title() {
		return product_title;
	}
	////android.view.View[contains(text(),'Hisense')]
	public void setProduct_title(String product_title) {
		this.product_title = driver.findElement(By.xpath(product_title)).getText();
	}
	public String getProduct_desc() {
		return product_desc;
	}
	public void setProduct_desc(String product_desc) {
		this.product_desc = driver.findElement(By.xpath(product_desc)).getText();
	}
	public String getProduct_price() {
		return product_price;
	}
	public void setProduct_price(String product_price) {
		this.product_price = driver.findElement(By.xpath(product_price)).getText();
	}
	
	

}
