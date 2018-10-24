   package temp.com.mathworks.util;



   public final class ExceptionHolder<T extends Throwable>
     extends Holder<T>
   {
     public void throwIfAny()
       throws Throwable
     {
       if (get() != null) {
         throw ((Throwable)get());
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\ExceptionHolder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */