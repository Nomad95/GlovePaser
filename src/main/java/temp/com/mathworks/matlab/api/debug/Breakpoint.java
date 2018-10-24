package temp.com.mathworks.matlab.api.debug;

public abstract interface Breakpoint
{
  public abstract int getZeroBasedLineNumber();
  
  public abstract int getOneBasedLineNumber();
  
  public abstract Breakpoint deriveBreakpoint(int paramInt);
  
  public abstract boolean isEnabled();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\debug\Breakpoint.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */