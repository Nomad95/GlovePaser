package temp.com.mathworks.util;

import java.util.List;

public abstract interface Combiner<E, A>
{
  public abstract A combine(List<E> paramList);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\Combiner.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */