package temp.com.mathworks.matlab.api.editor;

public abstract interface EditorLayerProvider
{
  public abstract boolean requiresEditor();
  
  public abstract EditorLayer createEditorLayer();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\EditorLayerProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */