package ru.andrew.pft.addressbook.Generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.andrew.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerators {

  @Parameter(names = "-c", description = "Group count")
  public int count;

  @Parameter(names = "-f", description = "Destination file")
  public String file;

  public static void main(String[] args) throws IOException {
    GroupDataGenerators generators = new GroupDataGenerators();
    try {
      JCommander.newBuilder().addObject(generators).build().parse(args);
    } catch (ParameterException ex) {
      ex.usage();
    }
    generators.run();
  }

  private void run() throws IOException {
    List<GroupData> groups = generateGroups(count);
    save(groups, new File(file));
  }

  private void save(List<GroupData> groups, File file) throws IOException {
    Writer fileWriter = new FileWriter(file);
    for (GroupData group : groups) {
      fileWriter.write(String.format("%s;%s;%s\n", group.getGroupName(), group.getGroupHeader(), group.getGroupFooter()));
    }
    fileWriter.close();
  }

  private List<GroupData> generateGroups(int count) {
    List<GroupData> groups = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++) {
      groups.add(new GroupData()
              .withName(String.format("GroupName_%s", i))
              .withHeader(String.format("GroupHeader_%s", i))
              .withFooter(String.format("GroupFooter_%s", i)));
    }
    return groups;
  }
}
