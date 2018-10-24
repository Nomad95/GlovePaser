package temp.com.mathworks.util.logger;

public abstract interface MessagePool
{
  public abstract void addErrorMessage(Message paramMessage);
  
  public abstract boolean isPoolEmpty();
  
  public abstract int size();
  
  public abstract Message[] getErrors();
  
  public abstract void clearPool();
  
  public abstract void addListener(MessagePoolListener paramMessagePoolListener);
  
  public abstract void removeListener(MessagePoolListener paramMessagePoolListener);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\logger\MessagePool.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */