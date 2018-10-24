package temp.com.mathworks.matlab.api.datamodel;

public abstract interface BackingStoreEventListener<T>
{
  public abstract void backingStoreSaved(BackingStore<T> paramBackingStore);
  
  public abstract void backingStoreLoaded(BackingStore<T> paramBackingStore);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\datamodel\BackingStoreEventListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */