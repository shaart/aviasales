package com.epam.aviasales.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SingOutServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if(req.getSession().getAttribute("language")==null){
      req.getSession().invalidate();
      resp.sendRedirect("/");
    } else{
      String language = req.getSession().getAttribute("language").toString();
      req.getSession().invalidate();
      req.getSession().setAttribute("language",language);
      resp.sendRedirect("/");
    }
  }
}
