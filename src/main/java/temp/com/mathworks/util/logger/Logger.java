package temp.com.mathworks.util.logger;

public abstract interface Logger
{
  public abstract boolean isDebugEnabled();
  
  public abstract boolean isErrorEnabled();
  
  public abstract boolean isInfoEnabled();
  
  public abstract boolean isWarnEnabled();
  
  public abstract void debug(String paramString);
  
  public abstract void debug(Throwable paramThrowable, String paramString);
  
  public abstract void error(String paramString);
  
  public abstract void error(Throwable paramThrowable, String paramString);
  
  public abstract void info(String paramString);
  
  public abstract void info(Throwable paramThrowable, String paramString);
  
  public abstract void warn(String paramString);
  
  public abstract void warn(Throwable paramThrowable, String paramString);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\logger\Logger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */