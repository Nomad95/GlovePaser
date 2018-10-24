package temp.com.mathworks.matlab.api.editor;

import com.mathworks.matlab.api.editor.highlighting.EditorHighlighterProvider;
import java.util.List;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.editor.ext.ExtEditorUI;

public abstract interface EditorLayer
{
  public abstract void enable(JTextComponent paramJTextComponent);
  
  public abstract void disable();
  
  public abstract void updateDocuments(Document paramDocument1, Document paramDocument2);
  
  public abstract String getLayerName();
  
  public abstract boolean supportsMessageBar();
  
  public abstract EditorMessageBarContributor getMessageBarContributor();
  
  public abstract boolean supportsHighlighting();
  
  public abstract EditorHighlighterProvider getEditorHighlighterProvider();
  
  public abstract void settingsChanged(String paramString, ExtEditorUI paramExtEditorUI);
  
  public abstract void cleanup();
  
  public abstract List<? extends EditorMessage> getMessagesAtPosition(int paramInt1, int paramInt2);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\EditorLayer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */