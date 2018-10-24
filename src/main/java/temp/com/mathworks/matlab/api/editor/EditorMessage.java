package temp.com.mathworks.matlab.api.editor;

import java.awt.Color;
import javax.swing.Action;
import javax.swing.Icon;

public abstract interface EditorMessage
{
  public abstract String getMessage();
  
  public abstract int getStartPosition();
  
  public abstract int getEndPosition();
  
  public abstract int getNumberOfLines();
  
  public abstract int getLineNumber();
  
  public abstract Color getColor();
  
  public abstract boolean dismissOnCaretUpdate();
  
  public abstract Icon getIcon(int paramInt);
  
  public abstract boolean isActionable();
  
  public abstract boolean hasTooltipAction();
  
  public abstract Action getTooltipAction();
  
  public abstract boolean isTooltipActionVisible();
  
  public abstract boolean isExtendable();
  
  public abstract boolean hasExtendedInformation();
  
  public abstract String getExtendedInformation();
  
  public abstract boolean isStatic();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\EditorMessage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */