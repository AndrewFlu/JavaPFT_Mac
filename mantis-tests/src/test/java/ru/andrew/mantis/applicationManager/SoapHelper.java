package ru.andrew.mantis.applicationManager;

import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ProjectData;
import ru.andrew.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {


  private final ApplicationManager app;

  public SoapHelper(ApplicationManager app) {
    this.app = app;
  }

  public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mantisConnectPort = new MantisConnectLocator()
            .getMantisConnectPort(new URL("http://localhost/mantisbt-2.19.0/api/soap/mantisconnect.php"));
    ProjectData[] projects = mantisConnectPort.mc_projects_get_user_accessible("administrator", "root");

    Set<Project> projectSet = Arrays.asList(projects).stream()
            .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName())).collect(Collectors.toSet());
    return projectSet;
  }
}
