package utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

  @Test
  void establishDataBaseConnection() {
    try {
      assertNotNull(Utils.establishDataBaseConnection());
    } catch (IOException | SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
   void readFromFile() {



    Utils.readFromCSV("src/test/resources/skillSets.csv");
  }

  @Test
  void whenReadNonExistentFileThrowException() {
    assertThrows(RuntimeException.class, () -> {
      Utils.readFromCSV("src/test/resources/nonExistent.csv");
    });
  }
}
