package temp.com.mathworks.matlab.api.debug;

import java.util.Collection;
import javax.swing.JPopupMenu;

public abstract interface BreakpointMarginPopUpMenu<T extends Breakpoint>
{
  public abstract JPopupMenu buildPopupMenu(int paramInt1, Collection<T> paramCollection, int paramInt2);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\debug\BreakpointMarginPopUpMenu.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */