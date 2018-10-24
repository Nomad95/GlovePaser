package temp.com.mathworks.util.jarloader;

import java.beans.BeanInfo;

public abstract interface DoOnBean
{
  public abstract void action(JarInfo paramJarInfo, BeanInfo paramBeanInfo, String paramString);
  
  public abstract void error(String paramString);
  
  public abstract void error(String paramString, Exception paramException);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\jarloader\DoOnBean.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */