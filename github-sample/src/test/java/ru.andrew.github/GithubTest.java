package ru.andrew.github;

import com.jcabi.github.*;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTest {

  @Test
  public void testGithub() throws IOException {
    Github github = new RtGithub("3c445748e71a777583ae0d0bd2e82a8a73fec215");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("AndrewFlu", "JavaPFT_Mac")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())){
//      System.out.println(new RepoCommit.Smart(commit).message());
      System.out.println(commit);
    }

  }
}
