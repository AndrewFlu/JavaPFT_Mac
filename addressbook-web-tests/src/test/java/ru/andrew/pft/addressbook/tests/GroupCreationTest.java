package ru.andrew.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.GroupData;
import ru.andrew.pft.addressbook.model.Groups;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupCreationTest extends TestBase{

  @Test
  public void testGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("Group2").withHeader("GroupHeader").withFooter("GroupFooter");
    app.group().create(group);
    Groups after = app.group().all();
    assertEquals(after.size(), before.size() + 1);
    assertThat(after, equalTo(before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testGroupCreationWithEmptyHeaderAndFooter() {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("Group1");
    app.group().create(group);
    Set<GroupData> after = app.group().all();
    assertEquals(after.size(), before.size() + 1);
    assertThat(after, equalTo(before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }
}
