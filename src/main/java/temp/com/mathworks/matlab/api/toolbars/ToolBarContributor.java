package temp.com.mathworks.matlab.api.toolbars;

import com.mathworks.matlab.api.editor.Editor;
import java.awt.Component;
import javax.swing.Action;
import javax.swing.Icon;

public abstract interface ToolBarContributor
{
  public abstract void contributeToEditorGroup(ToolBars<ToolBarItem> paramToolBars);
  
  public abstract void contributeToEditor(ToolBars<ToolBarItem> paramToolBars, Editor paramEditor);
  
  public abstract void layoutItems(ToolBars<String> paramToolBars);
  
  public static abstract interface ToolBarItem
  {
    public abstract Action getAction();
    
    public abstract Component getComponent();
    
    public abstract String getLabel();
    
    public abstract Icon getIcon();
    
    public abstract String getID();
  }
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\toolbars\ToolBarContributor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */