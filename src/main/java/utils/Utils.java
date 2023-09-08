package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dao.SkillSetDao;
import model.*;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class Utils {

  private static final String URL = "src/main/resources/application.properties";

  public static Connection establishDataBaseConnection() throws IOException, SQLException {

    InputStream input = new FileInputStream(URL);
    Properties props = new Properties();
    props.load(input);

    String url = props.getProperty("datasource.url");
    String username = props.getProperty("datasource.username");
    String password = props.getProperty("datasource.password");

    return DriverManager.getConnection(url, username, password);
  }

  public static void readFromCSV(String path) {

    try (FileReader fileReader = new FileReader(path)) {
      readOneByOne(fileReader);
    } catch (IOException | CsvValidationException | SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private static void readOneByOne(FileReader fileReader)
      throws IOException, CsvValidationException, SQLException {
    CSVReader csvReader = new CSVReader(fileReader);

    String[] csvRecord;
    while ((csvRecord = csvReader.readNext()) != null) {
      writeToDataBase(csvRecord);
    }

    fileReader.close();
    csvReader.close();
  }

  private static void writeToDataBase(String[] csvRecord) throws SQLException, IOException {
    if (!csvRecord[0].equals("name")) {

      Person person =
          new Person.PersonBuilder()
              .name(csvRecord[0])
              .surname(csvRecord[1])
              .email(csvRecord[2])
              .build();

      Skill skill =
          new Skill.SkillBuilder()
              .name(csvRecord[3])
              .domain(SkillDomain.getSkillDomainByName(csvRecord[4]))
              .build();

      SkillSet skillSet = new SkillSet();
      skillSet.setPerson(person);
      skillSet.addSkill(skill, SkillLevel.getSkillLevelByName(csvRecord[5]));

      SkillSetDao skillSetDao = new SkillSetDao();

      if (skillSet.isValid()) skillSetDao.save(skillSet);
    }
  }
}
