package temp.com.mathworks.matlab.api.editor;

import java.util.List;
import javax.swing.text.EditorKit;

public abstract interface EditorLanguage
{
  public abstract boolean isMatchingExtension(String paramString);
  
  public abstract String getInternalName();
  
  public abstract List<String> getDefaultExtensions();
  
  public abstract EditorLanguagePriority getPriority();
  
  public abstract String getMimeType();
  
  public abstract String getName();
  
  public abstract String getDescription();
  
  public abstract EditorKit createDefaultKit();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\EditorLanguage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */