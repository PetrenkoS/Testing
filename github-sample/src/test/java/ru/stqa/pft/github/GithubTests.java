package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by Admin on 31.07.2017.
 */
public class GithubTests {
  @Test
  public void testCommit() throws IOException {
    Github github = new RtGithub("d3ff2e4a5225514421fced7d63ad989d2a2fd486");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("PetrenkoS", "Testing")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());

    }
  }
}
