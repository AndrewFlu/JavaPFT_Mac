package ru.andrew.mantis.tests;

import org.testng.annotations.Test;
import ru.andrew.mantis.model.Issue;
import ru.andrew.mantis.model.IssueStatus;
import ru.andrew.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.fail;

public class SoapTest extends TestBase {

  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects();

    System.out.println(projects.size());
    for (Project project : projects) {
      System.out.println(project.getName());
    }
  }

  @Test
  public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
    Set<Project> projects = app.soap().getProjects();
    Issue issue = new Issue().withTitle("Test_issue_3")
            .withDescription("Issue created by SOAP test").withProject(projects.iterator().next());
    Issue createdIssue = app.soap().addIssue(issue);
    assertEquals(issue.getTitle(), createdIssue.getTitle());
  }

  @Test
  public void testIssueStatus() throws RemoteException, ServiceException, MalformedURLException {

    Project project = app.soap().getProjects().iterator().next();
    Issue issue = app.soap().getAllIssues(project).iterator().next();
    IssueStatus issueStatus = app.soap().getIssueData(issue.getId()).getStatus();
    System.out.println(issueStatus.getName());
    System.out.println(issueStatus.getId());
  }

  @Test
  public void testGetAllIssues() throws RemoteException, ServiceException, MalformedURLException {

    Project project = app.soap().getProjects().iterator().next();
    Set<Issue> allIssues = app.soap().getAllIssues(project);
    for (Issue issue : allIssues){
      System.out.println(issue.getId() + " " + issue.getStatus().getName());
    }
  }

  @Test
  public void testSkipExecutionIfNotFixed() throws RemoteException, ServiceException, MalformedURLException {
    skipTestIfNotFixed(0000002);
    fail("This test should be skip, but doesn't");
  }
}
