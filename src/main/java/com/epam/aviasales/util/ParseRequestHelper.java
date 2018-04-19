package com.epam.aviasales.util;

import javax.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Util class for working with <code>HttpServletRequest</code>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParseRequestHelper {

  public static Action getRequestAction(HttpServletRequest req) {
    Action action = Action.NONE;
    String actionAdd = req.getParameter("actionAdd");
    String actionSave = req.getParameter("actionSave");
    String actionDelete = req.getParameter("actionDelete");
    if (actionAdd != null && !actionAdd.isEmpty()) {
      action = Action.ADD;
    } else if (actionSave != null && !actionSave.isEmpty()) {
      action = Action.SAVE;
    } else if (actionDelete != null && !actionDelete.isEmpty()) {
      action = Action.DELETE;
    }
    return action;
  }

  public static int getParameterOrDefaultValue(HttpServletRequest req, String parameter,
      final int DEFAULT) {
    String paramStr = req.getParameter(parameter);
    return paramStr == null ? DEFAULT : Integer.parseInt(paramStr);
  }

  public static void setExistingParametersAsAttributes(HttpServletRequest request,
      String[] parameters) {
    for (String parameter : parameters) {
      String parameterValue = request.getParameter(parameter);
      if (parameterValue != null && !parameterValue.trim().isEmpty()) {
        request.setAttribute(parameter, parameterValue);
      }
    }
  }

  public static void setPagingURLAttributes(HttpServletRequest req, int page) {
    String reqParameters = req.getQueryString();
    if (reqParameters == null) {
      reqParameters = "?";
    } else {
      reqParameters = "?" + reqParameters;
    }
    String prevPage;
    String currPage;
    String nextPage;
    if (reqParameters.contains("page")) {
      prevPage = reqParameters.replaceFirst("page=[^&]*", "page=" + (page - 1));
      currPage = reqParameters;
      nextPage = reqParameters.replaceFirst("page=[^&]*", "page=" + (page + 1));
    } else {
      prevPage = reqParameters + "&page=" + (page - 1);
      currPage = reqParameters + "&page=" + (page);
      nextPage = reqParameters + "&page=" + (page + 1);
    }

    req.setAttribute("prevPageURL", prevPage);
    req.setAttribute("currPageURL", currPage);
    req.setAttribute("nextPageURL", nextPage);
  }

  private boolean isNullOrEmpty(String parameter) {
    return parameter == null || parameter.isEmpty();
  }
}
