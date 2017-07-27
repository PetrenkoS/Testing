package ru.stqa.pft.mantis.model;

/**
 * Created by Admin on 26.07.2017.
 */
public class MailMessage {

  public String to; //К кому пришло письмо
  public String text; //Текст письма

  public MailMessage(String to, String text) {
    this.to = to;
    this.text = text;
  }
}
