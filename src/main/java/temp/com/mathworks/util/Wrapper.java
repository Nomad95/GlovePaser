package temp.com.mathworks.util;

public abstract interface Wrapper<T, U>
{
  public abstract U wrap(T paramT);
  
  public abstract T unwrap(U paramU);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\Wrapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */