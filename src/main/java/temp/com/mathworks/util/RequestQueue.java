   package temp.com.mathworks.util;

   import com.mathworks.util.event.GlobalEventListener;
   import com.mathworks.util.event.GlobalEventManager;
   import java.util.ArrayList;
   import java.util.Iterator;
   import java.util.LinkedList;
   import java.util.List;
   import java.util.Queue;
   import javax.swing.SwingUtilities;

















   public class RequestQueue
   {
     private final String fName;
     private final Queue<Runnable> fQueue;
     private Thread fThread;
     private static final long KEEP_ALIVE_MILLISECONDS = 10000L;
     public static final RequestQueue EDT = new RequestQueue("EDT RequestQueue")
     {

       public void request(Runnable paramAnonymousRunnable)
       {
         if (SwingUtilities.isEventDispatchThread())
         {
           paramAnonymousRunnable.run();
         }
         else
         {
           SwingUtilities.invokeLater(paramAnonymousRunnable);
         }
       }
     };

     public RequestQueue(String paramString)
     {
       this.fName = paramString;
       this.fQueue = new LinkedList();
     }

     public void request(Runnable paramRunnable)
     {
       synchronized (this.fQueue)
       {
         this.fQueue.offer(paramRunnable);
         startThreadIfNecessary();
         this.fQueue.notifyAll();
       }
     }

     private void startThreadIfNecessary()
     {
       synchronized (this.fQueue)
       {
         if (this.fThread == null)
         {
           this.fThread = new Thread(new Runnable()
           {
             public void run()
             {
               try
               {
                 for (;;) {
                   RequestQueue.this.execute();
                   if (!RequestQueue.this.waitForRequestUntilTimeout())
                   {
                     synchronized (RequestQueue.this.fQueue)
                     {
                       RequestQueue.this.fThread = null;
                     }
                     break;
                   }
                 }




                 return;
               }
               catch (InterruptedException localInterruptedException)
               {
                 Thread.currentThread().interrupt();
               }

             }


           });
           this.fThread.setName(this.fName);
           this.fThread.setDaemon(true);
           this.fThread.start();

           GlobalEventManager.addListener("shutdown", new GlobalEventListener()
           {
             public void actionPerformed(String paramAnonymousString) {
               Thread localThread = RequestQueue.this.fThread;
               if (localThread != null) {
                 localThread.interrupt();
                 try {
                   localThread.join();
                 }
                 catch (InterruptedException localInterruptedException) {}
               }
             }
           });
         }
       }
     }

     private void execute() throws InterruptedException
     {
       ArrayList localArrayList;
       synchronized (this.fQueue)
       {
         localArrayList = new ArrayList(this.fQueue);
         this.fQueue.clear();
       }

       for (??? = localArrayList.iterator(); ((Iterator)???).hasNext();) { Runnable localRunnable = (Runnable)((Iterator)???).next();

         if (Thread.currentThread().isInterrupted()) throw new InterruptedException();
         localRunnable.run();
       }
     }

     private boolean waitForRequestUntilTimeout() throws InterruptedException
     {
       synchronized (this.fQueue)
       {
         if (this.fQueue.isEmpty())
         {

           this.fQueue.wait(10000L);
           return !this.fQueue.isEmpty();
         }

         return true;
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\RequestQueue.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */