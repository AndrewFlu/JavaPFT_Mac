package ru.andrew.pft.addressbook.applicationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.andrew.pft.addressbook.model.ContactData;
import ru.andrew.pft.addressbook.model.Contacts;

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
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
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

  public void initContactModificationById(int id) {
    driver.findElement(By.xpath(String.format("//input[@id='%s']/../..//td/a/img[@title='Edit']", id))).click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void selectContactById (int id) {
    driver.findElement(By.id(Integer.toString(id))).click();
  }

  public void initContactDeletion() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void submitContactDeletion() {
    submitAlert();
  }

  public void create(ContactData contactData) {
    initContactCreation();
    fillContactForm((contactData), true);
    submitContactCreation();
    returnToHomePage();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    returnToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    initContactDeletion();
    submitContactDeletion();
  }

  public Contacts all() {
    List<WebElement> elements = driver.findElements(By.cssSelector("tr[name=entry]"));
    Contacts contactData = new Contacts();
    for (WebElement element : elements){
      int id = Integer.parseInt(element.findElement(By.cssSelector("td:nth-of-type(1)>input")).getAttribute("id"));
      String name = element.findElement(By.cssSelector("td:nth-of-type(3)")).getText();
      String lastName = element.findElement(By.cssSelector("td:nth-of-type(2)")).getText();
      String allPhones = element.findElement(By.cssSelector("td:nth-of-type(6)")).getText();
      String email_1 = element.findElement(By.cssSelector("td:nth-of-type(5)")).getText();

      contactData.add(new ContactData()
              .withId(id).withName(name).withLastName(lastName)
              .withAllPhone(allPhones)
              .withEmail1(email_1));
    }
    return contactData;
  }

  public int getContactCount() {
    return driver.findElements(By.cssSelector("input[name='selected[]']")).size();
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String name = driver.findElement(By.name("firstname")).getAttribute("value");
    String lastName = driver.findElement(By.name("lastname")).getAttribute("value");
    String homePhone = driver.findElement(By.name("home")).getAttribute("value");
    String mobilePhone = driver.findElement(By.name("mobile")).getAttribute("value");
    String workPhone = driver.findElement(By.name("work")).getAttribute("value");
    driver.navigate().back();
    return new ContactData().withId(contact.getId()).withName(name).withLastName(lastName)
            .withHomePhone(homePhone).withMobilePhone(mobilePhone).withWorkPhone(workPhone);
  }
}
