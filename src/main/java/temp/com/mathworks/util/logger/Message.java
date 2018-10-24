package temp.com.mathworks.util.logger;

import java.util.Date;
import java.util.Map;

public abstract interface Message
{
  public abstract String getThrowableText();
  
  public abstract Throwable getThrowable();
  
  public abstract String getMessage();
  
  public abstract String getHeader();
  
  public abstract boolean isRead();
  
  public abstract void setRead(boolean paramBoolean);
  
  public abstract boolean isSubmitted();
  
  public abstract Date getDate();
  
  public abstract String getIssueUrl();
  
  public abstract void setIssueUrl(String paramString);
  
  public abstract String getDigest();
  
  public abstract Map<String, Object> getParameters();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\logger\Message.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */