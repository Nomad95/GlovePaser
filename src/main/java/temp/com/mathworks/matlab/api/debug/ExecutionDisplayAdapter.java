package temp.com.mathworks.matlab.api.debug;

import java.util.EnumSet;

public abstract interface ExecutionDisplayAdapter
{
  public abstract EnumSet<LineDebugState> getLineDebugState(int paramInt);
  
  public abstract void addListener(RepaintListener paramRepaintListener);
  
  public abstract void removeListener(RepaintListener paramRepaintListener);
  
  public abstract void dispose();
  
  public static abstract interface RepaintListener
  {
    public abstract void repaintNeeded();
  }
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\debug\ExecutionDisplayAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */