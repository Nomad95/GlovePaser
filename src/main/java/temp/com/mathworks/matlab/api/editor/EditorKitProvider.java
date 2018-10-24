package temp.com.mathworks.matlab.api.editor;

import com.mathworks.matlab.api.datamodel.BackingStore;
import javax.swing.text.EditorKit;

public abstract interface EditorKitProvider
{
  public abstract boolean isApplicable(BackingStore<?> paramBackingStore);
  
  public abstract String getType();
  
  public abstract EditorKit getEditorKit(BackingStore<?> paramBackingStore);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\EditorKitProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */