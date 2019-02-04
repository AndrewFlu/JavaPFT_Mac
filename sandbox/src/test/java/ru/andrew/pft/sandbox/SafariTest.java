package ru.andrew.pft.sandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Test;

public class SafariTest {
  @Test
  public void safariAutomationTest() throws InterruptedException {
    WebDriver driver = new SafariDriver();
    driver.get("https://www.apple.com/ru/");
    driver.findElement(By.xpath("//a[@class='ac-gn-link ac-gn-link-mac']")).click();

    Thread.sleep(5000);
    driver.quit();
  }
}
