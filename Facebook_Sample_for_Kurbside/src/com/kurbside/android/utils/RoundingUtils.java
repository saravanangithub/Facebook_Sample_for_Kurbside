package com.kurbside.android.utils;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created by tmeng999 on 6/13/13.
 */
public class RoundingUtils
{
  public static String formatAsString(double d)
  {
    BigDecimal bd = new BigDecimal(d);
    return formatAsString(bd);
  }


  public static Double formatAsDouble(double d)
  {
    BigDecimal bd = new BigDecimal(d);
    return formatAsDouble(bd);
  }


  public static String formatAsString(String d)
  {
    BigDecimal bd = new BigDecimal(d);
    return formatAsString(bd);
  }


  public static Double formatAsDouble(String d)
  {
    BigDecimal bd = new BigDecimal(d);
    return formatAsDouble(bd);
  }


  public static String formatTaxAsString(double d)
  {
    BigDecimal bd = new BigDecimal(d);
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    return bd.toPlainString();
  }


  public static Double formatTaxAsDouble(double d)
  {
    BigDecimal bd = new BigDecimal(d);
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    return bd.doubleValue();
  }


  public static String formatTaxAsString(String d)
  {
    BigDecimal bd = new BigDecimal(d);
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    return bd.toPlainString();
  }


  public static Double formatTaxAsDouble(String d)
  {
    BigDecimal bd = new BigDecimal(d);
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    return bd.doubleValue();
  }


  public static String formatAsString(BigDecimal bd)
  {
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    return bd.toPlainString();
  }


  public static Double formatAsDouble(BigDecimal bd)
  {
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    double doubleValue = bd.doubleValue();
    return doubleValue;
  }


  public static String formatAsString(BigDecimal bd, Currency c)
  {
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    return bd.toPlainString();
  }


  public static Double formatAsDouble(BigDecimal bd, Currency c)
  {
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    return bd.doubleValue();
  }
}
