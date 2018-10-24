package temp.com.mathworks.util;

public abstract interface DTClientLink
{
  public abstract void setStatusText(String paramString);
  
  public abstract void setStatusBar(Object paramObject);
  
  public abstract void destroyAndRemove();
  
  public abstract void restore(boolean paramBoolean);
  
  public abstract void setTitle(String paramString);
  
  public abstract void setActive(boolean paramBoolean);
  
  public abstract void activate();
  
  public abstract void show();
  
  public abstract void activateNextClient(boolean paramBoolean);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\DTClientLink.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */