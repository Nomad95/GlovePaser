package temp.com.mathworks.matlab.api.editor.actions;

import javax.swing.Action;

public abstract interface DebuggerActions
{
  public abstract boolean supportsStackComboBox();
  
  public abstract boolean supportsConditionalBreakpoints();
  
  public abstract boolean supportsDisabledBreakpoints();
  
  public abstract boolean isDummy();
  
  public abstract Action getStepActionNoEcho();
  
  public abstract Action getStepAction();
  
  public abstract Action getStepInActionNoEcho();
  
  public abstract Action getStepInAction();
  
  public abstract Action getStepOutActionNoEcho();
  
  public abstract Action getStepOutAction();
  
  public abstract Action getContinueActionNoEcho();
  
  public abstract Action getContinueAction();
  
  public abstract Action getExitDebugAction();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\actions\DebuggerActions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */