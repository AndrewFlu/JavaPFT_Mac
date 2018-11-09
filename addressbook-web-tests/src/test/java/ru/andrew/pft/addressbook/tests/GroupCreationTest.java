package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.Test;

public class GroupCreationTest extends TestBase{

  @Test
  public void testGroupCreation() {
    gotoGroupPage();
    initGroupCreation();
    fillGroupForm(new GroupData("Group1", "GroupHeader", "GroupFooter"));
    submitGroupCreation();
    returnToGroupPage();
  }
}
