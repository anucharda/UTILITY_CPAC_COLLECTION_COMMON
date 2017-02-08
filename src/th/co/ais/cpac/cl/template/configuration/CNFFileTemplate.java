/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author OptimusPrime
 */
public abstract class CNFFileTemplate extends CNFTemplate {

  abstract protected Object getObjectSynchronized();

  abstract protected void setAbsolutePath(String p);

  abstract protected String getAbsolutePath();

  abstract protected int getTimeInterval();

  abstract protected void setTimeInterval(int p);

  abstract protected long getLastUpdateFile();

  abstract protected void setLastUpdateFile(long p);

  abstract protected long getTimeUpdateHashMap();

  abstract protected void setTimeUpdateHashMap(long p);

  private void setStaticParameter(Properties dataConfig, String absolutePath, long lastModified) {
    setHashMap(dataConfig);
    setAbsolutePath(absolutePath);
    setLastUpdateFile(lastModified);
    setTimeInterval(get("TIME.INTERVAL.READ.FILE.CONFIGURATION", 0));
  }

  public CNFFileTemplate(String nfc) {
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    synchronized (getObjectSynchronized()) {
      File fileConf = null;
      try {
        fileConf = new File(nfc);
      } catch (Exception ex) {
        ex.printStackTrace(System.err);
      }
      if (fileConf == null) {
        return;
      }

      if (!fileConf.canRead()) {
        System.out.println("--------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------");
        System.out.println("--------- " + sdf.format(new java.util.Date()) + " ---------------------------");
        System.out.println("File '" + getAbsolutePath() + "' not found.");
        System.out.println("--------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------");
        fileConf = null;
        return;
      }
      if (fileConf.lastModified() == getLastUpdateFile()) {
        return;
      }
      Properties dataConfig = new Properties();
      InputStreamReader fnStream = null;
      FileInputStream fip = null;
      try {
        fip = new FileInputStream(nfc);
        fnStream = new InputStreamReader(fip, "UTF8");
        dataConfig.load(fnStream);
        setStaticParameter(dataConfig, nfc, fileConf.lastModified());
        dataConfig = null;
        fileConf = null;
      } catch (IOException ex) {
        ex.printStackTrace(System.err);
      } finally {
        if (fip != null) {
          try {
            fip.close();
          } catch (IOException ex) {
          }
        }
        if (fnStream != null) {
          try {
            fnStream.close();
          } catch (IOException ex) {
          }
        }
      }

    }
  }

  public CNFFileTemplate() {
    // getTimeIntervalStatic = + check time  , - no check  , 0 check all time

    if (!isReadNewConfiguration()) {
      return;
    }
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    updateTimeUpdateProperties();
    synchronized (getObjectSynchronized()) {
      if (getAbsolutePath() == null) {
        return;
      }

      File conf = new File(getAbsolutePath());
      if (getLastUpdateFile() == conf.lastModified()) {
        conf = null;
        return;
      }

      if (!conf.canRead()) {
        System.out.println("--------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------");
        System.out.println("--------- " + sdf.format(new java.util.Date()) + " ---------------------------");
        System.out.println("File '" + getAbsolutePath() + "' not found.");
        System.out.println("--------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------");
        conf = null;
        return;
      }
      Properties dataConfig = new Properties();
      InputStreamReader fnStream = null;
      FileInputStream fip = null;
      try {
        System.out.println("--------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------");
        System.out.println("--------- " + sdf.format(new java.util.Date()) + " ---------------------------");
        System.out.println("File '" + getAbsolutePath() + "' reload.");
        System.out.println("--------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------");

        String confPath = getAbsolutePath();
        //dataConfig.load(new InputStreamReader(new FileInputStream(getAbsolutePath()), "UTF8"));

        fip = new FileInputStream(confPath);
        fnStream = new InputStreamReader(fip, "UTF8");
        dataConfig.load(fnStream);

        setStaticParameter(dataConfig, confPath, conf.lastModified());
        conf = null;
        dataConfig = null;
      } catch (IOException ex) {
        ex.printStackTrace(System.out);
        conf = null;
      } finally {
        if (fip != null) {
          try {
            fip.close();
          } catch (IOException ex) {
          }
        }
        if (fnStream != null) {
          try {
            fnStream.close();
          } catch (IOException ex) {
          }
        }
      }
    }
  }

  private boolean isReadNewConfiguration() {
    if (getTimeInterval() > 0) {
      //++ check time interval
      // 1 , 2 ,3 
      if (System.currentTimeMillis() < getTimeUpdateHashMap()) {
        return false;
      }
    } else if (getTimeInterval() < 0) {
      //-- no update properties 
      return false;
    }
    // 0 all check 
    return true;
  }

  private void updateTimeUpdateProperties() {
    if (getTimeInterval() < 1) {
      return;
    }
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(Calendar.MINUTE, getTimeInterval());
    setTimeUpdateHashMap(c.getTimeInMillis());
  }

}
