package ru.andrew.pft.addressbook.applicationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.andrew.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver driver) {
    super(driver);
  }

  public void returnToHomePage() {
    click(By.linkText("home page"));
  }

  public void submitContactCreation() {
    click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Notes:'])[1]/following::input[1]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getContactName());
    type(By.name("lastname"), contactData.getLastContactName());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("email"), contactData.getEmail1());
    if (creation){
      if (contactData.getGroup() != null){
      new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void initContactModification(int index) {
    driver.findElements(By.cssSelector("img[title=\"Edit\"]")).get(index).click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void selectContact(int index) {
    driver.findElements(By.name("selected[]")).get(index).click();
  }

  public void initContactDeletion() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void submitContactDeletion() {
    submitAlert();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public void createContact(ContactData contactData) {
    initContactCreation();
    fillContactForm((contactData), true);
    submitContactCreation();
    returnToHomePage();
  }

  public int getContactsCount() {
    return driver.findElements(By.name("selected[]")).size();
  }

  // метод работает по css-селектору
  public List<ContactData> getContactList() {
    List<WebElement> elements = driver.findElements(By.cssSelector("tr[name=entry]"));
    List<ContactData> contactData = new ArrayList<>();
    for (WebElement element : elements){
      contactData.add(new ContactData(
              element.findElement(By.cssSelector("td:nth-of-type(3)")).getText(), // firstName
              element.findElement(By.cssSelector("td:nth-of-type(2)")).getText(),   // lastName
              element.findElement(By.cssSelector("td:nth-of-type(6)")).getText(), // mobilePhone
              element.findElement(By.cssSelector("td:nth-of-type(5)")).getText(), // email_1
              null  // group
      ));
    }
    return contactData;
  }
}
