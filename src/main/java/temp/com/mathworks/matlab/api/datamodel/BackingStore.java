package temp.com.mathworks.matlab.api.datamodel;

import java.awt.Component;

public abstract interface BackingStore<T>
  extends PropertyChangeProvider
{
  public abstract void load(T paramT)
    throws Exception;
  
  public abstract StorageLocation getStorageLocation();
  
  public abstract String getPreferredContentType(String paramString);
  
  public abstract boolean isPersistenceLocationSet();
  
  public abstract boolean isReadOnly();
  
  public abstract boolean isOutOfSync();
  
  public abstract void sync();
  
  public abstract boolean negotiateSave(T paramT, Component paramComponent)
    throws Exception;
  
  public abstract void save(T paramT)
    throws Exception;
  
  public abstract boolean negotiateSaveAs(T paramT, Component paramComponent)
    throws Exception;
  
  public abstract boolean shouldSaveOnClose(T paramT);
  
  public abstract void negotiateBackup(T paramT, Component paramComponent)
    throws Exception;
  
  public abstract void addBackingStoreEventListener(BackingStoreEventListener<T> paramBackingStoreEventListener);
  
  public abstract void removeBackingStoreEventListener(BackingStoreEventListener<T> paramBackingStoreEventListener);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\datamodel\BackingStore.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */