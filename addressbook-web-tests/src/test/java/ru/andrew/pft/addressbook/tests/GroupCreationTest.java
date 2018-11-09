package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.GroupData;

public class GroupCreationTest extends TestBase{

  @Test
  public void testGroupCreation() {
    app.gotoGroupPage();
    app.initGroupCreation();
    app.fillGroupForm(new GroupData("Group1", "GroupHeader", "GroupFooter"));
    app.submitGroupCreation();
    app.returnToGroupPage();
  }
}
