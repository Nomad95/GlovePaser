   package temp.com.mathworks.util;





   public class InvalidValueException
     extends RuntimeException
   {
     private final Object value;



     private volatile Object lastGoodValue;




     public InvalidValueException(Object paramObject, String paramString)
     {
       super(paramString);

       this.value = paramObject;
     }

     public Object getValue()
     {
       return this.value;
     }

     public void setLastGoodValue(Object paramObject)
     {
       this.lastGoodValue = paramObject;
     }

     public Object getLastGoodValue()
     {
       return this.lastGoodValue;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\InvalidValueException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */