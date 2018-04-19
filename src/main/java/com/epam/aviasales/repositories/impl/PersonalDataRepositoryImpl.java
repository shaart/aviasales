package com.epam.aviasales.repositories.impl;

import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.repositories.PersonalDataRepository;
import com.epam.aviasales.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

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
}
