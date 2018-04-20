package com.epam.aviasales.repositories.impl;

import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.repositories.AirportRepository;
import com.epam.aviasales.util.HibernateUtil;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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
  public List<Airport> getAirportsLike(Airport seekingAirport, int page, int size) {
    if (size <= 0) {
      return new ArrayList<>();
    }
    if (seekingAirport == null) {
      return getAirports(page, size);
    }
    if (page < 1) {
      page = 1;
    }
    // pages start from 1
    page -= 1;

    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    Criteria criteria = session.createCriteria(Airport.class);
    if (seekingAirport.getId() != null) {
      criteria.add(Restrictions.eq("id", seekingAirport.getId()));
    }
    if (seekingAirport.getName() != null) {
      criteria.add(Restrictions.like("name", seekingAirport.getName(), MatchMode.ANYWHERE));
    }

    criteria.addOrder(Order.asc("id"));
    criteria.setFirstResult(page * size);
    criteria.setMaxResults(size);

    List<Airport> result = (List<Airport>) criteria.list();
    session.getTransaction().commit();
    session.close();

    return result;
  }

  @Override
  public void updateAirport(Long id, Airport updatedAirport) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    if (id != null && id != updatedAirport.getId()) {
      updatedAirport.setId(id);
    }
    session.update(updatedAirport);

    session.getTransaction().commit();
    session.close();
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
  public List<Airport> getAirports(int page, int size) {
    if (size <= 0) {
      return new ArrayList<>();
    }
    if (page < 1) {
      page = 1;
    }
    // pages start from 1
    page -= 1;

    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    Criteria criteria = session.createCriteria(Airport.class);

    criteria.setFirstResult(page * size);
    criteria.setMaxResults(size);
    criteria.addOrder(Order.asc("id"));

    List<Airport> result = (List<Airport>) criteria.list();
    session.getTransaction().commit();
    session.close();

    return result;
  }

  @Override
  public Airport getAirportByName(String name) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Airport where name = :name");
    query.setParameter("name", name);
    List list = query.list();

    Airport airport = list.isEmpty() ? null : (Airport) list.get(0);

    session.getTransaction().commit();
    session.close();

    return airport;
  }
}
