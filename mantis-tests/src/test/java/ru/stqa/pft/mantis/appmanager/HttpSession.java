package ru.stqa.pft.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.remote.http.HttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 26.07.2017.
 */
public class HttpSession {
  private CloseableHttpClient httpClient;
  private ApplicationManager app;

  public HttpSession(ApplicationManager app) {
    this.app = app;
    httpClient  = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();

  }

  public boolean login(String username, String password) throws IOException {
    HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php"); //Пока пустой запрос
    List<NameValuePair> params = new ArrayList<>();
    params.add(new BasicNameValuePair("username", username));//Создаем параметры запроса
    params.add(new BasicNameValuePair("password", password));
    params.add(new BasicNameValuePair("secure_session", "on"));
    params.add(new BasicNameValuePair("return", "index.php"));
    post.setEntity(new UrlEncodedFormEntity(params)); //Упакованные параметры помещаются в ранее созданный запрос
    CloseableHttpResponse response = httpClient.execute(post); //Отправка запроса
    String body = geTextFrom(response); //Ответ на запрос в виде текста
    return body.contains(String.format("<span class=\"user-info\">%s</span>", username)); //Проверка, вошел ли пользователь в систему
  }

  private String geTextFrom(CloseableHttpResponse response) throws IOException {
    try {
      return EntityUtils.toString(response.getEntity());
    } finally {
      response.close();
    }
  }

  public boolean isLoggedInAs(String username) throws IOException { //Проверяет, нужный ли пользователь вошел на страницу
    HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/login.php");
    CloseableHttpResponse response = httpClient.execute(get);
    String body = geTextFrom(response);
    return body.contains(String.format("<span class=\"user-info\">%s</span>", username));

  }

}
