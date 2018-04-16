package com.epam.aviasales.controllers;

import com.epam.aviasales.services.TicketService;
import com.epam.aviasales.services.impl.TicketServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConfirmationServlet extends HttpServlet {

    private TicketService ticketService;

    @Override
    public void init() throws ServletException {
        ticketService = TicketServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("confirmation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

    }
}
