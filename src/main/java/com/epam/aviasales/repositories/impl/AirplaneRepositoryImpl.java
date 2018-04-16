package com.epam.aviasales.repositories.impl;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirplaneRepository {

  private static final AirplaneRepository instance = new AirplaneRepository();

  public void insert(Airplane airplane) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    session.beginTransaction();

    session.save(airplane);
    session.getTransaction().commit();

    session.close();
  }

  public boolean isExist(Long id) {
    return getAirplane(id) != null;
  }

  public void delete(Airplane airplane) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    session.delete(airplane);

    session.getTransaction().commit();
    session.close();
  }

  public Airplane getAirplane(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Airplane where id=:id");
    query.setParameter("id", id);
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (Airplane) list.get(0) : null;
  }

  public List<Airplane> getAirplanes() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Airplane");
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (List<Airplane>) list : null;
  }

  public static AirplaneRepository getInstance() {
    return instance;
  }
}
