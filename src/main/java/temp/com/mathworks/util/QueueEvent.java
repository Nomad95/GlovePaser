   package temp.com.mathworks.util;

   import java.awt.AWTEvent;
   import java.awt.Component;
   import java.awt.EventQueue;
   import java.awt.Toolkit;





   public abstract class QueueEvent
     extends AWTEvent
   {
     private static final int QUEUE_ID = 2076;
     private static QueueTarget sTarget = new QueueTarget();
     private static EventQueue sQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();



     private boolean fWait;




     public static void postQueueEvent(QueueEvent paramQueueEvent)
     {
       if (sQueue != null) {
         sQueue.postEvent(paramQueueEvent);
       }
     }







     public static void postAndWait(QueueEvent paramQueueEvent)
     {
       synchronized (paramQueueEvent)
       {
         if (sQueue != null) {
           paramQueueEvent.fWait = true;
           sQueue.postEvent(paramQueueEvent);
           try
           {
             paramQueueEvent.wait();
           }
           catch (InterruptedException localInterruptedException) {}
         }
       }
     }





     public QueueEvent()
     {
       super(sTarget, 2076);
     }




     public abstract void dispatch();




     private static class QueueTarget
       extends Component
     {
       QueueTarget()
       {
         enableEvents(2076L);
       }

       protected void processEvent(AWTEvent paramAWTEvent)
       {
         if ((paramAWTEvent instanceof QueueEvent))
         {
           QueueEvent localQueueEvent = (QueueEvent)paramAWTEvent;
           localQueueEvent.dispatch();
           if (localQueueEvent.fWait)
           {
             synchronized (localQueueEvent)
             {
               localQueueEvent.notify();
             }
           }
         }
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\QueueEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */