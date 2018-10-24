package temp.com.mathworks.matlab.api.debug;

import java.util.List;

public abstract interface Debugger<T, V>
{
  public abstract List<T> getBreakpoints(V paramV);
  
  public abstract void clearBreakpoint(T paramT);
  
  public abstract void continueExecution();
  
  public abstract void addDebugListener(DebugListener<T> paramDebugListener);
  
  public abstract void removeDebugListener(DebugListener<T> paramDebugListener);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\debug\Debugger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */