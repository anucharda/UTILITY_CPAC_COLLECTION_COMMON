package th.co.ais.cpac.cl.template.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import th.co.ais.cpac.cl.template.logger.LoggerTemplate;

public abstract class DBTemplatesExecuteUpdate<R extends DBTemplatesResponse, L extends LoggerTemplate, D extends DBConnectionTemplate> extends DBTemplates<R, L> {

  public DBTemplatesExecuteUpdate(L logger) {
    super(logger);
    setEnvDefault();
  }

  protected R executeUpdate(D connection, boolean autoClose) {
    long startTime = System.currentTimeMillis();
    Statement stm = null;
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
      response.recordCount = stm.executeUpdate(response.sqlProcess.toString());
      if (response.recordCount == 0) {
        response.statusCode = DBTemplatesResponse.STATUS_DATA_NOT_FOUND;
      }

    } catch (SQLException e) {
      setValue(e);
    } finally {
      close(stm);
      if (autoClose) {
        close(conn);
      }
      response.responseTimeProcessSQL = System.currentTimeMillis() - startTime;
    }
    return response;
  }

  protected R executeUpdateGetIdentity(D connection, boolean autoClose) {
    long startTime = System.currentTimeMillis();
    Statement stm = null;
    ResultSet objResultSet = null;
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
      response.recordCount = stm.executeUpdate(response.sqlProcess.toString(), Statement.RETURN_GENERATED_KEYS);

      if (response.recordCount == 0) {
        response.statusCode = DBTemplatesResponse.STATUS_DATA_NOT_FOUND;
      } else {
        objResultSet = stm.getGeneratedKeys();
        if (objResultSet.next()) {
          response.setIdentity(objResultSet.getBigDecimal(1));
        }
      }
    } catch (SQLException e) {
      setValue(e);
    } finally {
      close(objResultSet);
      close(stm);
      if (autoClose) {
        close(conn);
      }
      response.responseTimeProcessSQL = System.currentTimeMillis() - startTime;
    }
    return response;
  }
}
