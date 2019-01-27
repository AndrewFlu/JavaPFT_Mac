package ru.andrew.mantis.applicationManager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.andrew.mantis.model.Issue;
import ru.andrew.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
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
    MantisConnectPortType mantisConnectPort = getMantisConnect();
    ProjectData[] projects = mantisConnectPort.mc_projects_get_user_accessible(app.getProperty("soap.adminLogin"), app.getProperty("soap.adminPassword"));

    Set<Project> projectSet = Arrays.asList(projects).stream()
            .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName())).collect(Collectors.toSet());
    return projectSet;
  }

  private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    return new MantisConnectLocator()
            .getMantisConnectPort(new URL(app.getProperty("soap.url")));
  }

  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mantisConnect = getMantisConnect();
    String[] categories = mantisConnect.mc_project_get_categories(app.getProperty("soap.adminLogin")
            , app.getProperty("soap.adminPassword"), BigInteger.valueOf(issue.getProject().getId()));
    IssueData issueData = new IssueData();
    issueData.setCategory(categories[0]);
    issueData.setSummary(issue.getTitle());
    issueData.setDescription(issue.getDescription());
    issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
    BigInteger createdIssueId = mantisConnect.mc_issue_add(app.getProperty("soap.adminLogin"), app.getProperty("soap.adminPassword"), issueData);
    IssueData createdIssue = mantisConnect.mc_issue_get(app.getProperty("soap.adminLogin"), app.getProperty("soap.adminPassword"), createdIssueId);
    return new Issue().withId(createdIssue.getId().intValue())
            .withTitle(createdIssue.getSummary()).withDescription(createdIssue.getDescription())
            .withProject(new Project().withId(createdIssue.getProject().getId().intValue())
            .withName(createdIssue.getProject().getName()));
  }
}
