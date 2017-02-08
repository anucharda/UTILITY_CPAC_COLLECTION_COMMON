/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.database;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
import th.co.ais.cpac.cl.template.configuration.CNFTemplate;
import th.co.ais.cpac.cl.template.logger.LoggerTemplate;

/**
 *
 * @author Optimus
 * @param <C>
 * @param <L>
 */
public class DBConnectionPools<C extends CNFTemplate, L extends LoggerTemplate> extends DBConnectionTemplate<C, L> {

  private static BasicDataSource ds;

  public DBConnectionPools(C configuration, L logger) {
    super(configuration, logger);
  }

  @Override
  protected Connection buildConnection() {
    createPools();
    try {
      return ds.getConnection();
    } catch (SQLException ex) {
      logger.error("", ex);
    }
    return null;
  }

  /**
   * dbcp 2 *
   */
  private void createPools() {
    if (ds == null) {
      //logger.debug("create pools ....... ");
      ds = new BasicDataSource();

      ds.setDriverClassName(configuration.get("database.driver", "oracle.jdbc.driver.OracleDriver"));
      ds.setUsername(configuration.get("database.user", "gbeadaptor"));
      ds.setPassword(configuration.get("database.password", "pass1234"));
      ds.setUrl(configuration.get("database.url", "jdbc:oracle:thin:@127.0.0.1:1521:xe"));

      // the settings below are optional -- dbcp can work with defaults
      ds.setInitialSize(configuration.get("database.pool.initial.size", 100));
      ds.setMinIdle(configuration.get("database.pool.min.idle", 100));
      ds.setMaxIdle(configuration.get("database.pool.max.idle", 100));
      ds.setMaxTotal(configuration.get("database.pool.max.total", 100));
      ds.setMaxWaitMillis(configuration.get("database.pool.max.wait.millis", 100L * 3L));
      ds.setMaxOpenPreparedStatements(configuration.get("database.pool.max.open.prepared.statements", 180));

      ds.setTestOnCreate(true);
      ds.setTestOnBorrow(true);
      ds.setTestOnReturn(false);
      ds.setValidationQuery("select 1");

    }
  }

  public String connectionReady() {
    if (connection == null) {
      return "connection is null";
    }
    try {
      if (connection.isClosed()) {
        return "connection is close";
      }
    } catch (SQLException ex) {
      //ex.printStackTrace(System.out);
      return ex.getLocalizedMessage();
    }
    return null;
  }

  public void buildeDataSource() {
    createPools();
  }

  public void closeDataSource() {
    if (ds != null) {
      try {
        ds.close();
        ds = null;
      } catch (SQLException ex) {
      }
    }
  }

  public boolean poolActive() {
    if (ds == null) {
      return false;
    }
    return true;
  }
}
