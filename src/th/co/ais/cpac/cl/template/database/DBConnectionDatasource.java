/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.database;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import th.co.ais.cpac.cl.template.configuration.CNFTemplate;
import th.co.ais.cpac.cl.template.logger.LoggerTemplate;

/**
 *
 * @author Optimus
 * @param <C>
 * @param <L>
 */
public class DBConnectionDatasource<C extends CNFTemplate, L extends LoggerTemplate> extends DBConnectionTemplate<C, L> {

  public static DataSource ds;

  public DBConnectionDatasource(C configuration, L logger) {
    super(configuration, logger);
  }

  @Override
  protected Connection buildConnection() {
    try {
      if (ds == null) {
        Context envContext = (Context) new InitialContext().lookup("java:/comp/env");
        ds = (DataSource) envContext.lookup(configuration.get("database.datasource.name", "jdbc/UCPPool"));
        envContext.close();
      }
      return ds.getConnection();
    } catch (NamingException ex) {
    } catch (SQLException ex) {
    }
    return null;
  }

}
