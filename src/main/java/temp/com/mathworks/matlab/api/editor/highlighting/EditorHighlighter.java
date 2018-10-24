package temp.com.mathworks.matlab.api.editor.highlighting;

import com.mathworks.matlab.api.editor.EditorRegion;
import java.awt.Color;
import java.util.List;
import org.netbeans.editor.BaseDocument;

public abstract interface EditorHighlighter
{
  public abstract EditorRegion addRegion(int paramInt1, int paramInt2);
  
  public abstract void removeRegion(EditorRegion paramEditorRegion);
  
  public abstract void removeRegions(List<EditorRegion> paramList, boolean paramBoolean);
  
  public abstract boolean isEnabled();
  
  public abstract void addListener(HighlighterUpdateListener paramHighlighterUpdateListener);
  
  public abstract void removeListener(HighlighterUpdateListener paramHighlighterUpdateListener);
  
  public abstract List<EditorRegion> getEditorRegions();
  
  public abstract Color getHighlightColor();
  
  public abstract void dispose();
  
  public abstract void setDocument(BaseDocument paramBaseDocument);
  
  public abstract void initialize();
  
  public static abstract interface HighlighterUpdateListener
  {
    public abstract void highlightsUpdated();
  }
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\highlighting\EditorHighlighter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */