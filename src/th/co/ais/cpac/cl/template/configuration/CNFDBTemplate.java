/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.configuration;

import java.util.Properties;

/**
 *
 * @author OptimusPrime
 */
public abstract class CNFDBTemplate extends CNFTemplate {

  abstract protected Properties load();

  public void loadOnStartUp() {
    Properties pp = load();
    setHashMap(pp);
  }

}
