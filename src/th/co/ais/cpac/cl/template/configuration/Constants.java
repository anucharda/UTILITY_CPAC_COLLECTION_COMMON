/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.cpac.cl.template.configuration;

import java.io.File;
import java.math.BigDecimal;

/**
 *
 * @author Boy
 */
public class Constants {

  public static final String END_LINE_WINDOWS = "\r\n";
  public static final String END_LINE_LINUX = "\n";
  public static String END_LINE = "";

  static {
    if ("/".equals(File.separator)) {
      END_LINE = END_LINE_LINUX;
    } else {
      END_LINE = END_LINE_WINDOWS;
    }

  }

  public enum Language {

    ENG('E'), THAI('T');

    private char lang;

    private Language(char c) {
      lang = c;
    }

    public char getCode() {
      return lang;
    }

    public String getCodeAsString() {
      return "" + lang;
    }

    public boolean isEngLanguage() {
      return lang == 'E';
    }

    public boolean isThaiLanguage() {
      return lang == 'T';
    }

    public String getLocale() {
      if (isThaiLanguage()) {
        return "TH-th";
      } else if (isEngLanguage()) {
        return "EN-en";
      } else {
        return null;
      }
    }

    public BigDecimal getLanguageId() {
      if (lang == 'E') {
        return new BigDecimal("2");
      }
      if (lang == 'T') {
        return new BigDecimal("1");
      }
      return BigDecimal.ZERO;
    }

    @Override
    public String toString() {
      return getCodeAsString();
    }

  }

  public static Language mapLanguage(String lang, Language defVal) {
    if (lang == null) {
      return defVal;
    }
    lang = "#" + lang.toUpperCase() + "#";
    if ("#1#E#EN#ENG#".contains(lang)) {
      return Language.ENG;
    }
    if ("#2#T#TH#THA#THAI#".contains(lang)) {
      return Language.THAI;
    }
    return defVal;
  }

}
