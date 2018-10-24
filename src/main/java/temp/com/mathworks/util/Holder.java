   package temp.com.mathworks.util;



   public class Holder<T>
   {
     private T fValue;



     public Holder() {}



     public Holder(T paramT)
     {
       this.fValue = paramT;
     }

     public void set(T paramT)
     {
       this.fValue = paramT;
     }

     public T get()
     {
       return (T)this.fValue;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\Holder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */