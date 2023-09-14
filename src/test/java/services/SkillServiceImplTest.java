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

class SkillServiceImplTest {
    Skill skill1;
    Service<Skill> skillService;

    @BeforeEach
    void setUp() {

        skillService = new SkillServiceImpl();

        skill1 = new Skill("Test", SkillDomain.PROGRAMMING, SkillLevel.GODLIKE);
    }

    @Test
    void testGetById() {
        skillService.persist(skill1);

        long id = skillService.findByEntity(skill1).getId();

        assertEquals(skill1, skillService.findById(id));

        skillService.delete(skill1);
    }

    @Test
    void testGetSaveDeleteSkill() {
        assertNull(skillService.findByEntity(skill1));

        skillService.persist(skill1);
        assertEquals(skill1, skillService.findByEntity(skill1));

        skillService.delete(skill1);

        assertNull(skillService.findByEntity(skill1));
    }

    @Test
    void testSaveAllGetAllDeleteAll() {
        Skill skill2 = new Skill("Test1", SkillDomain.NONE, SkillLevel.NONE);

        List<Skill> skills =
                new ArrayList<>() {
                    {
                        add(skill1);
                        add(skill2);
                    }
                };

        skillService.persistAll(skills);

        assertTrue(skillService.findAll().containsAll(skills));

        skillService.deleteAll(skills);

        assertFalse(skillService.findAll().containsAll(skills));
    }

    @Test
    void update() {
        skillService.persist(skill1);

        skill1.setName("Test2");
        skillService.update(skill1);

        Skill returnedSkill = skillService.findByEntity(skill1);

        assertEquals("Test2", returnedSkill.getName());

        skillService.delete(returnedSkill);
    }

    @Test
    void whenDeleteSkillThenRemoveItFromPerson() {
        Person person = new Person("Test", "Test", "test.test@test");

        person.addSkill(skill1);

        assertTrue(person.getSkills().contains(skill1));

        PersonService personService = new PersonServiceImpl();
        personService.persist(person);

        Person personFromDB = personService.findByEntity(person);

        assertTrue(personFromDB.getSkills().contains(skill1));

        skillService.delete(skill1);

        assertFalse(person.getSkills().contains(skill1));

        personFromDB = personService.findByEntity(person);

        assertFalse(personFromDB.getSkills().contains(skill1));
    }
}
