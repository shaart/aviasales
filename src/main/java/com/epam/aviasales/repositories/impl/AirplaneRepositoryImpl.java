package com.epam.aviasales.repositories.impl;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.repositories.AirplaneRepository;
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
public class AirplaneRepositoryImpl implements AirplaneRepository {

  private static volatile AirplaneRepository instance;

  public static AirplaneRepository getInstance() {
    AirplaneRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (AirplaneRepositoryImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new AirplaneRepositoryImpl();
        }
      }
    }

    return localInstance;
  }

  @Override
  public void addAirplane(Airplane airplane) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    session.save(airplane);

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public boolean isExist(Long id) {
    return getAirplaneById(id) != null;
  }

  @Override
  public List<Airplane> getAirplanesLike(Airplane seekingAirplane, int page, int size) {
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
    Criteria criteria = session.createCriteria(Airplane.class);

    if (seekingAirplane != null) {
      if (seekingAirplane.getId() != null) {
        criteria.add(Restrictions.eq("id", seekingAirplane.getId()));
      }
      if (seekingAirplane.getName() != null) {
        criteria.add(Restrictions.like("name", seekingAirplane.getName(), MatchMode.ANYWHERE));
      }
      if (seekingAirplane.getEconomySeatsCount() != null) {
        criteria.add(Restrictions.eq("economySeatsCount", seekingAirplane.getEconomySeatsCount()));
      }
      if (seekingAirplane.getBusinessSeatsCount() != null) {
        criteria
            .add(Restrictions.eq("businessSeatsCount", seekingAirplane.getBusinessSeatsCount()));
      }
    }

    criteria.addOrder(Order.asc("id"));
    criteria.setFirstResult(page * size);
    criteria.setMaxResults(size);

    List<Airplane> result = (List<Airplane>) criteria.list();
    session.getTransaction().commit();
    session.close();

    return result;
  }

  @Override
  public void updateAirplane(Long id, Airplane updatedAirplane) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    if (id != null && id != updatedAirplane.getId()) {
      updatedAirplane.setId(id);
    }
    session.update(updatedAirplane);

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void deleteAirplane(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    session.delete(getAirplaneById(id));

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public Airplane getAirplaneById(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Airplane where id=:id");
    query.setParameter("id", id);
    List list = query.list();

    session.getTransaction().commit();
    session.close();
    return list.size() > 0 ? (Airplane) list.get(0) : null;
  }

  @Override
  public List<Airplane> getAirplanes() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Airplane");
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return list.size() > 0 ? (List<Airplane>) list : null;
  }

  @Override
  public List<Airplane> getAirplanes(int page, int count) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Airplane getAirplaneByName(String name) {

    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Airplane where name = :name");
    query.setParameter("name", name);
    List list = query.list();

    Airplane airplane = list.isEmpty() ? null : (Airplane) list.get(0);

    session.getTransaction().commit();
    session.close();

    return airplane;
  }
}
