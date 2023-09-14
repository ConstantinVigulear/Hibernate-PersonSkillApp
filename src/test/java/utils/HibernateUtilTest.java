package utils;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUtilTest {
  private static final String URL = "src/main/resources/application.properties";
  private static final String BAD_URL = "src/main/resources/applicationCopy.properties";

  @Test
  void whenGetSessionFactoryThenSessionFactoryNotNull() {
    assertNotNull(HibernateUtil.getSessionFactory());
  }

  @Test
  void whenPropertiesFileNotFoundThenThrowException() throws IOException {
    assertThrows(
        RuntimeException.class,
        () -> HibernateUtil.getProperties(BAD_URL));
  }
}
