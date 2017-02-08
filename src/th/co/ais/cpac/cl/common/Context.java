/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.common;

/**
 *
 * @author Optimus
 */
public class Context {

  protected UtilityJossDataGrid dataGrid;
  protected UtilityLogger logger;

  public Context() {
  }

  public void initailLogger(String name, String header) {
    logger = new UtilityLogger(name);
    logger.setHeaderLog(header);
  }

  public void initailDataGrid(String name) {
    dataGrid = new UtilityJossDataGrid(name);
  }

  public UtilityJossDataGrid getDataGrid() {
    return dataGrid;
  }

  public UtilityLogger getLogger() {
    return logger;
  }

  public Context clone(String header) {
    Context ctx = new Context();
    String tempHeader = this.logger.getHeaderLog().toString();
    tempHeader += ":";
    tempHeader += header;
    ctx.initailLogger("LoggerMasterBatchInfo", tempHeader);
    return ctx;
  }

  public static void main(String[] args) {
    Context context = new Context();
    context.initailLogger("xxxxx", "xxxxxxx|xxxxxxxxxx|yyyyyyyyyyy");
    context.initailDataGrid("dataCache1");

    context.getLogger().debug("start");

  }

}
