package com.epam.aviasales.util;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.Role;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j
public class AuthHelper {

  public static boolean isAllowedUser(HttpServletRequest req, HttpServletResponse resp, List<Role> allowedRoles,
      String servletAddress) throws IOException {
    try {
      HttpSession session = req.getSession();
      Account account = (Account) session.getAttribute("account");
      if (account == null || !allowedRoles.contains(account.getRole())) {
        log.info(String.format("[%s] %s tried to [%s] at %s", req.getRemoteAddr(),
            account == null ? "unknown user" : account.toString(),
            req.getMethod(), servletAddress));
        resp.sendError(403);
        return false;
      }
      return true;
    } catch (Exception e) {
      log.error(e);
      resp.sendError(500);
      return false;
    }
  }
}
