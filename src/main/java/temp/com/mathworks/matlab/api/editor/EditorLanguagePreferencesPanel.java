package temp.com.mathworks.matlab.api.editor;

import java.awt.Component;

public abstract interface EditorLanguagePreferencesPanel
{
  public abstract EditorLanguage getLanguage();
  
  public abstract Component createPanel();
  
  public abstract void cleanup();
  
  public abstract void commit();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\EditorLanguagePreferencesPanel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */