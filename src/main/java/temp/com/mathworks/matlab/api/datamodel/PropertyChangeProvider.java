package temp.com.mathworks.matlab.api.datamodel;

import java.beans.PropertyChangeListener;

public abstract interface PropertyChangeProvider
{
  public abstract void firePropertyChange(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);
  
  public abstract void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener);
  
  public abstract void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);
  
  public abstract void removePropertyChangeListener(String paramString,
          PropertyChangeListener paramPropertyChangeListener);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\datamodel\PropertyChangeProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */