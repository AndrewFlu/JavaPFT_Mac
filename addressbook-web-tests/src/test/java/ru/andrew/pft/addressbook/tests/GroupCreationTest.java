package ru.andrew.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.GroupData;

public class GroupCreationTest extends TestBase{

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().gotoGroupPage();
    int before = app.getGroupHelper().getGroupsCount();
    app.getGroupHelper().createGroup(new GroupData("Group1", "GroupHeader", "GroupFooter"));
    int after = app.getGroupHelper().getGroupsCount();
    Assert.assertEquals(after, before + 1);
  }
  @Test
  public void testGroupCreationWithEmptyHeaderAndFooter() {
    app.getNavigationHelper().gotoGroupPage();
    int before = app.getGroupHelper().getGroupsCount();
    app.getGroupHelper().createGroup(new GroupData("Group1", null, null));
    int after = app.getGroupHelper().getGroupsCount();
    Assert.assertEquals(after, before + 1);
  }
}
