   package temp.com.mathworks.util;

   import java.awt.Component;
   import java.util.Enumeration;







   public class DialogHelper
   {
     public static void enableAllComponents(Enumeration paramEnumeration, boolean paramBoolean)
     {
       while (paramEnumeration.hasMoreElements()) {
         Component localComponent = (Component)paramEnumeration.nextElement();
         localComponent.setEnabled(paramBoolean);
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\DialogHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */