package temp.com.mathworks.matlab.api.editor.actions;

import com.mathworks.util.ParameterRunnable;

public abstract interface EditorToolTipDelegate
  extends Prioritizable
{
  public abstract boolean isApplicable();
  
  public abstract void computeTooltip(String paramString, ParameterRunnable<String> paramParameterRunnable);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\actions\EditorToolTipDelegate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */