package temp.com.mathworks.util;

public abstract class AsyncReceiver<T>
{
  public abstract boolean receive(T paramT);
  
  public void finished() {}
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\AsyncReceiver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */