/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.common;

import java.io.File;
import org.apache.log4j.PropertyConfigurator;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMessages_es;

import th.co.ais.cpac.cl.template.logger.LoggerTemplate;

/**
 *
 * @author Optimus
 */
public class UtilityLogger extends LoggerTemplate {

  static {
    //initailLogger();
  }

  public static void initailLogger() {
    try {
      PropertyConfigurator.configure(Class.forName("th.co.ais.cpac.cl.common.UtilityLogger").getResource("log4j.properties"));
    } catch (ClassNotFoundException ex) {
    	ex.printStackTrace();
    }
  }

  public static void initailLogger(String path) {
    File file = new File(path);
    if (file.isFile() && file.canRead()) {
      PropertyConfigurator.configure(path);
    }
  }

  public UtilityLogger(String name) {
    super(org.apache.log4j.Logger.getLogger(name));
  }

}
