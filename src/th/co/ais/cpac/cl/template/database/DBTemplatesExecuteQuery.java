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
import java.util.ArrayList;
import th.co.ais.cpac.cl.template.logger.LoggerTemplate;

public abstract class DBTemplatesExecuteQuery<R extends DBTemplatesResponse, L extends LoggerTemplate, D extends DBConnectionTemplate> extends DBTemplates<R, L> {

  public DBTemplatesExecuteQuery(L logger) {
    super(logger);
    setEnvDefault();
  }

  protected abstract void setReturnValue(ResultSet rs) throws SQLException;

  protected R executeQuery(D connection, boolean autoClose) {
    long startTime = System.currentTimeMillis();

    Statement stm = null;
    ResultSet rs = null;
    Connection conn = null;
    try {
      if (isFail(connection)) {
        return response;
      }
      conn = connection.getConnection();
      response.responseTimeGetConnection = connection.getResponseTime();

      response.sqlProcess = createSqlProcess();

      if (isFail(conn) || isFail(response.sqlProcess)) {
        return response;
      }
      stm = conn.createStatement();
      rs = stm.executeQuery(response.sqlProcess.toString());
      response.recordCount = 0;
      //System.out.println("xxxxxxxxxxxxxxx ");
      while (rs.next()) {
        //System.out.println("xxxxxxxxxxxxxxx : " + response.recordCount);
        response.createResponse(false);
        setReturnValue(rs);
        response.recordCount++;
      }
      if (response.recordCount == 0) {
        response.errorMsg = "Data not found.";
        response.statusCode = DBTemplatesResponse.STATUS_DATA_NOT_FOUND;
      }
    } catch (SQLException e) {
      setValue(e);
      logger.debug("e", e);
    } catch (Exception e) {
      setValue(e);
      logger.debug("e1", e);
      //e.printStackTrace(System.err);
    } finally {
      close(stm);
      close(rs);
      if (autoClose) {
        close(conn);
      }
      response.responseTimeProcessSQL = System.currentTimeMillis() - startTime;
    }
    return response;
  }


}
