package temp.com.mathworks.util;

public abstract interface ExtendedIterable<T>
  extends Iterable<T>
{
  public abstract boolean isEmpty();
  
  public abstract boolean isSizeGreaterThan(int paramInt);
  
  public abstract boolean isSizeEqualTo(int paramInt);
  
  public abstract boolean isSizeLessThanOrEqualTo(int paramInt);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\ExtendedIterable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */