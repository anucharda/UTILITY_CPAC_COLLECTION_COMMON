package th.co.ais.cpac.cl.template.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Properties;

abstract public class CNFTemplate {

  public CNFTemplate(Properties dataConfig) {
    setHashMap(dataConfig);
  }

  public CNFTemplate() {
  }

  abstract protected HashMap<String, String> createHashMap();

  abstract protected HashMap<String, Object> createHashMapArray();

  abstract protected HashMap<String, String> getHashMap();

  abstract protected HashMap<String, Object> getHashMapArray();

  protected void setHashMap(Properties dataConfig) {
    if (dataConfig == null || dataConfig.isEmpty()) {
      return;
    }
    if (getHashMap() != null && !getHashMap().isEmpty()) {
      getHashMap().clear();      
    }
    copyHashMap(dataConfig);
    if (getHashMapArray() != null) {
      getHashMapArray().clear();
    }
  }

  protected int getHashMap(String k, int d) {
    if (getHashMap() == null) {
      return d;
    }
    String value = getHashMap().get(k);
    if (value == null || value.isEmpty()) {
      return d;
    }
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException ec) {
    }
    return d;
  }

  protected String getHashMap(String k, String d) {
    if (getHashMap() == null) {
      return d;
    }
    String value = getHashMap().get(k);
    if (value == null || value.isEmpty()) {
      return d;
    }
    return value;
  }

  protected long getHashMap(String k, long d) {
    if (getHashMap() == null) {
      return d;
    }
    String value = getHashMap().get(k);
    if (value == null || value.isEmpty()) {
      return d;
    }
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException ec) {
    }
    return d;
  }

  private void copyHashMap(Properties p) {
    if (getHashMap() == null) {
      createHashMap();
    }
    if (p == null) {
      return;
    }
    Enumeration<Object> keys = p.keys();

    while (keys.hasMoreElements()) {
      String key = (String) keys.nextElement();
      getHashMap().put(key, p.getProperty(key));
    }
  }

  protected String keyHashMapArrayList(String key, String type) {
    return key + "___ArrayList___" + type;
  }

  protected String keyHashMapArray(String key, String type) {
    return key + "___Array___" + type;
  }

  //
  public String get(String name, String language, String defaultVal) {
    return get(name + "." + language, defaultVal);
  }

  public String get(String name, String defaultVal) {
    return getHashMap(name, defaultVal);
  }

  public int get(String key, int defaultVal) {
    return getHashMap(key, defaultVal);
  }

  public long get(String key, long defaultVal) {
    return getHashMap(key, defaultVal);
  }

  public boolean isMatchValue(String key, String strCheck) {
    String value = getHashMap(key, null);
    if (strCheck != null && strCheck.equals(value)) {
      return true;
    }
    return strCheck == null && value == null;
  }

  public ArrayList<String> getArrayListOfString(String key, String det) {
    String keyArray = keyHashMapArrayList(key, "String");

    String value = get(key, null);
    if (value == null) {
      return null;
    }
    if (getHashMapArray() == null) {
      createHashMapArray();
    }
    if (getHashMapArray().get(keyArray) == null) {
      String[] aa = value.split(det);
      ArrayList<String> ll = new ArrayList<>(Arrays.asList(aa));
      getHashMapArray().put(keyArray, ll);
    }
    return (ArrayList<String>) getHashMapArray().get(keyArray);
  }

  public ArrayList<Integer> getArrayListOfInteger(String key, String det) {
    String keyArray = keyHashMapArrayList(key, "Integer");

    String value = get(key, null);
    if (value == null) {
      return null;
    }
    if (getHashMapArray() == null) {
      createHashMapArray();
    }

    if (getHashMapArray().get(keyArray) == null) {
      String[] aa = value.split(det);
      ArrayList<Integer> ll = new ArrayList<>();
      for (String temp : aa) {
        try {
          ll.add(new Integer(temp));
        } catch (NumberFormatException ex) {
          ll = null;
          break;
        }
      }
      if (ll != null) {
        getHashMapArray().put(keyArray, ll);
      }
    }

    return (ArrayList<Integer>) getHashMapArray().get(keyArray);
  }

  public long[] getArrayOfLong(String key, String det) {
    String keyArray = keyHashMapArray(key, "Long");

    String value = get(key, null);
    if (value == null) {
      return null;
    }
    if (getHashMapArray() == null) {
      createHashMapArray();
    }

    if (getHashMapArray().get(keyArray) == null) {
      String[] aa = value.split(det);
      long[] ll = new long[aa.length];

      for (int i = 0; i < aa.length; i++) {
        try {
          ll[i] = Long.parseLong(aa[i]);
        } catch (NumberFormatException ex) {
          ll = null;
          break;
        }
      }

      if (ll != null && ll.length > 0) {
        getHashMapArray().put(keyArray, ll);
      }
    }

    return (long[]) getHashMapArray().get(keyArray);
  }  
  
  public int[] getArrayOfInteger(String key, String det) {
    String keyArray = keyHashMapArray(key, "Integer");

    String value = get(key, null);
    if (value == null) {
      return null;
    }
    if (getHashMapArray() == null) {
      createHashMapArray();
    }

    if (getHashMapArray().get(keyArray) == null) {
      String[] aa = value.split(det);
      int[] ll = new int[aa.length];

      for (int i = 0; i < aa.length; i++) {
        try {
          ll[i] = Integer.parseInt(aa[i]);
        } catch (NumberFormatException ex) {
          ll = null;
          break;
        }
      }

      if (ll != null && ll.length > 0) {
        getHashMapArray().put(keyArray, ll);
      }
    }

    return (int[]) getHashMapArray().get(keyArray);
  }

  public String[] getArrayOfString(String key, String det) {
    String keyArray = keyHashMapArray(key, "String");

    String value = get(key, null);
    if (value == null) {
      return null;
    }
    if (getHashMapArray() == null) {
      createHashMapArray();
    }

    if (getHashMapArray().get(keyArray) == null) {
      String[] aa = value.split(det);
      if (aa != null && aa.length > 0) {
        getHashMapArray().put(keyArray, aa);
      }
    }

    return (String[]) getHashMapArray().get(keyArray);
  }

  public void addHashMap(Properties p) {
    copyHashMap(p);
  }

  public void addHashMap(String key, String value) {
    if (getHashMap() == null) {
      createHashMap();
    }
    if (key == null || value == null) {
      return;
    }
    getHashMap().put(key, value);
    if (getHashMapArray() != null) {
      getHashMapArray().clear();
    }
  }

  //
  public void printArrayListOfString(StringBuffer s, String key, String det, String endline) {
    ArrayList<String> value = getArrayListOfString(key, det);
    if (value == null) {
      s.append("list [key=").append(key).append(",det=").append(det).append("] is null");
      return;
    }
    if (value.isEmpty()) {
      s.append("list [key=").append(key).append(",det=").append(det).append("] is isEmpty");
      return;
    }
    s.append(endline);
    s.append("list [key=").append(key).append(",det=").append(det).append("]").append(endline);
    for (int i = 0; i < value.size(); i++) {
      s.append("value(").append(i + 1).append(")=").append(value.get(i));
    }

  }

  public void printArrayListOfInteger(StringBuffer s, String key, String det, String endline) {
    ArrayList<Integer> value = getArrayListOfInteger(key, det);
    if (value == null) {
      s.append("list [key=").append(key).append(",det=").append(det).append("] is null");
      return;
    }
    if (value.isEmpty()) {
      s.append("list [key=").append(key).append(",det=").append(det).append("] is isEmpty");
      return;
    }
    s.append(endline);
    s.append("list [key=").append(key).append(",det=").append(det).append("]").append(endline);
    for (int i = 0; i < value.size(); i++) {
      s.append("value(").append(i + 1).append(")=").append(value.get(i).intValue());
    }
  }

  public StringBuilder dump(String endline) {
    Iterator<String> lk = getHashMap().keySet().iterator();
    StringBuilder str = new StringBuilder();
    while (lk.hasNext()) {
      String k = (String) lk.next();
      str.append(k).append(":[").append(getHashMap(k, "null")).append("]").append(endline);
    }
    return str;
  }

  public static void main(String[] args) {
    HashMap<String, Object> ll = new HashMap<>();

    ArrayList<String> l = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      l.add("" + i);
    }
    ll.put("string", l);

    ArrayList<Integer> l2 = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      l2.add(i);
    }
    ll.put("int", l2);

    ArrayList<String> temp1 = (ArrayList<String>) ll.get("string");
    for (int i = 0; i < temp1.size(); i++) {
      System.out.println(temp1.get(i));
    }

    ArrayList<Integer> temp2 = (ArrayList<Integer>) ll.get("int");
    for (int i = 0; i < temp2.size(); i++) {
      System.out.println(temp2.get(i) + 100);
    }

    /*
     ArrayList<String> result = new ArrayList();
     String value = "#11111111#222222222222#";
     String det = "#";
     while (value.contains(det)) {
     int index = value.indexOf(det);
     String mm = value.substring(0, index);
     if (mm != null && !mm.trim().isEmpty()) {
     result.add(mm);
     }

     if (value.equals(det)) {
     break;
     }
     value = value.substring(index + 1);
     }
     if (!value.equals(det)) {
     result.add(value);
     }
     System.out.println(result);
     */
  }

}

//
//  private static void readConfig() throws ClassNotFoundException {
//    Class.forName(null).getResourceAsStream("ConfigurationService.properties");
//    
//    InputStream in = class.getResourceAsStream("ConfigurationService.properties");
//    
//            dataConfig = new Properties();
//    try {
//      dataConfig.load(in);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    try {
//      in.close();
//    } catch (IOException ex) {
//    }
//  }
//  public ArrayList<String> toStringArrayList(String key, String det) {
//    String value = getHashMap(key, null);
//    if (value == null || value.length() == 0) {
//      return null;
//    }
//    ArrayList<String> result = new ArrayList();
//
//    while (value.contains(det)) {
//      int index = value.indexOf(det);
//      String mm = value.substring(0, index);
//      if (mm != null && !mm.trim().isEmpty()) {
//        result.add(mm);
//      }
//      if (value.equals(det)) {
//        break;
//      }
//      value = value.substring(index + 1);
//    }
//    if (value != null && !value.trim().isEmpty() && !value.equals(det)) {
//      result.add(value);
//    }
//
//    return result;
//  }
/*

 public ArrayList<Integer> getArrayListInteger(String key, String det) {
 ArrayList<String> value = toStringArrayList(key, det);
 if (value == null) {
 return null;
 }
 ArrayList<Integer> result = new ArrayList<>();
 for (String value1 : value) {
 // System.out.println("value = " + value.get(i));
 try {
 result.add(new Integer(value1));
 } catch (NumberFormatException ex) {
 return null;
 }
 }
 return result;
 }

 public String[] toStringArray(String key, String det) {
 ArrayList<String> list = toStringArrayList(key, det);
 if (list == null) {
 return null;
 }
 String[] result = new String[list.size()];
 for (int i = 0; i < list.size(); i++) {
 result[i] = list.get(i);
 }
 return result;
 }

 public int[] toInteger(String key, String det) {
 ArrayList<Integer> list = toIntegerArrayList(key, det);
 if (list == null || list.isEmpty()) {
 return null;
 }
 int[] result = new int[list.size()];
 for (int i = 0; i < list.size(); i++) {
 result[i] = list.get(i);
 }
 return result;
 }

 */
 /* for reload 
 abstract protected Timer createTimer();

 abstract protected Properties load();
 protected class Reload extends TimerTask {

 public Reload() {
 }

 @Override
 public void run() {
 int round = 0;
 while (true) {
 round++;
 if (round >= 3) {
 return;
 }
 Properties data = load();
 if (data == null || data.isEmpty()) {
 //error 
 } else {
 setHashMap(data);
 return;
 }
 try {
 Thread.sleep(1000 * 1);
 } catch (InterruptedException ex) {
 }
 }
 }

 }

 public void load(int hour, String timestart) {
 Reload reload = new Reload();
 Timer timer = createTimer();
 timer.schedule(reload, getTimeStartTimer(hour, timestart), hour * 1000 * 60 * 60);
 }

 private Date getTimeStartTimer(int hour, String timestart) {
 SimpleDateFormat sdf_d = new SimpleDateFormat("yyyy-MM-dd");
 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
 Date current = new Date();
 Date start;
 Date next;

 //int hour = cnf.getInt("cycle.time.get.template.gbe", 24);
 String yyyymmdd = sdf_d.format(current);

 //cnf.get("start.time.get.template.gbe", "00:00") 
 // yyyy-MM-dd hh:mm
 start = parseDate("yyyy-MM-dd hh:mm", yyyymmdd + " " + timestart, yyyymmdd + " 00:00");
 next = addTime(start, hour);

 while (true) {
 if (current.before(next) && current.after(start)) {
 break;
 }
 start = next;
 next = addTime(start, hour);
 }
 return start;
 }

 private Date parseDate(String format, String date, String dateDef) {
 SimpleDateFormat sdf = new SimpleDateFormat(format);
 try {
 return sdf.parse(date);
 } catch (ParseException ex) {
 }
 try {
 return sdf.parse(dateDef);
 } catch (ParseException ex) {
 }
 return null;
 }

 private static Date addTime(Date d, int ses) {
 Calendar c = Calendar.getInstance();
 c.setTime(d);
 c.add(Calendar.HOUR, ses);
 return c.getTime();
 }
 reload auto */
