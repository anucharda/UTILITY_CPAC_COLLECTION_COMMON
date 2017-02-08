/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.logger;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Optimus
 */
public class LoggerTemplate {

  public LoggerTemplate(Logger logger) {
    this.logger = logger;
  }

  protected final Logger logger;

  protected long startTime;
  protected long startDebugTime;
  protected StringBuilder header;

  public void setHeaderLog(String header) {
    this.header = new StringBuilder(header);
  }

  public StringBuilder getHeaderLog() {
    return this.header;
  }

  public Logger getLogger() {
    return logger;
  }

  public void debug(String message) {
    if (isLoggerNotNull(message)) {
      logger.debug(messageLog(message));
    }
  }

  public void debug(String message, Throwable ex) {
    if (isLoggerNotNull(message)) {
      logger.debug(messageLog(message), ex);
    }
  }

  public void info(String message) {
    if (isLoggerNotNull(message)) {
      logger.info(messageLog(message));
    }
  }

  public void info(String message, Throwable ex) {
    if (isLoggerNotNull(message)) {
      logger.info(messageLog(message), ex);
    }
  }

  public void warn(String message) {
    if (isLoggerNotNull(message)) {
      logger.warn(messageLog(message));
    }
  }

  public void warn(String message, Throwable ex) {
    if (isLoggerNotNull(message)) {
      logger.warn(messageLog(message), ex);
    }
  }

  public void error(String message) {
    if (isLoggerNotNull(message)) {
      logger.error(messageLog(message));
    }
  }

  public void error(String message, Throwable ex) {
    if (isLoggerNotNull(message)) {
      logger.error(messageLog(message), ex);
    }
  }

  public void debug(ArrayList<BigDecimal> list) {
    if (list == null || list.isEmpty()) {
      logger.debug("parameter is null or empty");
      return;
    }
    for (int i = 0; i < list.size(); i++) {
      logger.debug(messageLog("param " + i + "=" + list.get(i).toPlainString()));
    }
  }

  /**
   * *
   * PRIVATE ......... *
   */
  private boolean isLoggerNotNull(String message) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    if (logger == null) {
      if (message == null) {
        System.out.println(sdf.format(new Date()) + ":" + header + ":-NULL");
      } else {
        System.out.println(sdf.format(new Date()) + ":" + header + ":-" + message);
      }
      return false;
    }
    return true;
  }

  private String messageLog(String message) {
    return header + "|" + message;
  }

}
