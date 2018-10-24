package temp.com.mathworks.matlab.api.editor;

import com.mathworks.matlab.api.datamodel.BackingStore;
import com.mathworks.matlab.api.datamodel.StorageLocation;
import com.mathworks.matlab.api.dataview.UiInfoProvider;
import java.io.File;
import java.util.List;
import javax.swing.text.Document;

public abstract interface EditorApplication
{
  public abstract Editor newEditor(String paramString);
  
  public abstract Editor openEditor(File paramFile);
  
  public abstract Editor openEditorForExistingFile(File paramFile);
  
  public abstract Editor openEditor(BackingStore<Document> paramBackingStore, UiInfoProvider paramUiInfoProvider);
  
  public abstract Editor openEditorForDebug(File paramFile, int paramInt);
  
  public abstract Editor getEditor(StorageLocation paramStorageLocation);
  
  public abstract Editor findEditor(StorageLocation paramStorageLocation);
  
  public abstract Editor getActiveEditor();
  
  public abstract List<Editor> getOpenEditors();
  
  public abstract Editor goToLineOfOpenEditor(String paramString, int paramInt1, int paramInt2);
  
  public abstract boolean isEditorOpen(StorageLocation paramStorageLocation);
  
  public abstract boolean isEditorOpenAndDirty(StorageLocation paramStorageLocation);
  
  public abstract void close();
  
  public abstract void closeNoPrompt();
  
  public abstract void addEditorApplicationListener(EditorApplicationListener paramEditorApplicationListener);
  
  public abstract void removeEditorApplicationListener(EditorApplicationListener paramEditorApplicationListener);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\EditorApplication.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */