package ru.stqa.pft.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 22.07.2017.
 */
public class DbHelper {

  private final SessionFactory sessionFactory;
  private final ApplicationManager app;

  public DbHelper(ApplicationManager app) {
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    this.app = app;
  }

  public Users users() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<UserData> result = session.createQuery("from UserData").list();
    session.getTransaction().commit();
    session.close();
    return new Users(result);
  }

  public Users getSituatedUsers() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<UserData> result = session.createQuery("from UserData where username != 'administrator'").list();
    session.getTransaction().commit();
    session.close();
    return new Users(result);
  }

  public String getUsername() {
    Connection conn = null;
    List<String> result = new ArrayList<>();

    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bugtracker1?user=root&password=");
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("select username from mantis_user_table");
      while (rs.next()) {
        //result.add(new UserData().withUsername(rs.getString("username")));
        result.add(rs.getString("username"));
      }
      rs.close();
      st.close();
      conn.close();
      return result.get(1);

    } catch (SQLException ex) {

      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
      return null;
    }

  }
}
