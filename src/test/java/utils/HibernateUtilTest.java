package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUtilTest {
  private static final String BAD_URL = "src/main/resources/applicationCopy.properties";

  @Test
  void whenGetSessionFactoryThenSessionFactoryNotNull() {
    assertNotNull(HibernateUtil.getEntityManagerFactory());
  }

  @Test
  void whenPropertiesFileNotFoundThenThrowException() {
    assertThrows(
        RuntimeException.class,
        () -> HibernateUtil.getProperties(BAD_URL));
  }
}
