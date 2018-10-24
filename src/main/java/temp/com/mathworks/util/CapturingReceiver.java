   package temp.com.mathworks.util;

   import java.util.LinkedList;
   import java.util.List;




   public class CapturingReceiver<T>
     extends AsyncReceiver<T>
   {
     private final List<T> fData;
     private final AsyncReceiver<T> fWrapped;

     public CapturingReceiver()
     {
       this(null);
     }

     public CapturingReceiver(AsyncReceiver<T> paramAsyncReceiver)
     {
       this.fWrapped = paramAsyncReceiver;
       this.fData = new LinkedList();
     }

     public boolean receive(T paramT)
     {
       boolean bool = (this.fWrapped == null) || (this.fWrapped.receive(paramT));
       if (bool)
       {
         this.fData.add(paramT);
       }
       return bool;
     }

     public void finished()
     {
       if (this.fWrapped != null)
       {
         this.fWrapped.finished();
       }
     }

     public List<T> getCapturedData()
     {
       return new LinkedList(this.fData);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\CapturingReceiver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */