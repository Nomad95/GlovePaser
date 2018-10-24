   package temp.com.mathworks.matlab.api.dataview;

   import java.beans.PropertyChangeListener;
   import java.beans.PropertyChangeSupport;
   import javax.swing.Icon;






















   public class BasicUiInfoProvider
     implements UiInfoProvider
   {
     private String fShortName;
     private String fLongName;
     private Icon fIcon;
     private String fUniqueKey;
     private PropertyChangeSupport fPropertyChangeSupport;

     public BasicUiInfoProvider() {}

     public BasicUiInfoProvider(String paramString1, String paramString2, String paramString3)
     {
       this.fShortName = paramString1;
       this.fLongName = paramString2;
       this.fUniqueKey = paramString3;
     }


     public final String getShortName()
     {
       return this.fShortName;
     }




     public final void setShortName(String paramString)
     {
       String str = this.fShortName;
       this.fShortName = paramString;
       firePropertyChange("shortName", str, paramString);
     }

     public final String getLongName() {
       return this.fLongName;
     }




     public final void setLongName(String paramString)
     {
       String str = this.fLongName;
       this.fLongName = paramString;
       firePropertyChange("longName", str, paramString);
     }

     public final Icon getIcon() {
       return this.fIcon;
     }




     public final void setIcon(Icon paramIcon)
     {
       Icon localIcon = this.fIcon;
       this.fIcon = paramIcon;
       firePropertyChange("icon", localIcon, paramIcon);
     }

     public final String getUniqueKey() {
       return this.fUniqueKey;
     }




     public final void setUniqueKey(String paramString)
     {
       String str = this.fUniqueKey;
       this.fUniqueKey = paramString;
       firePropertyChange("uniqueKey", str, paramString);
     }





     private PropertyChangeSupport getPropertyChangeSupport()
     {
       if (this.fPropertyChangeSupport == null) {
         this.fPropertyChangeSupport = new PropertyChangeSupport(this);
       }

       return this.fPropertyChangeSupport;
     }









     public final void firePropertyChange(String paramString, Object paramObject1, Object paramObject2)
     {
       getPropertyChangeSupport().firePropertyChange(paramString, paramObject1, paramObject2);
     }


     public final void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
     {
       getPropertyChangeSupport().addPropertyChangeListener(paramPropertyChangeListener);
     }

     public final void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener)
     {
       getPropertyChangeSupport().addPropertyChangeListener(paramString, paramPropertyChangeListener);
     }


     public final void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
     {
       getPropertyChangeSupport().removePropertyChangeListener(paramPropertyChangeListener);
     }

     public final void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener)
     {
       getPropertyChangeSupport().removePropertyChangeListener(paramString, paramPropertyChangeListener);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\dataview\BasicUiInfoProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */