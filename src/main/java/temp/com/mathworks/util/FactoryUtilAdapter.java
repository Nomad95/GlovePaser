   package temp.com.mathworks.util;

   import java.lang.reflect.Method;








   public class FactoryUtilAdapter
     implements FactoryUtilSupplier
   {
     public Object getValue() { return null; }
     public void callVoidMethod(Object paramObject) {}
     public boolean callBooleanMethod(Object paramObject) { return false; }



     protected FactoryUtilSupplier getSupplier(String paramString1, String paramString2)
     {
       FactoryUtilSupplier localFactoryUtilSupplier = null;

       try
       {
         Class localClass = Class.forName(paramString1);
         if (localClass != null)
         {
           Method localMethod = localClass.getMethod(paramString2, new Class[0]);
           if (localMethod != null)
           {
             localFactoryUtilSupplier = (FactoryUtilSupplier)localMethod.invoke(null, new Object[0]);
           }
         }
       }
       catch (Exception localException) {}



       return localFactoryUtilSupplier;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\FactoryUtilAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */