package com.epam.aviasales.util;

import java.io.Serializable;
import java.util.ResourceBundle;
import lombok.extern.log4j.Log4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

@Log4j
public class HibernateUtil {

  private static final SessionFactory sessionFactory = buildSessionFactory();
  private static ServiceRegistry serviceRegistry;
  private static final String HIBERNATE_CFG = "hibernate.cfg.xml";

  private static SessionFactory buildSessionFactory() {
    try {
      // Create session with hibernate.cfg.xml
      Configuration configuration = new Configuration().addResource(HIBERNATE_CFG);
      // Set hibernate credentials
      ResourceBundle credentials = ResourceBundle.getBundle("credentials");
      configuration.setProperty("hibernate.connection.username", credentials.getString("db.username"));
      configuration.setProperty("hibernate.connection.password", credentials.getString("db.password"));
      configuration.setProperty("hibernate.connection.url", credentials.getString("db.url"));

      configuration.configure();
      serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
          .buildServiceRegistry();

      return configuration.buildSessionFactory(serviceRegistry);
    } catch (Throwable ex) {
      log.error("Initial SessionFactory creation failed.", ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static void shutdown() {
    // Clear cache and close connection to DB
    getSessionFactory().close();
  }

}