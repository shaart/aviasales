package com.epam.aviasales.controllers;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.services.AccountService;
import com.epam.aviasales.util.HibernateUtil;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

public class UserProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        AccountService accountService = AccountService.getInstance();
        Account account1 = accountService.getAccount("johnsmith");

        req.setAttribute("type", account1.getType());
        req.setAttribute("name", account1.getName());
        req.setAttribute("login", account1.getLogin());
        req.setAttribute("email", account1.getEmail());
        req.setAttribute("phone", account1.getPhone());

        req.getRequestDispatcher("UserProfile.jsp").forward(req, resp);

    }
}
