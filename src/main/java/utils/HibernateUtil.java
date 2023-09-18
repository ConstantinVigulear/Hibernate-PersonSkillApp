package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jakarta.persistence.EntityManagerFactory;
import model.Person;
import model.Skill;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
  private static final String URL = "src/main/resources/application.properties";
  private static EntityManagerFactory entityManagerFactory;

  public static EntityManagerFactory getEntityManagerFactory() {
    if (entityManagerFactory == null) {

      Properties props = getProperties(URL);

      Configuration configuration = new Configuration();
      configuration.setProperties(props);
      configuration.addAnnotatedClass(Person.class);
      configuration.addAnnotatedClass(Skill.class);

      ServiceRegistry serviceRegistry =
          new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

      entityManagerFactory = configuration.buildSessionFactory(serviceRegistry);
    }
    return entityManagerFactory;
  }

  public static Properties getProperties(String url) {
    try {
      InputStream input = new FileInputStream(url);
      Properties props = new Properties();
      props.load(input);
      return props;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
