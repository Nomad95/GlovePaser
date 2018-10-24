package temp.com.mathworks.util;

public abstract interface InitializeBeforeAccess
{
  public abstract void initializationFinished();
  
  public abstract void checkMutable()
    throws IllegalStateException;
  
  public abstract void checkImmutable()
    throws IllegalStateException;
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\InitializeBeforeAccess.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */