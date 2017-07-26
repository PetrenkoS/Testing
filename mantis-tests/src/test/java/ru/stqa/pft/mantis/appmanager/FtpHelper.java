package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Admin on 26.07.2017.
 */
public class FtpHelper {

  private final ApplicationManager app;
  private FTPClient ftp;

  public FtpHelper(ApplicationManager app) {
    this.app = app;
    ftp = new FTPClient();
  }

  public void upload(File file, String target, String backup) throws IOException { //Загружает новый файл, а старый временно переименовывает
    ftp.connect(app.getProperty("ftp.host")); //Устанавливается соединение с удаленной машиной
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftp.deleteFile(backup); //Удаляется предыдущий бэкап
    ftp.rename(target, backup); //Переименовывается новый бэкап
    ftp.enterLocalPassiveMode();
    ftp.storeFile(target, new FileInputStream(file)); //Загружается новый файл с локальной машины
    ftp.disconnect();
  }

  public void restore(String backup, String target) throws IOException { // Восстанавливает старый файл
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftp.deleteFile(backup);
    ftp.rename(backup, target);
    ftp.disconnect();
  }
}
