package ru.stqa.pft.mantis.appmanager;


import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;




/**
 * Created by Admin on 26.07.2017.
 */
public class MailHelper {
  private ApplicationManager app;
  private final Wiser wiser;

  public MailHelper(ApplicationManager app) {
    this.app = app;
    wiser = new Wiser(); //почтовый сервер

  }

  public List<MailMessage> waitForMail(int count, long timeout) throws MessagingException, IOException { //Ожидание письма
    long start = System.currentTimeMillis(); //Смотрим текущее время
    while (System.currentTimeMillis() < start + timeout) {
      if (wiser.getMessages().size() >= count) { //Если писем уже достаточно много, прерываем ожидание
        return wiser.getMessages().stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
      }
      try {
        Thread.sleep(1000); //Подождать секунду
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail :(");
  }

  public static MailMessage toModelMail(WiserMessage m)  {
    try {
      MimeMessage mm = m.getMimeMessage();
      return new MailMessage(mm.getAllRecipients()[0].toString(), (String) mm.getContent());
    } catch (MessagingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
    public void start()  {wiser.start(); }

    public void stop()  {wiser.stop();  }
  }

