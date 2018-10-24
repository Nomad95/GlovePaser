package temp.com.mathworks.matlab.api.editor;

import java.util.List;
import javax.swing.text.EditorKit;
import org.netbeans.editor.TokenContext;

public abstract interface EditorSyntaxHighlighting
{
  public abstract EditorLanguage getLanguage();
  
  public abstract List<SyntaxHighlightingColor> getColors();
  
  public abstract TokenContext getTokenContext();
  
  public abstract Class<? extends EditorKit> getBaseKitClass();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\EditorSyntaxHighlighting.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */