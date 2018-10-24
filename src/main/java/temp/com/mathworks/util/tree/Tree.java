package temp.com.mathworks.util.tree;

public abstract interface Tree<T>
{
  public abstract T getRoot();
  
  public abstract int getChildCount(T paramT);
  
  public abstract T getChild(T paramT, int paramInt);
  
  public abstract T getParent(T paramT);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\tree\Tree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */