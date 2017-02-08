/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.database;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import th.co.ais.cpac.cl.template.logger.LoggerTemplate;

public abstract class DBTemplatesInsert<R extends DBTemplatesResponse, L extends LoggerTemplate, D extends DBConnectionTemplate> extends DBTemplatesExecuteUpdate<R, L, D> {

  public DBTemplatesInsert(L logger) {
    super(logger);
  }

  protected void genString(String colName, String value, String defVal, StringBuilder col, StringBuilder val, boolean isAuto) {
    if (value == null) {
      if (isAuto) {
        if (col.length() > 0) {
          col.append(",");
          val.append(",");
        }
        if (defVal == null) {
          col.append(colName);
          val.append(ORACLE_STRING_NULL);
        } else {
          col.append(colName);
          val.append("'").append(prepareStrig(defVal)).append("'");
        }
      }
      return;
    }

    if (ORACLE_STRING_NULL.equalsIgnoreCase(value.trim())) {
      if (isAuto) {
        if (col.length() > 0) {
          col.append(",");
          val.append(",");
        }
        col.append(colName);
        val.append(ORACLE_STRING_NULL);
      }
      return;
    }

    if (value.trim().isEmpty()) {
      if (isAuto) {
        if (col.length() > 0) {
          col.append(",");
          val.append(",");
        }
        col.append(colName);
        val.append("''");
      }
      return;
    }
    if (col.length() > 0) {
      col.append(",");
      val.append(",");
    }
    col.append(colName);
    val.append("'").append(prepareStrig(value)).append("'");
  }

  protected String prepareStrig(String mmm) {
    if (mmm.contains("'")) {
      return mmm.replace("'", "''");
    }
    return mmm;
  }

  protected void genString(String colName, String value, StringBuilder col, StringBuilder val, boolean isAuto) {
    genString(colName, value, null, col, val, isAuto);
  }

  protected void genNumber(String colName, BigDecimal value, BigDecimal defVal, StringBuilder col, StringBuilder val, boolean isAuto) {
    if (value == null) {
      if (isAuto) {
        if (col.length() > 0) {
          col.append(",");
          val.append(",");
        }
        if (defVal == null) {
          col.append(colName);
          val.append(ORACLE_STRING_NULL);
        } else {
          col.append(colName);
          val.append(defVal.toPlainString());
        }
      }
      return;
    }
    if (col.length() > 0) {
      col.append(",");
      val.append(",");
    }
    col.append(colName);
    val.append(value.toPlainString());
  }

  protected void genNumber(String colName, Integer value, Integer defVal, StringBuilder col, StringBuilder val, boolean isAuto) {
    if (value == null) {
      if (isAuto) {
        if (col.length() > 0) {
          col.append(",");
          val.append(",");
        }
        if (defVal == null) {
          col.append(colName);
          val.append(ORACLE_STRING_NULL);
        } else {
          col.append(colName);
          val.append(defVal.intValue());
        }
      }
      return;
    }
    if (col.length() > 0) {
      col.append(",");
      val.append(",");
    }
    col.append(colName);
    val.append(value);
  }

  protected void genNumber(String colName, Integer value, StringBuilder col, StringBuilder val, boolean isAuto) {
    genNumber(colName, value, null, col, val, isAuto);
  }

  protected void genNumber(String colName, Long value, Long defVal, StringBuilder col, StringBuilder val, boolean isAuto) {
    if (value == null) {
      if (isAuto) {
        if (col.length() > 0) {
          col.append(",");
          val.append(",");
        }
        if (defVal == null) {
          col.append(colName);
          val.append(ORACLE_STRING_NULL);
        } else {
          col.append(colName);
          val.append(defVal.longValue());
        }
      }
      return;
    }
    if (col.length() > 0) {
      col.append(",");
      val.append(",");
    }
    col.append(colName);
    val.append(value.longValue());
  }

  protected void genNumber(String colName, Long value, StringBuilder col, StringBuilder val, boolean isAuto) {
    genNumber(colName, value, null, col, val, isAuto);
  }

  protected void genNumber(String colName, String value, Long defVal, StringBuilder col, StringBuilder val, boolean isAuto) {
    if (value == null) {
      if (isAuto) {
        if (col.length() > 0) {
          col.append(",");
          val.append(",");
        }
        if (defVal == null) {
          col.append(colName);
          val.append(ORACLE_STRING_NULL);
        } else {
          col.append(colName);
          val.append("").append(defVal.longValue()).append("");
        }
      }
      return;
    }

    if (ORACLE_STRING_NULL.equalsIgnoreCase(value.trim())) {
      if (isAuto) {
        if (col.length() > 0) {
          col.append(",");
          val.append(",");
        }
        col.append(colName);
        val.append(ORACLE_STRING_NULL);
      }
      return;
    }

    if (value.trim().isEmpty()) {
      if (isAuto) {
        if (col.length() > 0) {
          col.append(",");
          val.append(",");
        }
        col.append(colName);
        val.append("''");
      }
      return;
    }
    if (col.length() > 0) {
      col.append(",");
      val.append(",");
    }
    col.append(colName);
    val.append("").append(prepareStrig(value)).append("");

  }

  protected void genNumber(String colName, Double value, Double defVal, StringBuilder col, StringBuilder val, boolean isAuto) {
    if (value == null) {
      if (isAuto) {
        if (col.length() > 0) {
          col.append(",");
          val.append(",");
        }
        if (defVal == null) {
          col.append(colName);
          val.append(ORACLE_STRING_NULL);
        } else {
          col.append(colName);
          val.append(defVal.doubleValue());
        }
      }
      return;
    }
    if (col.length() > 0) {
      col.append(",");
      val.append(",");
    }
    col.append(colName);
    val.append(value.doubleValue());
  }

  protected void genDateTime(String colName, java.util.Date value, java.util.Date defVal, StringBuilder col, StringBuilder val, boolean isAuto) {
    if (value == null) {
      if (isAuto) {
        if (col.length() > 0) {
          col.append(",");
          val.append(",");
        }
        if (defVal == null) {
          col.append(colName);
          val.append(ORACLE_STRING_NULL);
        } else {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
          col.append(colName);
          val.append("CONVERT( datetime , '").append(sdf.format(defVal)).append("' , 121 )");
        }
      }
      return;
    }
    if (col.length() > 0) {
      col.append(",");
      val.append(",");
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    col.append(colName);
    val.append("CONVERT( datetime , '").append(sdf.format(value)).append("' , 121 )");
  }

  protected void genDateTime(String colName, java.util.Date value, StringBuilder col, StringBuilder val, boolean isAuto) {
    genDateTime(colName, value, null, col, val, isAuto);
  }

  protected void genMethod(String colName, String value, StringBuilder col, StringBuilder val, boolean isAuto) {

    if (value == null) {
      return;
    }
    if (value.trim().isEmpty()) {
      return;
    }
    if (ORACLE_STRING_NULL.equalsIgnoreCase(value.trim())) {
      if (isAuto) {
        if (col.length() > 0) {
          col.append(",");
          val.append(",");
        }
        col.append(colName);
        val.append(ORACLE_STRING_NULL);
      }
      return;
    }

    if (col.length() > 0) {
      col.append(",");
      val.append(",");
    }
    col.append(colName);
    val.append(value);
  }

}
/*
  protected void genDateTime(String colName, java.util.Date value, java.util.Date defVal, String format_value, String format_db, StringBuilder col, StringBuilder val, boolean isAuto) {
    if (value == null) {
      if (isAuto) {
        if (col.length() > 0) {
          col.append(",");
          val.append(",");
        }
        if (defVal == null) {
          col.append(colName);
          val.append(ORACLE_STRING_NULL);
        } else {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
          col.append(colName);
          
          // oracle val.append(" STR_TO_DATE ('").append(sdf.format(defVal)).append("','").append(format_db).append("')");
          val.append("CONVERT( datetime , "+sdf.format(defVal) +" , 121 )");
          val.append(" CONVERT (     '").append(sdf.format(defVal)).append("','").append(format_db).append("')");
        }
      }
      return;
    }
    if (col.length() > 0) {
      col.append(",");
      val.append(",");
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format_value);
    col.append(colName);
    val.append(" STR_TO_DATE ('").append(sdf.format(value)).append("','").append(format_db).append("')");
  }

 */
