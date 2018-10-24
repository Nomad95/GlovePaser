package temp.com.mathworks.matlab.api.editor;

import com.mathworks.matlab.api.datamodel.PropertyChangeProvider;
import com.mathworks.matlab.api.datamodel.StorageLocation;
import com.mathworks.matlab.api.debug.BreakpointMargin;
import com.mathworks.matlab.api.debug.ExecutionArrowMargin;
import java.awt.Component;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

public abstract interface Editor
  extends PropertyChangeProvider
{
  public abstract void appendText(String paramString);
  
  public abstract void bringToFront();
  
  public abstract void close();
  
  public abstract void closeNoPrompt();
  
  public abstract String getShortName();
  
  public abstract String getLongName();
  
  public abstract String getUniqueKey();
  
  public abstract StorageLocation getStorageLocation();
  
  public abstract Component getComponent();
  
  public abstract JTextComponent getTextComponent();
  
  public abstract void insertTextAtCaret(String paramString);
  
  public abstract void insertAndFormatTextAtCaret(String paramString);
  
  public abstract void replaceText(String paramString, int paramInt1, int paramInt2);
  
  public abstract void setCaretPosition(int paramInt);
  
  public abstract void setSelection(int paramInt1, int paramInt2);
  
  public abstract String getSelection();
  
  public abstract int getCaretPosition();
  
  public abstract int getLength();
  
  public abstract boolean negotiateSave();
  
  public abstract void save()
    throws Exception;
  
  public abstract void saveAs(String paramString)
    throws Exception;
  
  public abstract Document getDocument();
  
  public abstract String getTextWithSystemLineEndings();
  
  public abstract String getText();
  
  public abstract void goToLine(int paramInt, boolean paramBoolean);
  
  public abstract void goToLine(int paramInt1, int paramInt2);
  
  public abstract void goToPositionAndHighlight(int paramInt1, int paramInt2);
  
  public abstract boolean isBuffer();
  
  public abstract boolean isDirty();
  
  public abstract void setDirtyUntilSave();
  
  public abstract void setClean();
  
  public abstract boolean isMCode();
  
  public abstract boolean isOpen();
  
  public abstract void goToFunction(String paramString1, String paramString2);
  
  public abstract void reload();
  
  public abstract void refreshMenus();
  
  public abstract void setStatusText(String paramString);
  
  public abstract void setEditorStatusBarText(String paramString);
  
  public abstract void smartIndentContents();
  
  public abstract void setEditable(boolean paramBoolean);
  
  public abstract boolean isEditable();
  
  public abstract void fireEditorEvent(EditorEvent paramEditorEvent);
  
  public abstract void addEventListener(EditorEventListener paramEditorEventListener);
  
  public abstract void removeEventListener(EditorEventListener paramEditorEventListener);
  
  public abstract Object getProperty(Object paramObject);
  
  public abstract void putProperty(String paramString, Object paramObject);
  
  public abstract EditorLanguage getLanguage();
  
  public abstract BreakpointMargin<?> getBreakpointMargin();
  
  public abstract ExecutionArrowMargin getExecutionArrowMargin();
  
  public abstract int getLineNumber();
  
  public abstract boolean lockIfOpen();
  
  public abstract void unlock();
  
  public abstract int[] positionToLineAndColumn(int paramInt);
  
  public abstract void refreshToolstrip();
  
  public abstract int lineAndColumnToPosition(int paramInt1, int paramInt2);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\Editor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */