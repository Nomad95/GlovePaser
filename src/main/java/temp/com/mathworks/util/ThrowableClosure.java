package temp.com.mathworks.util;

public abstract interface ThrowableClosure<O, I, T extends Throwable>
{
  public abstract O run(I paramI)
    throws Throwable;
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\ThrowableClosure.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */