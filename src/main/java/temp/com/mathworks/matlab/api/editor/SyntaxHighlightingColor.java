package temp.com.mathworks.matlab.api.editor;

import java.awt.Color;
import java.util.Collection;

public abstract interface SyntaxHighlightingColor
{
  public abstract String getDisplayName();
  
  public abstract Color getCurrentColor();
  
  public abstract Color getDefaultColor();
  
  public abstract String getPreferenceKey();
  
  public abstract String getTokenName();
  
  public abstract Collection<String> getAllTokenNames();
  
  public abstract Collection<Integer> getTokenIDs();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\SyntaxHighlightingColor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */