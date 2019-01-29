package ru.andrew.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;

public class CreateIssueTest {

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
    String json = getExecute().execute(Request.Get("http://demo.bugify.com/api/issues.json")).returnContent().asString();
    JsonElement parse = new JsonParser().parse(json);
    JsonElement parsed = parse.getAsJsonObject().get("issues");
    return new Gson().fromJson(parsed, new TypeToken<Set<Issue>>(){}.getType());
  }

  private Executor getExecute() {
    Executor executor = Executor.newInstance();
    executor.auth("28accbe43ea112d9feb328d2c00b3eed", "");
    return executor;
  }


  private int createIssue(Issue issue) throws IOException {
    String json = getExecute().execute(Request.Post("http://demo.bugify.com/api/issues.json").bodyForm(
            new BasicNameValuePair("subject", issue.getTitle()),
            new BasicNameValuePair("description", issue.getDescription())
    )).returnContent().asString();
    JsonElement issue_id = new JsonParser().parse(json).getAsJsonObject().get("issue_id");
    return issue_id.getAsInt();
  }
}
