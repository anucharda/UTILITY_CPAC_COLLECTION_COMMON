/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import th.co.ais.cpac.cl.template.logger.LoggerTemplate;

/**
 *
 * @author OptimusPrime
 * @param <R>
 * @param <C>
 * @param <L>
 */
public abstract class DBTemplatePreparedStatementNoComplete<R extends DBTemplatesResponse,   L extends LoggerTemplate> extends DBTemplates<R, L> {

  public DBTemplatePreparedStatementNoComplete( L logger) {
    super( logger);
    setEnvDefault();
  }

  protected abstract void setParameter(PreparedStatement funcout) throws SQLException;

  protected abstract void setReturnValue(PreparedStatement funcout) throws SQLException;

  
  
  protected R executeQuery(Connection conn, boolean autoClose) {
    long startTime = System.currentTimeMillis();

    PreparedStatement stm = null;

    
    try {
      response.sqlProcess = createSqlProcess();

      if (isFail(conn) || isFail(response.sqlProcess)) {
        return response;
      }
      stm = conn.prepareCall(response.sqlProcess.toString());
      setParameter(stm);
      boolean execute = stm.execute();
      response.createResponse(false);
      response.recordCount = 0;
      setReturnValue(stm);
      if (response.recordCount == 0) {
        response.statusCode = DBTemplatesResponse.STATUS_DATA_NOT_FOUND;
      }
    } catch (SQLException e) {
      setValue(e);
      e.printStackTrace(System.out);
    } catch (Exception e) {
      setValue(e);
      e.printStackTrace(System.out);
    } finally {
      close(stm);
      if (autoClose) {
        close(conn);
      }
      response.responseTimeProcessSQL = System.currentTimeMillis() - startTime;
    }
    return response;
  }

  protected void countRecord() {
    response.recordCount++;
  }

}
