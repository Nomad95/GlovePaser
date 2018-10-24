package temp.com.mathworks.matlab.api.toolbars;

import java.util.List;

public abstract interface ToolBarBuilder<T>
{
  public abstract void add(ToolBarGroupID paramToolBarGroupID, T... paramVarArgs);
  
  public abstract void addToFront(ToolBarGroupID paramToolBarGroupID, T... paramVarArgs);
  
  public abstract List<T> getItems();
  
  public abstract List<String> getSeparatedItems();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\toolbars\ToolBarBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */