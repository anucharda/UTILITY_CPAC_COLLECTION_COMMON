/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import th.co.ais.cpac.cl.template.logger.LoggerTemplate;

public abstract class DBTemplateCallableStatement<R extends DBTemplatesResponse, L extends LoggerTemplate, D extends DBConnectionTemplate> extends DBTemplates<R, L> {

  protected abstract void setParameter() throws SQLException;

  protected abstract void setReturnValue() throws SQLException;

  protected ResultSet resultSet = null;
  protected CallableStatement statement = null;

  public DBTemplateCallableStatement(L logger) {
    super(logger);
    setEnvDefault();
  }

  protected R executeQuery(D connection, boolean autoClose) {
    long startTime = System.currentTimeMillis();
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
      statement = conn.prepareCall(response.sqlProcess.toString());
      setParameter();

      resultSet = statement.executeQuery();

      response.createResponse(false);
      response.recordCount = 0;
      setReturnValue();

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
      close(resultSet);
      close(statement);
      if (autoClose) {
        close(conn);
      }
      response.responseTimeProcessSQL = System.currentTimeMillis() - startTime;
    }
    return response;
  }

  protected R execute(D connection, boolean autoClose) {
    long startTime = System.currentTimeMillis();
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
      statement = conn.prepareCall(response.sqlProcess.toString());
      setParameter();

      boolean execute = statement.execute();
      response.createResponse(false);
      response.recordCount = 0;
      setReturnValue();
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
      close(resultSet);
      close(statement);
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
