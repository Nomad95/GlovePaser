package temp.com.mathworks.matlab.api.editor.actions;

import com.mathworks.matlab.api.editor.Editor;
import com.mathworks.mwswing.MInputMap;
import javax.swing.ActionMap;

public abstract interface KeyBindingContributor
{
  public abstract boolean isApplicable(Editor paramEditor);
  
  public abstract MInputMap buildInputMap(Editor paramEditor, ActionMap paramActionMap);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\actions\KeyBindingContributor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */