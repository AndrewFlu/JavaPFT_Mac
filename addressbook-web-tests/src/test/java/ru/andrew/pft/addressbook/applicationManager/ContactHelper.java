package ru.andrew.pft.addressbook.applicationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.andrew.pft.addressbook.model.ContactData;
import ru.andrew.pft.addressbook.model.Contacts;
import ru.andrew.pft.addressbook.model.GroupData;

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
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    type(By.name("address"), contactData.getAddress());
    attach(By.name("photo"), contactData.getPhoto());

    if (creation) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getGroupName());
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

  public void selectContactById(int id) {
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
    if (! isElementPresent(By.id("maintable"))){
      driver.navigate().back();
    }
    List<WebElement> elements = driver.findElements(By.cssSelector("tr[name=entry]"));
    Contacts contactData = new Contacts();
    for (WebElement element : elements) {
      int id = Integer.parseInt(element.findElement(By.cssSelector("td:nth-of-type(1)>input")).getAttribute("id"));
      String name = element.findElement(By.cssSelector("td:nth-of-type(3)")).getText();
      String lastName = element.findElement(By.cssSelector("td:nth-of-type(2)")).getText();
      String allPhones = element.findElement(By.cssSelector("td:nth-of-type(6)")).getText();
      String allEmails = element.findElement(By.cssSelector("td:nth-of-type(5)")).getText();
      String address = element.findElement(By.cssSelector("td:nth-of-type(4)")).getText();

      contactData.add(new ContactData()
              .withId(id).withName(name).withLastName(lastName)
              .withAllPhone(allPhones)
              .withAllEmails(allEmails)
              .withAddress(address));
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
    String email1 = driver.findElement(By.name("email")).getAttribute("value");
    String email2 = driver.findElement(By.name("email2")).getAttribute("value");
    String email3 = driver.findElement(By.name("email3")).getAttribute("value");
    String address = driver.findElement(By.name("address")).getText();
    driver.navigate().back();
    return new ContactData().withId(contact.getId()).withName(name).withLastName(lastName)
            .withHomePhone(homePhone).withMobilePhone(mobilePhone).withWorkPhone(workPhone)
            .withEmail1(email1).withEmail2(email2).withEmail3(email3)
            .withAddress(address);
  }

  public boolean hasPhoto(ContactData contact) {
    initContactDetailsView(contact.getId());
    return isElementPresent(By.cssSelector("img[alt='Embedded Image']"));
  }

  private void initContactDetailsView(int id) {
    click(By.xpath(String.format("//a[@href='view.php?id=%s']", id)));
  }

  public void addToGroup(ContactData contact, GroupData group) {
    selectContactById(contact.getId());
    selectDesiredGroup(group.getId());
    submitContactGroupAddition();
    driver.navigate().back();
  }

  private void submitContactGroupAddition() {
    click(By.name("add"));
  }

  private void selectDesiredGroup(int groupId) {
    new Select(driver.findElement(By.name("to_group"))).selectByValue(String.valueOf(groupId));
  }

  public void deleteFromGroup(ContactData contact) {
    goToContactGroupsPage(contact.getGroups().iterator().next());
    selectContactById(contact.getId());
    submitContactRemovingFromGroup();
  }

  private void goToContactGroupsPage(GroupData group) {
    new Select(driver.findElement(By.name("group"))).selectByValue(String.valueOf(group.getId()));
  }

  private void submitContactRemovingFromGroup() {
    click(By.name("remove"));
  }
}
