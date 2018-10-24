package temp.com.mathworks.matlab.api.debug;

import java.io.File;
import java.util.Collection;

public abstract interface DebugListener<T>
{
  public abstract void breakpointsAdded(Collection<T> paramCollection);
  
  public abstract void breakpointsRemoved(Collection<T> paramCollection);
  
  public abstract void breakpointHit(int paramInt, File paramFile);
  
  public abstract void errorInBreakpointExpression(int paramInt, File paramFile, String paramString);
  
  public abstract void breakpointsCleared();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\debug\DebugListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */