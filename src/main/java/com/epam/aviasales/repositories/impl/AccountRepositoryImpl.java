package com.epam.aviasales.repositories.impl;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.repositories.AccountRepository;
import com.epam.aviasales.util.HibernateUtil;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountRepositoryImpl implements AccountRepository {

  private static volatile AccountRepository instance;

  public static AccountRepository getInstance() {
    AccountRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (AccountRepositoryImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new AccountRepositoryImpl();
        }
      }
    }

    return localInstance;
  }

  @Override
  public void addAccount(Account account) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.save(account);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public Account getAccountById(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("from Account WHERE id = :id");
    List list = query.setParameter("id", id).list();
    session.close();
    return list.size() > 0 ? (Account) list.get(0) : null;
  }

  @Override
  public List<Account> getAccountByLogin(String login) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("from Account WHERE login = :login");
    List list = query.setParameter("login", login).list();
    session.close();
    return list;
  }

  @Override
  public List<Account> getAccounts() {

    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("from Account");
    List list = query.list();

    session.getTransaction().commit();
    session.close();

    return (List<Account>) list;
  }

  @Override
  public List<Account> getAccounts(int page, int count) {
    throw new UnsupportedOperationException();
  }

  /*ToDo there is no check for row. Add it!, This is a bad way to choose the row*/
  public boolean isExist(String rowName, String rowValue) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("from Account WHERE " + rowName + " = :parameter");
    query.setParameter("parameter", rowValue);
    List list = query.list();
    session.close();
    return !list.isEmpty();
  }

  public void updateAccountPasswordById(Long id, String password) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    Query query =
        session.createQuery(
            "update Account set password = :password where id = :id");
    query.setParameter("password", password);
    query.setParameter("id", id);
    query.executeUpdate();
    transaction.commit();
    session.close();
  }

  public void updateAccount(Account account) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();
    Query query =
        session.createQuery(
            "update Account set login = :login, name = :name, email = :email, phone = :phone where id = :id");
    query.setParameter("login", account.getLogin());
    query.setParameter("name", account.getName());
    query.setParameter("email", account.getEmail());
    query.setParameter("phone", account.getPhone());
    query.setParameter("id", account.getId());
    query.executeUpdate();
    transaction.commit();
    session.close();
  }

  @Override
  public List<Account> getAccountsLike(Account seekingAccount, int page, int size) {
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
    Criteria criteria = session.createCriteria(Account.class);

    if (seekingAccount != null) {
      if (seekingAccount.getId() != null) {
        criteria.add(Restrictions.eq("id", seekingAccount.getId()));
      }
      if (seekingAccount.getRole() != null) {
        criteria.add(Restrictions.eq("role", seekingAccount.getRole()));
      }
      if (seekingAccount.getName() != null) {
        criteria.add(Restrictions.like("name", seekingAccount.getName(), MatchMode.ANYWHERE));
      }
      if (seekingAccount.getLogin() != null) {
        criteria.add(Restrictions.like("login", seekingAccount.getLogin(), MatchMode.ANYWHERE));
      }
      if (seekingAccount.getEmail() != null) {
        criteria.add(Restrictions.like("email", seekingAccount.getEmail(), MatchMode.ANYWHERE));
      }
      if (seekingAccount.getPhone() != null) {
        criteria.add(Restrictions.like("phone", seekingAccount.getPhone(), MatchMode.ANYWHERE));
      }
    }

    criteria.addOrder(Order.asc("id"));
    criteria.setFirstResult(page * size);
    criteria.setMaxResults(size);

    List<Account> result = (List<Account>) criteria.list();
    session.getTransaction().commit();
    session.close();

    return result;
  }

  @Override
  public void updateAccount(Long id, Account updatedAccount) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    if (id != null && id != updatedAccount.getId()) {
      updatedAccount.setId(id);
    }
    updatedAccount.setPassword(getAccountById(id).getPassword());

    session.update(updatedAccount);

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void deleteAccount(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    Query query = session.createQuery("delete Ticket where account.id = :id");
    query.setParameter("id", id);
    int result = query.executeUpdate();
    query = session.createQuery("delete Account where id = :id");
    query.setParameter("id", id);
    result = query.executeUpdate();

    session.getTransaction().commit();
    session.close();
  }
}
