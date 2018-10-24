package temp.com.mathworks.matlab.api.editor;

public abstract interface EditorRegion
{
  public abstract int getStart();
  
  public abstract int getEnd();
  
  public abstract boolean isValid();
  
  public abstract void dispose();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\EditorRegion.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */