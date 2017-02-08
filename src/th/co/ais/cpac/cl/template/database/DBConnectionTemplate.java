package th.co.ais.cpac.cl.template.database;

import java.sql.Connection;
import java.sql.SQLException;
import th.co.ais.cpac.cl.template.configuration.CNFTemplate;
import th.co.ais.cpac.cl.template.logger.LoggerTemplate;

public abstract class DBConnectionTemplate<C extends CNFTemplate, L extends LoggerTemplate> {

  private static final int default_wait_get_connection[] = {0};

  protected Connection connection;
  protected long responseTime = -1;
  protected final C configuration;
  protected final L logger;
  protected int wait_get_connection[] = null;

  public DBConnectionTemplate(C configuration, L logger) {
    this.configuration = configuration;
    this.logger = logger;
  }

  public long getResponseTime() {
    return responseTime;
  }

  public void close() {
    try {
      if (connection == null) {
        return;
      }
      if (!connection.isClosed()) {
        connection.close();
      }
      connection = null;
    } catch (SQLException pe) {
      pe.printStackTrace(System.err);
    }
  }

  public boolean setManualCommit() {
    try {
      if (connection == null || connection.isClosed()) {
        return false;
      }
      connection.setAutoCommit(false);

    } catch (SQLException ex) {
      ex.printStackTrace(System.err);
      return false;
    }
    return true;
  }

  public boolean setAutoCommit() {
    try {
      if (connection == null || connection.isClosed()) {
        return false;
      }
      connection.setAutoCommit(true);
    } catch (SQLException ex) {
      ex.printStackTrace(System.err);
      return false;
    }
    return true;
  }

  public boolean commit() {
    try {
      connection.commit();
    } catch (SQLException ex) {
      ex.printStackTrace(System.err);
      return false;
    }
    return true;
  }

  public boolean isAutoCommit() {
    try {
      return connection.getAutoCommit();
    } catch (SQLException ex) {
      ex.printStackTrace(System.err);
    }
    return false;
  }

  public boolean rollback() {
    try {
      connection.rollback();
    } catch (SQLException ex) {
      ex.printStackTrace(System.err);
      return false;
    }
    return true;
  }

  public boolean ready() {
     
    try {
      return connection != null && !connection.isClosed();
    } catch (SQLException ex) {
      ex.printStackTrace(System.err);
    }
    return false;
  }

  protected abstract Connection buildConnection();

  protected int[] getWaitGetConnection() {
    return configuration.getArrayOfInteger("database.wait.get.connection", "\\|");
  }

  public Connection getConnection() {
    long start = System.currentTimeMillis();

    try {

      try {
        if (connection != null && !connection.isClosed()) {
          return connection;
        }
      } catch (SQLException ex) {
      }

      wait_get_connection = getWaitGetConnection();
      
      if(wait_get_connection == null || wait_get_connection.length < 1 ){
        wait_get_connection = default_wait_get_connection;
      }

      int retry = wait_get_connection.length;
 
      for (int i = 0; i < retry; i++) {
        //logger.debug("i="+i+",wait="+wait_get_connection[i]);
        if (wait_get_connection[i] > 0) {
          try {
            Thread.sleep(wait_get_connection[i]);
          } catch (InterruptedException ex) {
          }
        }
        connection = buildConnection();
        try {
          if (connection != null && !connection.isClosed()) {
            return connection;
          }
        } catch (SQLException ex) {
        }

      }
      return null;

    } finally {
      responseTime = System.currentTimeMillis() - start;
    }
  }

}
