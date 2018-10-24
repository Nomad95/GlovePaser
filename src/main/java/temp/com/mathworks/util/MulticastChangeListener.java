   package temp.com.mathworks.util;

   import java.util.ArrayList;
   import java.util.List;
   import javax.swing.event.ChangeEvent;
   import javax.swing.event.ChangeListener;




   public class MulticastChangeListener
     implements ChangeListener
   {
     private final List<ChangeListener> fListeners;
     private boolean fDisposed;

     public MulticastChangeListener()
     {
       this.fListeners = new ArrayList();
     }

     public void stateChanged(ChangeEvent paramChangeEvent)
     {
       for (ChangeListener localChangeListener : getListeners()) {
         localChangeListener.stateChanged(paramChangeEvent);
       }
     }


     public List<ChangeListener> getListeners()
     {
       // Byte code:
       //   0: aload_0
       //   1: getfield 4	com/mathworks/util/MulticastChangeListener:fListeners	Ljava/util/List;
       //   4: dup
       //   5: astore_1
       //   6: monitorenter
       //   7: new 2	java/util/ArrayList
       //   10: dup
       //   11: aload_0
       //   12: getfield 4	com/mathworks/util/MulticastChangeListener:fListeners	Ljava/util/List;
       //   15: invokespecial 11	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
       //   18: aload_1
       //   19: monitorexit
       //   20: areturn
       //   21: astore_2
       //   22: aload_1
       //   23: monitorexit
       //   24: aload_2
       //   25: athrow
       // Line number table:
       //   Java source line #30	-> byte code offset #0
       //   Java source line #32	-> byte code offset #7
       //   Java source line #33	-> byte code offset #21
       // Local variable table:
       //   start	length	slot	name	signature
       //   0	26	0	this	MulticastChangeListener
       //   5	18	1	Ljava/lang/Object;	Object
       //   21	4	2	localObject1	Object
       // Exception table:
       //   from	to	target	type
       //   7	20	21	finally
       //   21	24	21	finally
     }

     public void addChangeListener(ChangeListener paramChangeListener)
     {
       synchronized (this.fListeners)
       {
         if (!this.fDisposed) {
           this.fListeners.add(paramChangeListener);
         }
       }
     }

     public void removeChangeListener(ChangeListener paramChangeListener) {
       synchronized (this.fListeners)
       {
         this.fListeners.remove(paramChangeListener);
       }
     }

     public void dispose()
     {
       synchronized (this.fListeners)
       {
         this.fListeners.clear();
         this.fDisposed = true;
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\MulticastChangeListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */