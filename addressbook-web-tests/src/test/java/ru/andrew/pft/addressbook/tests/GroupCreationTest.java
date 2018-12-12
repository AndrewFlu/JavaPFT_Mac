package ru.andrew.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.andrew.pft.addressbook.model.GroupData;
import ru.andrew.pft.addressbook.model.Groups;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTest extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroupsFromXml() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groupData.xml")));
    String line = reader.readLine();
    String xml = "";
    while (line != null){
      xml += line;
      line = reader.readLine();
    }
    reader.close();

    XStream xStream = new XStream();
    xStream.processAnnotations(GroupData.class);
    List<GroupData> groupData = (List<GroupData>) xStream.fromXML(xml);
    return groupData.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groupData.json")));
    String line = reader.readLine();
    String json = "";
    while (line != null){
      json += line;
      line = reader.readLine();
    }
    reader.close();

    Gson gson = new Gson();
    List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {}.getType());
    return groups.stream().map((g)-> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @Test (dataProvider = "validGroupsFromJson")
  public void testGroupCreation(GroupData group) {
    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().create(group);
    assertThat(app.group().getGroupsCount(), equalTo(before.size() + 1));
    Groups after = app.group().all();
    assertThat(after, equalTo(before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test (dataProvider = "validGroupsFromXml")
  public void testGroupCreationWithEmptyHeaderAndFooter(GroupData group) {
    group.withFooter(null).withHeader(null);
    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().create(group);
    assertThat(app.group().getGroupsCount(), equalTo(before.size() + 1));
    Groups after = app.group().all();
    assertThat(after, equalTo(before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData badGroup = new GroupData().withName("test1'");
    app.group().create(badGroup);
    assertThat(app.group().getGroupsCount(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }
}
