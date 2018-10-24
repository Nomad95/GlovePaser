package temp.com.mathworks.matlab.api.debug;

import java.awt.Component;

public abstract interface ViewProvider
{
  public abstract Component getView(ViewProviderKey paramViewProviderKey);
  
  public abstract Component clearView(ViewProviderKey paramViewProviderKey);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\debug\ViewProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */