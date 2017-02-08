/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.database;

import java.text.SimpleDateFormat;
import th.co.ais.cpac.cl.template.logger.LoggerTemplate;

public abstract class DBTemplatesUpdate<R extends DBTemplatesResponse, L extends LoggerTemplate, D extends DBConnectionTemplate> extends DBTemplatesExecuteUpdate<R, L, D> {

  public DBTemplatesUpdate(L logger) {
    super(logger);
  }

  protected void genString(String colName, String value, String defVal, StringBuilder sql, boolean isDefval) {
    if (value == null || ORACLE_STRING_NULL.equalsIgnoreCase(value.trim())) {
      if (isDefval) {
        if (defVal == null) {
          if (sql.length() > 0) {
            sql.append(",").append(colName).append(" = ").append(ORACLE_STRING_NULL);
          } else {
            sql.append(colName).append(" = ").append(ORACLE_STRING_NULL);
          }
        } else if (sql.length() > 0) {
          sql.append(", ").append(colName).append(" = '").append(defVal).append("' ");
        } else {
          sql.append(colName).append(" = '").append(defVal).append("' ");
        }
      }
      return;
    }
    if (value.trim().isEmpty()) {
      if (sql.length() > 0) {
        sql.append(", ").append(colName).append(" = ''");
      } else {
        sql.append(colName).append(" = ''");
      }
      return;
    }

    if (sql.length() > 0) {
      sql.append(", ").append(colName).append(" = '").append(value).append("' ");
    } else {
      sql.append(colName).append(" = '").append(value).append("' ");
    }

  }

  protected void genNumber(String colName, Integer value, Integer defVal, StringBuilder sql, boolean isDefval) {
    if (value == null) {
      if (isDefval) {
        if (defVal == null) {
          if (sql.length() > 0) {
            sql.append(",").append(colName).append(" = ").append(ORACLE_STRING_NULL);
          } else {
            sql.append(colName).append(" = ").append(ORACLE_STRING_NULL);
          }
        } else if (sql.length() > 0) {
          sql.append(", ").append(colName).append(" = ").append(defVal.intValue()).append(" ");
        } else {
          sql.append(colName).append(" = ").append(defVal.intValue()).append(" ");
        }
      }
    } else if (sql.length() > 0) {
      sql.append(", ").append(colName).append(" = ").append(value.intValue()).append(" ");
    } else {
      sql.append(colName).append(" = ").append(value.intValue()).append(" ");
    }
  }

  protected void genNumber(String colName, Long value, Long defVal, StringBuilder sql, boolean isDefval) {
    if (value == null) {
      if (isDefval) {
        if (defVal == null) {
          if (sql.length() > 0) {
            sql.append(",").append(colName).append(" = ").append(ORACLE_STRING_NULL);
          } else {
            sql.append(colName).append(" = ").append(ORACLE_STRING_NULL);
          }
        } else if (sql.length() > 0) {
          sql.append(", ").append(colName).append(" = ").append(defVal.longValue()).append(" ");
        } else {
          sql.append(colName).append(" = ").append(defVal.longValue()).append(" ");
        }
      }
    } else if (sql.length() > 0) {
      sql.append(", ").append(colName).append(" = ").append(value.longValue()).append(" ");
    } else {
      sql.append(colName).append(" = ").append(value.longValue()).append(" ");
    }
  }

  protected void genNumber(String colName, Double value, Double defVal, StringBuilder sql, boolean isDefval) {
    if (value == null) {
      if (isDefval) {
        if (defVal == null) {
          if (sql.length() > 0) {
            sql.append(",").append(colName).append(" = ").append(ORACLE_STRING_NULL);
          } else {
            sql.append(colName).append(" = ").append(ORACLE_STRING_NULL);
          }
        } else if (sql.length() > 0) {
          sql.append(", ").append(colName).append(" = ").append(defVal.doubleValue()).append(" ");
        } else {
          sql.append(colName).append(" = ").append(defVal.doubleValue()).append(" ");
        }
      }
    } else if (sql.length() > 0) {
      sql.append(", ").append(colName).append(" = ").append(value.doubleValue()).append(" ");
    } else {
      sql.append(colName).append(" = ").append(value.doubleValue()).append(" ");
    }
  }

  protected void genDate(String colName, java.util.Date value, java.util.Date defVal, String format_value, String format_db, StringBuilder sql, boolean isDefval) {
    if (value == null) {
      if (isDefval) {
        if (defVal == null) {
          if (sql.length() > 0) {
            sql.append(",").append(colName).append(" = ").append(ORACLE_STRING_NULL);
          } else {
            sql.append(colName).append(" = ").append(ORACLE_STRING_NULL);
          }

        } else {
          SimpleDateFormat sdf = new SimpleDateFormat(format_value);
          if (sql.length() > 0) {
            sql.append(",").append(colName).append(" = ").append(" STR_TO_DATE ('").append(sdf.format(defVal)).append("','").append(format_db).append("')");
          } else {
            sql.append(colName).append(" = ").append(" STR_TO_DATE ('").append(sdf.format(defVal)).append("','").append(format_db).append("')");
          }
        }
      }
    } else {
      SimpleDateFormat sdf = new SimpleDateFormat(format_value);
      if (sql.length() > 0) {
        sql.append(",").append(colName).append(" = ").append(" STR_TO_DATE ('").append(sdf.format(value)).append("','").append(format_db).append("')");
      } else {
        sql.append(colName).append(" = ").append(" STR_TO_DATE ('").append(sdf.format(value)).append("','").append(format_db).append("')");
      }
    }
  }

  protected void genMethod(String colName, String value, StringBuilder sql, boolean isAuto) {

    if (value == null) {
      return;
    }
    if (value.trim().isEmpty()) {
      return;
    }

    if (ORACLE_STRING_NULL.equalsIgnoreCase(value.trim())) {
      return;
    }

    if (sql.length() > 0) {
      sql.append(", ").append(colName).append(" = ").append(value).append(" ");
    } else {
      sql.append(colName).append(" = ").append(value).append(" ");
    }
  }

}
