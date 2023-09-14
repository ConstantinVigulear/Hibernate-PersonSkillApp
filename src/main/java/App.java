import model.Person;
import model.Skill;
import model.SkillDomain;
import model.SkillLevel;
import services.PersonServiceImpl;
import services.Service;
import services.SkillServiceImpl;

public class App {
  public static void main(String[] args) {

    Service<Person> personService = new PersonServiceImpl();
    Service<Skill> skillService = new SkillServiceImpl();

    Person person1 = new Person("First", "First", "first.first@gmail.com");
    Person person2 = new Person("Second", "Second", "second.second@gmail.com");
    Person person3 = new Person("Third", "Third", "third.third@gmail.com");

    Skill skill1 = new Skill("Java Core", SkillDomain.PROGRAMMING, SkillLevel.GODLIKE);
    Skill skill2 = new Skill("SQL", SkillDomain.PROGRAMMING, SkillLevel.HIGH);
    Skill skill3 = new Skill("C++", SkillDomain.PROGRAMMING, SkillLevel.LOW);

    person1.addSkill(skill1);
    person1.addSkill(skill2);

    person2.addSkill(skill1);
    person2.addSkill(skill2);

    personService.persist(person1);
    personService.persist(person2);
    personService.persist(person3);

    skillService.persist(skill3);

/*
  Can't understand: when merging detached entity multiple times it duplicates in the database
 */
    System.out.println();
  }
}
