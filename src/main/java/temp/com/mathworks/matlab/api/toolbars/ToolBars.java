package temp.com.mathworks.matlab.api.toolbars;

import java.util.List;

public abstract interface ToolBars<T>
{
  public abstract void addNewToolBar(ToolBarID paramToolBarID, ToolBarBuilder<T> paramToolBarBuilder);
  
  public abstract ToolBarBuilder<T> getBuilder(ToolBarID paramToolBarID);
  
  public abstract List<ToolBarID> getToolBarIDs();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\toolbars\ToolBars.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */