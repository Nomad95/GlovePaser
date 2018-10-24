package temp.com.mathworks.matlab.api.dataview;

import com.mathworks.matlab.api.datamodel.PropertyChangeProvider;
import javax.swing.Icon;

public abstract interface UiInfoProvider
  extends PropertyChangeProvider
{
  public static final String SHORT_NAME = "shortName";
  public static final String LONG_NAME = "longName";
  public static final String ICON = "icon";
  public static final String UNIQUE_KEY = "uniqueKey";
  
  public abstract String getShortName();
  
  public abstract String getLongName();
  
  public abstract Icon getIcon();
  
  public abstract String getUniqueKey();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\dataview\UiInfoProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */