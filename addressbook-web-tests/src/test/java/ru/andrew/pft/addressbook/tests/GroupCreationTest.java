package ru.andrew.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupCreationTest extends TestBase{

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().createGroup(new GroupData("Group1", "GroupHeader", "GroupFooter"));
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() + 1);
  }
  @Test
  public void testGroupCreationWithEmptyHeaderAndFooter() {
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().createGroup(new GroupData("Group1", null, null));
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() + 1);
  }
}
