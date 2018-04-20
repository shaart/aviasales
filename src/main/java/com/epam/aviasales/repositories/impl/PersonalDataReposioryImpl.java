package com.epam.aviasales.repositories.impl;

import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.repositories.PersonalDataRepository;
import com.epam.aviasales.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PersonalDataReposioryImpl implements PersonalDataRepository {

  private static volatile PersonalDataRepository instance;

  public static PersonalDataRepository getInstance() {
    PersonalDataRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (AccountRepositoryImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new PersonalDataReposioryImpl();
        }
      }
    }
    return localInstance;
  }

  public List<PersonalData> getPersonalDatas(){ return new ArrayList<>();}


  public List<PersonalData> getPersonalDatas(int page, int count){ return new ArrayList<>();}


  public PersonalData getPersonalDataById(Long id){
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from PersonalData where id=:id");
    query.setParameter("id", id);
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (PersonalData) list.get(0) : null;}

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
  public boolean isExist(String rowName, String rowValue){
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("from PersonalData WHERE " + rowName + " = :parameter");
    query.setParameter("parameter", rowValue);
    List list = query.list();
    session.close();
    return !list.isEmpty();
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
}
