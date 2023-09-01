package model;

import java.util.Objects;

public class Person {
  private final String name;
  private final String surname;
  private final String email;

  private Person(PersonBuilder personBuilder) {
    this.name = personBuilder.name;
    this.surname = personBuilder.surname;
    this.email = personBuilder.email;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String getEmail() {
    return email;
  }

  public static class PersonBuilder {

    private String name;
    private String surname;
    private String email;

    public PersonBuilder() {}

    public PersonBuilder name(String name) {
      this.name = Objects.requireNonNullElse(name, "");
      return this;
    }

    public PersonBuilder surname(String surname) {
      this.surname = Objects.requireNonNullElse(surname, "");
      return this;
    }

    public PersonBuilder email(String email) {
      this.email = Objects.requireNonNullElse(email, "");
      return this;
    }

    public Person build() {
      return new Person(this);
    }
  }
}
