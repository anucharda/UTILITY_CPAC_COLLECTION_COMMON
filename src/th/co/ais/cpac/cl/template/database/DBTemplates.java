/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import th.co.ais.cpac.cl.template.configuration.Constants;
import th.co.ais.cpac.cl.template.logger.LoggerTemplate;

public abstract class DBTemplates<R extends DBTemplatesResponse, L extends LoggerTemplate> {

  protected L logger;
  //protected C configurtion;

  public DBTemplates(L logger) {
    this.logger = logger;
    //this.configurtion = configurtion;
  }

  protected static final String ORACLE_STRING_NULL = "NULL";

  protected abstract R createResponse();

  protected abstract StringBuilder createSqlProcess();

  protected R response;

  protected void setEnvDefault() {
    response = createResponse();
    response.statusRun = true;
    response.recordCount = -1;
    response.statusCode = DBTemplatesResponse.STATUS_COMPLETE;
    response.errorMsg = "complete";
    response.sqlState = "";
    response.sqlErrorCode = 0;
  }

  protected boolean isFail(DBConnectionTemplate db) {
    if (db == null) {
      response.statusRun = false;
      response.recordCount = -1;
      response.statusCode = DBTemplatesResponse.STATUS_CONNECTION_NULL;
      response.errorMsg = "Connection is null.";
      return true;
    }
    return false;
  }

  protected boolean isFail(Connection conn) {
    if (conn == null) {
      response.statusRun = false;
      response.recordCount = -1;
      response.statusCode = DBTemplatesResponse.STATUS_CONNECTION_NULL;
      response.errorMsg = "Connection is null.";
      return true;
    }
    try {
      //System.out.println("mmmmmm");
      if (conn.isClosed()) {
        //System.out.println(" connection is close");
        response.statusRun = false;
        response.recordCount = -1;
        response.statusCode = DBTemplatesResponse.STATUS_CONNECTION_CLOSE;
        response.errorMsg = "Connection is closed.";
        return true;
      }
    } catch (SQLException ex) {
      setValue(ex);
      return true;
    }
    return false;
  }

  protected boolean isFail(StringBuilder sql) {
    if (sql == null || 0 == sql.length()) {
      response.statusRun = false;
      response.recordCount = -1;
      response.statusCode = DBTemplatesResponse.STATUS_SQL_NULL;
      response.errorMsg = "SQL is null or empty.";
      return true;
    }
    return false;
  }

  protected void setValue(SQLException e) {
    response.statusRun = false;
    response.recordCount = -1;
    response.statusCode = DBTemplatesResponse.STATUS_ERROR;
    response.sqlErrorCode = e.getErrorCode();
    response.sqlState = e.getSQLState();
    response.errorMsg = "[ErrorCode:" + e.getErrorCode() + "][SQLState:" + e.getSQLState() + "][SQLExecption:" + e.toString() + "]";
  }

  protected void setValue(Exception e) {
    response.statusRun = false;
    response.recordCount = -1;
    response.statusCode = DBTemplatesResponse.STATUS_ERROR;
    response.errorMsg = "[Exception:" + e.toString() + "]";
  }

  protected void close(ResultSet rs) {
    try {
      if (rs == null) {
        return;
      }
      rs.close();
      rs = null;
    } catch (SQLException pe) {

    }
  }

  protected void close(Statement stm) {
    try {
      if (stm == null) {
        return;
      }
      stm.close();
      stm = null;
    } catch (SQLException pe) {

    }
  }

  protected void close(Connection conn) {
    try {
      if (conn == null) {
        return;
      }
      conn.close();
      conn = null;
    } catch (SQLException pe) {

    }
  }

  protected String toString(Constants.Language co, Constants.Language defval) {
    if (co == null) {
      if (defval == null) {
        return null;
      }
      return defval.getCodeAsString();
    }
    return co.getCodeAsString();
  }

  protected Long toLong(BigDecimal co, Long defval) {
    if (co == null) {
      return defval;
    }
    return co.longValue();
  }

  protected Long toLong(java.sql.Date date, String format, Long defVal) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try {
      return Long.parseLong(sdf.format(toDate(date, null)));
    } catch (Exception ex) {

    }
    return defVal;
  }

  protected Long toLong(java.sql.Timestamp date, String format, Long defVal) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try {
      return Long.parseLong(sdf.format(toDate(date, null)));
    } catch (Exception ex) {

    }
    return defVal;
  }

  protected Integer toInteger(BigDecimal co, Integer defval) {
    if (co == null) {
      return defval;
    }
    return co.intValue();
  }

  protected Integer toInteger(String co, Integer defval) {
    if (co != null) {
      try {
        return new Integer(co);
      } catch (Exception ex) {

      }
    }
    return defval;
  }

  protected Double toDouble(BigDecimal co, Double defval) {
    if (co == null) {
      return defval;
    }
    return co.doubleValue();
  }

  protected String toString(BigDecimal co, Long defval) {
    if (co == null) {
      if (defval == null) {
        return null;
      }
      return "" + defval;
    }
    return "" + co.longValue();
  }

  protected java.util.Date toDate(java.sql.Date d1, java.util.Date defval) {
    if (d1 == null) {
      return defval;
    }
    return new java.util.Date(d1.getTime());
  }

  protected java.util.Date toDate(java.sql.Timestamp d1, java.util.Date defval) {
    if (d1 == null) {
      return defval;
    }
    return new java.util.Date(d1.getTime());
  }

  protected java.sql.Date toSqlDate(java.util.Date d1, java.sql.Date defval) {
    if (d1 == null) {
      return defval;
    }
    return new java.sql.Date(d1.getTime());
  }

  protected java.sql.Timestamp toSqlTimestamp(java.util.Date d1, java.sql.Timestamp defval) {
    if (d1 == null) {
      return defval;
    }
    return new java.sql.Timestamp(d1.getTime());
  }

  protected String toString(java.sql.Timestamp d1, String format, String def) {
    if (d1 == null) {
      return def;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try {
      return sdf.format(new java.util.Date(d1.getTime()));
    } catch (Exception ex) {
    }
    return def;
  }

  protected String toString(java.util.Date d1, String format, String def) {
    if (d1 == null) {
      return def;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try {
      return sdf.format(new java.util.Date(d1.getTime()));
    } catch (Exception ex) {
    }
    return def;
  }

  protected char toChar(String co, char defval) {
    if (co == null || co.trim().isEmpty()) {
      return defval;
    }
    return co.trim().charAt(0);
  }

  protected Character toCharcter(String co, Character defVal) {
    if (co == null || co.isEmpty() || co.length() != 1) {
      return defVal;
    }
    return co.charAt(0);
  }

  protected String arrayListStringToStringQuery(ArrayList<String> data) {
    if (data == null || data.isEmpty()) {
      return null;
    }
    String condition = "";
    for (int i = 0; i < data.size(); i++) {
      if (i == 0) {
        condition = "'" + data.get(i) + "'";
      } else {
        condition += ",";
        condition += "'" + data.get(i) + "'";
      }

    }
    return condition;
  }

  protected String arrayListBigDecimalToStringQuery(ArrayList<BigDecimal> data) {
    if (data == null || data.isEmpty()) {
      return null;
    }
    String condition = "";
    for (int i = 0; i < data.size(); i++) {
      if (i == 0) {
        condition = "'" + data.get(i).toPlainString() + "'";
      } else {
        condition += ",";
        condition += "'" + data.get(i).toPlainString() + "'";
      }

    }
    return condition;
  }

}
