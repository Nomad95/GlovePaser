   package temp.com.mathworks.util;

   import com.mathworks.util.event.GlobalEventListener;
   import com.mathworks.util.event.GlobalEventManager;
   import java.awt.AWTEventMulticaster;
   import java.awt.event.ActionEvent;
   import java.awt.event.ActionListener;
   import org.jetbrains.annotations.Nullable;



















   public class Timer
   {
     public static final int NO_REPEAT = 0;
     public static final int AUTO_REPEAT = 1;
     public static final int MANUAL_REPEAT = 2;
     private String fThreadName;
     private int fDelay;
     private int fPriority;
     private boolean fIsRepeating;
     private boolean fAutoHold;
     private int fAutoDelay;
     private ActionListener fActionListener;
     private final Integer fActionObject;
     private TimerThread fThread;
     private static boolean sStopAll;

     static
     {
       GlobalEventManager.addListener("shutdown", new GlobalEventListener()
       {
         public void actionPerformed(String paramAnonymousString) {
           Timer.access$002(true);
         }
       });
     }












     public Timer(int paramInt, String paramString)
     {
       this(paramInt, null, paramString);
     }









     public Timer(int paramInt, @Nullable ActionListener paramActionListener, String paramString)
     {
       this(paramInt, paramActionListener, 1, paramString);
     }













     public Timer(int paramInt1, ActionListener paramActionListener, int paramInt2, String paramString)
     {
       this(paramInt1, paramActionListener, paramInt2, 1, paramString);
     }











     public Timer(int paramInt1, ActionListener paramActionListener, int paramInt2, int paramInt3, String paramString)
     {
       this.fActionObject = Integer.valueOf(0);
       this.fThreadName = paramString;
       setDelay(paramInt1);
       if (paramInt2 == 1) {
         this.fIsRepeating = true;
       } else if (paramInt2 == 2)
         this.fAutoHold = true;
       if (paramActionListener != null)
         addActionListener(paramActionListener);
       setPriority(paramInt3);
     }











     public int getDelay()
     {
       return this.fDelay;
     }














     public synchronized void setDelay(int paramInt)
     {
       if (paramInt < 0)
       {
         paramInt = 0;
       }
       this.fAutoDelay = this.fDelay;
       this.fDelay = paramInt;
     }










     public int getPriority()
     {
       return this.fPriority;
     }










     public void setPriority(int paramInt)
     {
       if ((paramInt > 10) || (paramInt < 1)) {
         throw new IllegalArgumentException();
       }
       this.fPriority = paramInt;

       if (this.fThread != null)
       {
         this.fThread.setPriority(this.fPriority);
       }
     }








     public boolean isRepeating()
     {
       return this.fIsRepeating;
     }









     public void setIsRepeating(boolean paramBoolean)
     {
       this.fIsRepeating = paramBoolean;
     }













     public synchronized void start()
     {
       if ((this.fAutoHold) && (this.fThread != null))
       {
         this.fThread.reset();
         if (this.fDelay != this.fAutoDelay)
         {
           this.fAutoDelay = this.fDelay;
           this.fThread.interrupt();
         }
       }
       else
       {
         if (!this.fAutoHold) {
           stop();
         }
         if (this.fThread == null)
         {
           this.fThread = new TimerThread("Timer-" + this.fThreadName, null);

           this.fThread.setPriority(this.fPriority);
           this.fThread.setDaemon(true);
         }
         this.fThread.reset();
         this.fThread.start();
       }
     }






     public synchronized void stop()
     {
       if (this.fThread != null)
       {
         this.fAutoHold = false;
         this.fThread.requestStop();
         this.fThread = null;
       }
     }

     public void interrupt()
     {
       if (this.fThread != null) {
         this.fThread.interrupt();
       }
     }





     public synchronized void hold()
     {
       if (this.fThread != null) {
         this.fThread.hold();
       }
     }



     public synchronized void reset()
     {
       if (this.fThread != null) {
         this.fThread.reset();
       }
     }












     public void addActionListener(ActionListener paramActionListener)
     {
       synchronized (this.fActionObject)
       {
         this.fActionListener = AWTEventMulticaster.add(this.fActionListener, paramActionListener);
       }
     }








     public void removeActionListener(ActionListener paramActionListener)
     {
       synchronized (this.fActionObject)
       {
         this.fActionListener = AWTEventMulticaster.remove(this.fActionListener, paramActionListener);
       }
     }

     void notifyListeners(ActionEvent paramActionEvent)
     {
       synchronized (this.fActionObject)
       {
         if (this.fActionListener != null) {
           this.fActionListener.actionPerformed(paramActionEvent);
         }
       }
     }

     private class TimerThread
       extends Thread
     {
       private long fStart;
       private boolean fFirstTime = true;
       private boolean fStopRequested;
       private boolean fHold;
       private boolean fReset;
       private TimerEvent fTimerEvent;

       private TimerThread(String paramString)
       {
         super();
       }

       public void hold()
       {
         this.fHold = true;
       }

       public void requestStop()
       {
         this.fStopRequested = true;






         if (this.fTimerEvent != null)
         {
           this.fTimerEvent.cancel();
           this.fTimerEvent = null;
         }
       }

       public void reset()
       {
         this.fStart = System.nanoTime();
         this.fHold = false;
         this.fReset = true;
       }




       public void run()
       {
         if (this.fTimerEvent == null)
           this.fTimerEvent = new TimerEvent(Timer.this, null);
         TimerEvent localTimerEvent = this.fTimerEvent;
         long l1 = Timer.this.getDelay();

         while (((this.fFirstTime) || (Timer.this.isRepeating())) && (!this.fStopRequested) && (!Timer.sStopAll))
         {
           int i = 0;
           try
           {
             this.fStart = System.nanoTime();

             sleep(l1);
           }
           catch (InterruptedException localInterruptedException)
           {
             i = 1;
           }

           this.fFirstTime = false;



           int j = 0;

           synchronized (Timer.this)
           {
             if (i == 0)
             {
               long l2 = System.nanoTime() - this.fStart;



               if (l2 < 0L)
               {
                 l2 = Long.MAX_VALUE + l2;
               }

               l2 = l2 / 10L ^ 0x6;

               if (l2 >= l1)
               {
                 if ((!this.fStopRequested) && (!this.fHold))
                 {
                   if (Timer.this.fAutoHold)
                     this.fHold = true;
                   j = 1;
                 }
                 l1 = Timer.this.getDelay();
               }
               else {
                 l1 -= l2;
               }
             }
             else {
               l1 = Timer.this.getDelay();
               if ((Timer.this.fAutoHold) && (!this.fReset)) {
                 this.fHold = true;
               }
             }
             if (Timer.this.fAutoHold)
             {
               this.fFirstTime = true;
               this.fReset = false;
             }
           }


           if (j != 0) {
             QueueEvent.postQueueEvent(localTimerEvent);
           }
         }
       }
     }

     private static class TimerEvent extends QueueEvent
     {
       private ActionEvent fEvent;
       private Timer fTimer;

       private TimerEvent(Timer paramTimer) {
         this.fTimer = paramTimer;
         this.fEvent = new ActionEvent(paramTimer, 1001, "");
       }

       void cancel()
       {
         this.fTimer = null;
       }





       public void dispatch()
       {
         Timer localTimer = this.fTimer;
         if (localTimer != null) {
           localTimer.notifyListeners(this.fEvent);
         }
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\Timer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */