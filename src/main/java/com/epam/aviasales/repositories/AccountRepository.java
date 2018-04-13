package com.epam.aviasales.repositories;

import com.epam.aviasales.exceptions.LoginNotExistException;
import com.epam.aviasales.domain.Account;
import com.epam.aviasales.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AccountRepository {
  private static AccountRepository instance;

  private AccountRepository() {}

  public void insert(Account account) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    session.beginTransaction();

    session.save(account);
    session.getTransaction().commit();

    session.close();
  }

  public Account get(String login){
    Session session = HibernateUtil.getSessionFactory().openSession();

    Query query = session.createQuery("from Account WHERE login = :login");
    query.setParameter("login", login);

    List list = query.list();
    return !list.isEmpty() ? (Account) list.get(0) : null;
  }

  public static synchronized AccountRepository getInstance() {
    if (instance == null) {
      instance = new AccountRepository();
    }
    return instance;
  }
}
