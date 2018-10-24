   package temp.com.mathworks.util;



   public final class CancellationDetectingReceiver<T>
     extends AsyncReceiver<T>
   {
     private final AsyncReceiver<T> fWrapped;

     private boolean fCanceled;


     public CancellationDetectingReceiver(AsyncReceiver<T> paramAsyncReceiver)
     {
       this.fWrapped = paramAsyncReceiver;
     }

     public boolean receive(T paramT)
     {
       boolean bool = this.fWrapped.receive(paramT);
       if (!bool)
       {
         this.fCanceled = true;
       }
       return bool;
     }

     public void finished()
     {
       this.fWrapped.finished();
     }

     public boolean wasCanceled()
     {
       return this.fCanceled;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\CancellationDetectingReceiver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */