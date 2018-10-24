   package temp.com.mathworks.util;








   public class EmptyCheckReceiver<T>
     extends AsyncReceiver<T>
   {
     private boolean fEmpty = true;
     private boolean fValid = false;

     public boolean receive(Object paramObject)
     {
       this.fEmpty = false;
       this.fValid = true;
       return false;
     }

     public void finished()
     {
       this.fValid = true;
     }

     public boolean isEmpty()
     {
       assert (this.fValid);
       return this.fEmpty;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\EmptyCheckReceiver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */