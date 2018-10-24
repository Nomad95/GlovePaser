package temp.com.mathworks.matlab.api.debug;

import java.util.Collection;

public abstract interface BreakpointUiInfoProvider<T extends Breakpoint>
{
  public abstract BreakpointStyle getBreakpointStyle(Collection<T> paramCollection, boolean paramBoolean);
  
  public abstract String generateToolTipText(Collection<T> paramCollection, boolean paramBoolean1,
          boolean paramBoolean2);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\debug\BreakpointUiInfoProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */