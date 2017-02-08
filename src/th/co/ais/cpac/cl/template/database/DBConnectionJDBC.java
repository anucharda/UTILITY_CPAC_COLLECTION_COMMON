/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import th.co.ais.cpac.cl.template.configuration.CNFTemplate;
import th.co.ais.cpac.cl.template.logger.LoggerTemplate;

/**
 *
 * @author Optimus
 * @param <C>
 * @param <L>
 */
public class DBConnectionJDBC<C extends CNFTemplate, L extends LoggerTemplate> extends DBConnectionTemplate<C, L> {

  public DBConnectionJDBC(C configuration, L logger) {
    super(configuration, logger);
  }

  @Override
  protected Connection buildConnection() {
    try {
      Class.forName(configuration.get("database.driver", "com.mysql.jdbc.Driver"));
      return connection = DriverManager.getConnection(
        configuration.get("database.url", "jdbc:mysql://localhost/refillgwdb?characterEncoding=UTF-8&useSSL=false"),
        configuration.get("database.user", "root"),
        configuration.get("database.password", "@A22FA2T4!3lrt1sdf")
      );
    } catch (ClassNotFoundException ex) {
    } catch (SQLException ex) {
    }
    return null;
  }

}
