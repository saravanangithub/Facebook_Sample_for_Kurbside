package com.kurbside.android.commons;

import com.kurbside.android.R;

/**
 * Created by tommeng on 8/10/13.
 */
public enum LoginType
{
  FACEBOOK(R.string.Facebook),
  PHONE(R.string.Phone_And_Password);

  private int _strRes;


  LoginType(int strRes)
  {
    _strRes = strRes;
  }


  public void setStrRes(int strRes)
  {
    _strRes = strRes;
  }


  public int getStrRes()
  {
    return _strRes;
  }
}
