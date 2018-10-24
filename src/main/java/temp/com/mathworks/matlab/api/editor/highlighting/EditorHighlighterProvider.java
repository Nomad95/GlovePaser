package temp.com.mathworks.matlab.api.editor.highlighting;

import com.mathworks.matlab.api.editor.Editor;

public abstract interface EditorHighlighterProvider
{
  public abstract void configureHighlighter(Editor paramEditor);
  
  public abstract EditorHighlighter getHighlighter();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\highlighting\EditorHighlighterProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */