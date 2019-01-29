package ru.andrew.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;

public class RestAssuredCreateIssueTest {

  @BeforeClass
  public void init() {
    RestAssured.authentication = RestAssured.basic("759e17adb9b268d9a484c36345e1e778", "");
  }

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getAllIssues();
    Issue issue = (new Issue().withTitle("Title issue").withDescription("Description issue"));
    int issueId = createIssue(issue);
    Set<Issue> newIssues = getAllIssues();
    oldIssues.add(issue.withId(issueId));
    assertEquals(oldIssues, newIssues);

  }

  private Set<Issue> getAllIssues() throws IOException {
    String json = RestAssured.get("http://demo.bugify.com/api/issues.json?limit=1000").asString();
    JsonElement parse = new JsonParser().parse(json);
    JsonElement parsed = parse.getAsJsonObject().get("issues");
    return new Gson().fromJson(parsed, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  private int createIssue(Issue issue) throws IOException {
    String json = RestAssured.given()
            .param("subject", issue.getTitle())
            .param("description", issue.getDescription())
            .post("http://demo.bugify.com/api/issues.json").asString();
    JsonElement issue_id = new JsonParser().parse(json).getAsJsonObject().get("issue_id");
    return issue_id.getAsInt();
  }
}
