package ru.andrew.pft.addressbook.Generators;

import ru.andrew.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerators {

  public static void main(String[] args) throws IOException {
    int count = Integer.parseInt(args[0]);
    File file = new File(args[1]);

    List<GroupData> groups = generateGroups(count);
    save(groups, file);
  }

  private static void save(List<GroupData> groups, File file) throws IOException {

    Writer fileWriter = new FileWriter(file);
    for (GroupData group : groups) {
      fileWriter.write(String.format("%s;%s;%s\n", group.getGroupName(), group.getGroupHeader(), group.getGroupFooter()));
    }
    fileWriter.close();
  }

  private static List<GroupData> generateGroups(int count) {
    List<GroupData> groups = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++) {
      groups.add(new GroupData().withName(String.format("GroupName_%s", i))
              .withHeader(String.format("GroupHeader_%s", i))
              .withFooter(String.format("GroupFooter_%s", i)));
    }
    return groups;
  }
}
