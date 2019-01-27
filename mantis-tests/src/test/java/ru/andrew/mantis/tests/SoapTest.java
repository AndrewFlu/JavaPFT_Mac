package ru.andrew.mantis.tests;

import org.testng.annotations.Test;
import ru.andrew.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class SoapTest extends TestBase {

  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects();

    System.out.println(projects.size());
    for (Project project : projects) {
      System.out.println(project.getName());
    }
  }
}
