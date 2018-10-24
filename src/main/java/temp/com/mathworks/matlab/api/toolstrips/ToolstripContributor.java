package temp.com.mathworks.matlab.api.toolstrips;

import com.mathworks.matlab.api.editor.Editor;
import com.mathworks.toolstrip.ToolstripSection;
import com.mathworks.toolstrip.ToolstripTab;
import java.util.List;

public abstract interface ToolstripContributor
{
  public abstract List<ToolstripTab> getToolstripTabs(Editor paramEditor);
  
  public abstract List<ToolstripSection> getToolstripSections(Editor paramEditor);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\toolstrips\ToolstripContributor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */