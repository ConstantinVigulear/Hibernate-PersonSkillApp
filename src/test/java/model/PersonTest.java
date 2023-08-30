package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
  private Person person;

  @BeforeEach
  void setUp() {
    person = new Person();
  }

  @Test
  void testNameSetterGetter() {
    person.setName("Kurokami");
    assertEquals("Kurokami", person.getName());
  }

  @Test
  void testSurnameSetterGetter() {
    person.setSurname("Onna");
    assertEquals("Onna", person.getSurname());
  }

  @Test
  void testEmailSetterGetter() {
    person.setEmail("kurokami.onna@gmail.com");
    assertEquals("kurokami.onna@gmail.com", person.getEmail());
  }
}
