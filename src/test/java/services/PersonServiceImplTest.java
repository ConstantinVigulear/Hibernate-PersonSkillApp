package services;

import model.Person;
import model.Skill;
import model.SkillDomain;
import model.SkillLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceImplTest {

    Service<Person> personService;
    Person person1;

    @BeforeEach
    void setUp() {


    personService = new PersonServiceImpl();

        person1 = new Person();
        person1.setName("George");
        person1.setSurname("Orwell");
        person1.setEmail("george.orwell@gmail.com");
    }

    @Test
    void testGetById() {
        personService.persist(person1);

        long id = personService.findByEntity(person1).getId();

        assertEquals(person1, personService.findById(id));

        personService.delete(person1);
    }

    @Test
    void testGetSaveDeletePerson() {

        personService.persist(person1);
        assertEquals(person1, personService.findByEntity(person1));

        personService.delete(person1);

        assertNull(personService.findByEntity(person1));
    }

    @Test
    void testSaveAllGetAllDeleteAll() {

        Person person2 = new Person();
        person2.setName("Test");
        person2.setSurname("Test");
        person2.setEmail("test.test@gmail.com");

        List<Person> people =
                new ArrayList<>() {
                    {
                        add(person1);
                        add(person2);
                    }
                };

        personService.persistAll(people);

        assertTrue(personService.findAll().containsAll(people));

        personService.deleteAll(people);

        assertFalse(personService.findAll().containsAll(people));
    }

    @Test
    void update() {
        personService.persist(person1);

        person1.setName("Test");
        personService.update(person1);

        Person returnedPerson = personService.findByEntity(person1);

        assertEquals("Test", returnedPerson.getName());

        personService.delete(returnedPerson);
    }

    @Test
    void whenDeletePersonRemoveFromSkills() {
        Skill skill = new Skill("Test", SkillDomain.SECURITY, SkillLevel.GODLIKE);

        person1.addSkill(skill);

        personService.persist(person1);

        assertTrue(person1.getSkills().contains(skill));

        Service<Skill> skillService = new SkillServiceImpl();

        skillService.delete(skill);

        assertFalse(person1.getSkills().contains(skill));
    }
}
