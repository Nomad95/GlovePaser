   package temp.com.mathworks.util;







   public class FinishBlockingReceiver<T>
     extends AsyncReceiver<T>
   {
     private final AsyncReceiver<T> fWrapped;






     private int fCount;






     public FinishBlockingReceiver(AsyncReceiver<T> paramAsyncReceiver)
     {
       this.fWrapped = paramAsyncReceiver;
       this.fCount = 0;
     }

     public boolean receive(T paramT)
     {
       this.fCount += 1;
       return this.fWrapped.receive(paramT);
     }


     public void finished()
     {
       int i = this.fCount;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\FinishBlockingReceiver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */