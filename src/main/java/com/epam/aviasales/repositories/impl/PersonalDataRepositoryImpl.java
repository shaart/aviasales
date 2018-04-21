package com.epam.aviasales.repositories.impl;

import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.repositories.PersonalDataRepository;
import com.epam.aviasales.util.HibernateUtil;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class PersonalDataRepositoryImpl implements PersonalDataRepository {

  private static volatile PersonalDataRepository instance;

  public static PersonalDataRepository getInstance() {
    PersonalDataRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (PersonalDataRepositoryImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new PersonalDataRepositoryImpl();
        }
      }
    }

    return localInstance;
  }

  @Override
  public List<PersonalData> getPersonalDatas() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from PersonalData");
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return (List<PersonalData>) list;
  }

  @Override
  public List<PersonalData> getPersonalDatas(int page, int count) {
    throw new UnsupportedOperationException();
  }

  @Override
  public PersonalData getPersonalDataByPassport(String passport) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from PersonalData where passport=:passport");
    query.setParameter("passport", passport);
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (PersonalData) list.get(0) : null;
  }

  @Override
  public PersonalData getPersonalDataById(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from PersonalData where id=:id");
    query.setParameter("id", id);
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (PersonalData) list.get(0) : null;
  }

  @Override
  public void addPersonalData(PersonalData personalData) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    session.beginTransaction();

    session.save(personalData);

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public boolean isExist(PersonalData personalData) {
    return getPersonalDataById(personalData.getId()) != null;
  }

  @Override
  public void updatePersonalDataById(PersonalData personalData) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    Query query =
        session.createQuery(
            "update PersonalData set name = :name, passport = :passport, dateOfBirth = :dateOfBirth where id = :id");
    query.setParameter("name", personalData.getName());
    query.setParameter("passport", personalData.getPassport());
    query.setParameter("dateOfBirth", personalData.getDateOfBirth());
    query.setParameter("id", personalData.getId());
    query.executeUpdate();
    transaction.commit();
    session.close();
  }

  @Override
  public void updatePersonalData(Long id, PersonalData updatedPersonalData) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    if (id != null && id != updatedPersonalData.getId()) {
      updatedPersonalData.setId(id);
    }
    session.update(updatedPersonalData);

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void deletePersonalData(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("delete Ticket where personalData.id = :id");
    query.setParameter("id", id);
    int result = query.executeUpdate();
    query = session.createQuery("delete PersonalData where id = :id");
    query.setParameter("id", id);
    result = query.executeUpdate();

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public List<PersonalData> getPersonalDatasLike(PersonalData seekingPersonalData, int page,
      int size) {
    if (size <= 0) {
      return new ArrayList<>();
    }
    if (seekingPersonalData == null) {
      return getPersonalDatas(page, size);
    }
    if (page < 1) {
      page = 1;
    }
    // pages start from 1
    page -= 1;

    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    Criteria criteria = session.createCriteria(PersonalData.class);
    if (seekingPersonalData.getId() != null) {
      criteria.add(Restrictions.eq("id", seekingPersonalData.getId()));
    }
    if (seekingPersonalData.getName() != null) {
      criteria.add(Restrictions.like("name", seekingPersonalData.getName(), MatchMode.ANYWHERE));
    }
    if (seekingPersonalData.getPassport() != null) {
      criteria.add(
          Restrictions.like("passport", seekingPersonalData.getPassport(), MatchMode.ANYWHERE));
    }
    if (seekingPersonalData.getDateOfBirth() != null) {
      criteria.add(Restrictions.eq("dateOfBirth", seekingPersonalData.getDateOfBirth()));
    }

    criteria.addOrder(Order.asc("id"));
    criteria.setFirstResult(page * size);
    criteria.setMaxResults(size);

    List<PersonalData> result = (List<PersonalData>) criteria.list();
    session.getTransaction().commit();
    session.close();

    return result;
  }
}
