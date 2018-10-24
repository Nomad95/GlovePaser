   package temp.com.mathworks.util;

   import org.apache.commons.lang.builder.ToStringBuilder;

















   public final class Result<V>
   {
     private volatile V value;
     private volatile boolean isOk;
     private volatile String errorMsg;

     public Result()
     {
       this.isOk = false;
     }








     public boolean isOk()
     {
       return this.isOk;
     }





     public V get()
     {
       return (V)this.value;
     }





     public void setErrorMessage(String paramString)
     {
       this.errorMsg = paramString;
     }





     public String getErrorMessage()
     {
       return this.errorMsg;
     }







     public synchronized Result<V> set(V paramV)
     {
       this.value = paramV;
       this.isOk = true;
       return this;
     }

     public synchronized String toString()
     {
       return ToStringBuilder.reflectionToString(this);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\Result.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */