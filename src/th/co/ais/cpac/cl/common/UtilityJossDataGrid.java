/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.common;

import org.infinispan.client.hotrod.RemoteCache;
import th.co.ais.cpac.common.cache.CacheManager;

/**
 *
 * @author Optimus
 */
public class UtilityJossDataGrid {

  private final String cacheName;

  public UtilityJossDataGrid(String cacheName) {
    this.cacheName = cacheName;
  }

  public String get(String key, String defVal) {
    RemoteCache<String, String> cache = CacheManager.getInstance().getCache(cacheName, String.class);
    String value = cache.get(key);
    if (value == null || value.isEmpty()) {
      return defVal;
    }
    return value;
  }

  
  
  
}
