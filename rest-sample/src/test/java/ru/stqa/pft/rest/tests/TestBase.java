package ru.stqa.pft.rest.tests;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 12.07.2017.
 */
public class TestBase {


  public boolean isIssueOpen(int issueId) throws IOException {
    String json = getExecutor().execute(Request.Get(String.format("http://demo.bugify.com/api/issues/%s.json", issueId))).returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues").getAsJsonArray();
    JsonElement testIssue = issues.getAsJsonArray().get(0).getAsJsonObject();
    String issueStatus = testIssue.getAsJsonObject().get("state_name").getAsString();
    if (issueStatus.equals("Open")) {
      return true;
    } else {
      return false;
    }
  }

  protected int getIssueStatus() throws IOException {
    String json = getExecutor().execute(Request.Get("http://demo.bugify.com/api/issues.json")).returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues").getAsJsonArray();
    JsonElement testIssue = issues.getAsJsonArray().get(0).getAsJsonObject();
    return Integer.parseInt(testIssue.getAsJsonObject().get("id").getAsString());

  }


  protected Executor getExecutor() {
    return Executor.newInstance().auth("LSGjeU4yP1X493ud1hNniA==", "");
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      System.out.println("It is useless, the bug is still not fixed.");
      throw new SkipException("Ignored because of issue " + issueId);
    }


  }


}


