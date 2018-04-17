package com.epam.aviasales.repositories.impl;

import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.repositories.AirportRepository;
import com.epam.aviasales.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirportRepositoryImpl implements AirportRepository {

  private static volatile AirportRepository instance;

  public static AirportRepository getInstance() {
    AirportRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (AirportRepositoryImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new AirportRepositoryImpl();
        }
      }
    }

    return localInstance;
  }

  @Override
  public void addAirport(Airport airport) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    session.save(airport);

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public boolean isExist(Long id) {
    return getAirportById(id) != null;
  }

  @Override
  public void deleteAirport(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    session.delete(getAirportById(id));

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public Airport getAirportById(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Airport where id=:id");
    query.setParameter("id", id);
    List list = query.list();

    session.getTransaction().commit();
    session.close();
    return list.size() > 0 ? (Airport) list.get(0) : null;
  }

  @Override
  public List<Airport> getAirports() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Airport");
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (List<Airport>) list : null;
  }

  @Override
  public List<Airport> getAirports(int page, int count) {
    return null;
  }

  @Override
  public Airport getAirportByName(String name) {
    return null;
  }
}
