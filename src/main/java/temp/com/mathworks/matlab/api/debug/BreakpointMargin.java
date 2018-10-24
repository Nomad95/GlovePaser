package temp.com.mathworks.matlab.api.debug;

public abstract interface BreakpointMargin<T extends Breakpoint>
{
  public abstract void setBreakpoint(int paramInt);
  
  public abstract void clearBreakpoint(T paramT);
  
  public abstract void toggle(int paramInt);
  
  public abstract void dispose();
  
  public abstract boolean isActive();
  
  public abstract void setLive(boolean paramBoolean);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\debug\BreakpointMargin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */