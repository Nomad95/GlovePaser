   package temp.com.mathworks.util;

   import java.awt.AWTEvent;
   import java.awt.Component;
   import java.awt.EventQueue;
   import java.awt.Toolkit;
   import java.awt.Window;



   public class NativeEvent
     extends AWTEvent
   {
     private static EventQueue sQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();

     private int fMessage;
     private long fwParam;
     private long flParam;
     public static int WM_USER = 1024;

     public static int WM_MOUSEWHEEL = 522;
     public static int WM_IME_CHAR = 646;




     public static int JM_FRAMEENTERED = WM_USER + 1;
     public static int JM_FRAMEEXITED = WM_USER + 2;
     public static int JM_FRAMEPRESSED = WM_USER + 3;
     public static int JM_FRAMEDRAGGED = WM_USER + 4;
     public static int JM_FRAMERELEASED = WM_USER + 5;




     public static synchronized void postToAWTEventQueue(long paramLong1, int paramInt, long paramLong2, long paramLong3)
     {
       Window localWindow = NativeJava.windowReferenceFromHWND(paramLong1);
       NativeEvent localNativeEvent = new NativeEvent(localWindow, 2076);

       localNativeEvent.setNativeMessage(paramInt);
       localNativeEvent.setWParam(paramLong2);
       localNativeEvent.setLParam(paramLong3);
       sQueue.postEvent(localNativeEvent);
     }

     public NativeEvent(Component paramComponent, int paramInt)
     {
       super(paramComponent, paramInt);
     }


     public void setNativeMessage(int paramInt) { this.fMessage = paramInt; }
     public int getNativeMessage() { return this.fMessage; }
     public void setWParam(long paramLong) { this.fwParam = paramLong; }
     public long getWParam() { return this.fwParam; }
     public void setLParam(long paramLong) { this.flParam = paramLong; }
     public long getLParam() { return this.flParam; }

     public static abstract interface Listener
     {
       public abstract void framePressed(NativeEvent paramNativeEvent);

       public abstract void frameReleased(NativeEvent paramNativeEvent);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\NativeEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */