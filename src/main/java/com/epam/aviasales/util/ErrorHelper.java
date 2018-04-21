package com.epam.aviasales.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

/**
 * Util class for working with <code>Errors</code>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j
public class ErrorHelper {

  public static void redirectToErrorPage(HttpServletRequest req, HttpServletResponse resp,
      Exception e, String previousPage) {
    try {
      log.error(e.getCause(), e);
      req.setAttribute("error", e.getMessage());
      req.setAttribute("previousPage", previousPage);
      req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
    } catch (Exception ex) {
      log.error(ex);
    }
    return;
  }
}
