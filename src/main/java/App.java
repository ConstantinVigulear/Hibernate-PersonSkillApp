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

    Skill skill1 = new Skill("Skill1", SkillDomain.PROGRAMMING, SkillLevel.GODLIKE);
    Skill skill2 = new Skill("Skill2", SkillDomain.PROGRAMMING, SkillLevel.HIGH);
    Skill skill3 = new Skill("Skill3", SkillDomain.PROGRAMMING, SkillLevel.LOW);

//    person3 = personService.update(person3);


    System.out.println("Done");
  }
}
