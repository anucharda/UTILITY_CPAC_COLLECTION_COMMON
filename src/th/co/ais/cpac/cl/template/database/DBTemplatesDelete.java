/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.database;

import th.co.ais.cpac.cl.template.logger.LoggerTemplate;

public abstract class DBTemplatesDelete<R extends DBTemplatesResponse, L extends LoggerTemplate, D extends DBConnectionTemplate> extends DBTemplatesExecuteUpdate<R, L, D> {

  public DBTemplatesDelete(L logger) {
    super(logger);
  }

}
