/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.database;

import java.math.BigDecimal;

public abstract class DBTemplatesResponse<T> {

  protected DBTemplatesResponse() {

  }

  protected abstract T createResponse();

  protected T response;

  public static final int STATUS_COMPLETE = 0;
  public static final int STATUS_DATA_NOT_FOUND = 1;
  public static final int STATUS_CONNECTION_CLOSE = -1;
  public static final int STATUS_CONNECTION_NULL = -2;
  public static final int STATUS_SQL_NULL = -3;
  public static final int STATUS_ERROR = -9;

  protected int statusCode = STATUS_COMPLETE;
  protected String errorMsg = "";
  protected int recordCount = -1;
  protected String sqlState = "";
  protected int sqlErrorCode = 0;

  protected StringBuilder sqlProcess;
  protected boolean statusRun = true;

  protected long responseTimeProcessSQL = -1;
  protected long responseTimeGetConnection = -1;

  protected BigDecimal identity;

  public StringBuffer info(String END_LINE) {
    //System.out.println("END_LINE:"+END_LINE);

    if (END_LINE == null) {
      return info();
    }

    StringBuffer text = new StringBuffer();
    text.append(END_LINE);
    text.append("SQL           :").append(sqlProcess);
    text.append(END_LINE);
    text.append("RECORD        : ").append(recordCount);
    text.append(END_LINE);
    text.append("STATUS        : ").append(statusRun);
    text.append(END_LINE);
    text.append("ERROR CODE    : ").append(statusCode);
    text.append(END_LINE);
    text.append("ERROR MESSAGE : ").append(errorMsg);
    text.append(END_LINE);
    text.append("DB CONNECTION : ").append(responseTimeGetConnection);
    text.append(END_LINE);
    text.append("PROCESS TIME  : ").append(responseTimeProcessSQL);
    return text;
  }

  public StringBuffer info() {
    StringBuffer text = new StringBuffer();
    text.append("[Monitor]:");
    text.append("DB CONNECTION:[").append(responseTimeGetConnection).append("],");
    text.append("PROCESS TIME:[").append(responseTimeProcessSQL).append("],");
    text.append("RECORD:[").append(recordCount).append("],");
    text.append("STATUS:[").append(statusRun).append("],");
    text.append("ERROR CODE:[").append(statusCode).append("],");
    text.append("ERROR MESSAGE:[").append(errorMsg).append("],");
    text.append("SQL:[").append(sqlProcess).append("]");
    return text;
  }

  public StringBuffer info(String key, String endline) {
    StringBuffer text = new StringBuffer();
    text.append("[Monitor]:");
    text.append("DB CONNECTION:[").append(responseTimeGetConnection).append("],");
    text.append("PROCESS TIME:[").append(responseTimeProcessSQL).append("],");
    text.append("RECORD:[").append(recordCount).append("],");
    text.append("STATUS:[").append(statusRun).append("],");
    text.append("ERROR CODE:[").append(statusCode).append("],");
    text.append("ERROR MESSAGE:[").append(errorMsg).append("],");
    text.append("SQL:[").append(sqlProcess).append("]");
    return text;
  }

  public boolean isConnectionError() {
    switch (statusCode) {
      case STATUS_CONNECTION_CLOSE:
      case STATUS_CONNECTION_NULL:
        return true;
    }
    return false;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public int getRecordCount() {
    return recordCount;
  }

  public StringBuilder getSqlProcess() {
    return sqlProcess;
  }

  public boolean getStatusRun() {
    return statusRun;
  }

  public String getSqlState() {
    return sqlState;
  }

  public int getSqlErrorCode() {
    return sqlErrorCode;
  }

  public T getResponse() {
    return response;
  }

  public void setResponse(T t) {
    this.response = t;
  }

  public BigDecimal getIdentity() {
    return identity;
  }

  public void setIdentity(BigDecimal identity) {
    this.identity = identity;
  }

  protected void getResponse(T resp) {
    response = resp;
  }

  protected void createResponse(boolean allcreate) {
    if (allcreate) {
      response = createResponse();
    } else if (response == null) {
      response = createResponse();
    }
  }

  protected void set(int statusCode, String errorMsg) {
    this.statusCode = statusCode;
    this.errorMsg = errorMsg;
    this.statusRun = false;
  }

}
