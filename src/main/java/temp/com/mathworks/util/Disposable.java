package temp.com.mathworks.util;

public abstract interface Disposable
{
  public abstract void dispose();
  
  public static abstract interface Parent
    extends Disposable
  {
    public abstract void beforeChildrenDisposed(Iterable<Disposable> paramIterable);
  }
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\Disposable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */