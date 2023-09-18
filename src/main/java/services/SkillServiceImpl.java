package services;

import dao.Dao;
import dao.SkillDaoImpl;
import jakarta.transaction.Transactional;
import model.Skill;

import java.util.List;

public class SkillServiceImpl implements SkillService {

  private final Dao<Skill> skillDao = new SkillDaoImpl();

  @Override
  public void persist(Skill entity) {
    skillDao.persist(entity);
  }

  @Override
  public void persistAll(List<Skill> entities) {
    skillDao.persistAll(entities);
  }

  @Override
  public Skill update(Skill entity) {
    return skillDao.update(entity);
  }

  @Override
  public Skill findById(Long id) {
    return skillDao.get(id);
  }

  @Override
  public Skill findByEntity(Skill skill) {
    return skillDao.get(skill);
  }

  @Override
  public void delete(Skill entity) {
    skillDao.delete(entity);
  }

  @Override
  public List<Skill> findAll() {
    return skillDao.getAll();
  }

  @Override
  public void deleteAll(List<Skill> entities) {
    skillDao.deleteAll(entities);
  }
}
