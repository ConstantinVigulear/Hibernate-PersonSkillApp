package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
  private Person person;

  @BeforeEach
  void setUp() {
    person =
        new Person.PersonBuilder()
            .name("Kurokami")
            .surname("Onna")
            .email("kurokami.onna@gmail.com")
            .build();
  }

  @Test
  void testNameGetter() {
    assertEquals("Kurokami", person.getName());
  }

  @Test
  void testSurnameSetterGetter() {
    assertEquals("Onna", person.getSurname());
  }

  @Test
  void testEmailSetterGetter() {
    assertEquals("kurokami.onna@gmail.com", person.getEmail());
  }
}
