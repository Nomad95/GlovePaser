package temp.com.mathworks.matlab.api.debug;

import java.util.Collection;

public abstract interface BreakpointModel<T extends Breakpoint>
{
  public abstract boolean wasThereAnError();
  
  public abstract boolean areBreakpointsLive();
  
  public abstract int getNumberOfPossibleInLineBreakpoints(int paramInt);
  
  public abstract Collection<T> getBreakpointsAt(int paramInt);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\debug\BreakpointModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */