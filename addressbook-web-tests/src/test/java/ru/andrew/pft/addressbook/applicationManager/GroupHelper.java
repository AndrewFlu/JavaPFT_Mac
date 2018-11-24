package ru.andrew.pft.addressbook.applicationManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.andrew.pft.addressbook.model.GroupData;
import ru.andrew.pft.addressbook.model.Groups;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupHelper extends HelperBase{

  public GroupHelper(WebDriver driver) {
    super(driver);
  }

  public void submitGroupCreation() {
    click(By.name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getGroupName());
    type(By.name("group_header"), groupData.getGroupHeader());
    type(By.name("group_footer"), groupData.getGroupFooter());
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void returnToGroupPage() {
    click(By.linkText("group page"));
  }

  public void deleteSelectedGroups() {
    click(By.name("delete"));
  }

  private void selectGroupById(int id) {
    driver.findElement(By.cssSelector(String.format("input[value='%d']", id))).click();
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void create(GroupData group) {
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    returnToGroupPage();
  }

  public void modify(GroupData group) {
    selectGroupById(group.getId());
    initGroupModification();
    fillGroupForm(group);
    submitGroupModification();
    returnToGroupPage();
  }

  public void delete(GroupData group) {
    selectGroupById(group.getId());
    deleteSelectedGroups();
    returnToGroupPage();
  }


  public boolean isThereAGroup() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getGroupsCount() {
    return driver.findElements(By.name("selected[]")).size();
  }

  public Groups all(){
    List<WebElement> elements = driver.findElements(By.cssSelector("span.group"));
    Groups groupData = new Groups();
    for (WebElement element : elements){
      int id = Integer.parseInt(element.findElement(By.name("selected[]")).getAttribute("value"));
      String name = element.getText();
      groupData.add(new GroupData().withId(id).withName(name));
    }
    return groupData;
  }
}
