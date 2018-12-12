package ru.andrew.pft.addressbook.Generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.andrew.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerators {

  @Parameter(names = "--count", description = "Group count")
  public int count;

  @Parameter(names = "--file", description = "Destination file")
  public String file;

  @Parameter(names = "--format", description = "Data format")
  public String format;

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
    if (format.equals("csv")){
      saveAsCsv(groups, new File(file));
    } else if (format.equals("xml")){
      saveAsXml(groups, new File(file));
    } else if (format.equals("json")){
      saveAsJson(groups, new File(file));
    } else {
      System.out.println("Unrecognized format: " + format);
    }
  }

  private void saveAsJson(List<GroupData> groups, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(groups);
    Writer writer = new FileWriter(file);
    writer.write(json);
    writer.close();
  }

  private void saveAsCsv(List<GroupData> groups, File file) throws IOException {
    Writer fileWriter = new FileWriter(file);
    for (GroupData group : groups) {
      fileWriter.write(String.format("%s;%s;%s\n", group.getGroupName(), group.getGroupHeader(), group.getGroupFooter()));
    }
    fileWriter.close();
  }

  private void saveAsXml(List<GroupData>groups, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(GroupData.class);
    String xml = xstream.toXML(groups);
    Writer writer = new FileWriter(file);
    writer.write(xml);
    writer.close();
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
