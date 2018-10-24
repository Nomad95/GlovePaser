   package temp.com.mathworks.util.event;

   import java.awt.AWTEvent;
   import java.awt.Component;
   import java.awt.KeyboardFocusManager;
   import java.awt.Window;
   import java.awt.event.AWTEventListener;
   import java.awt.event.KeyEvent;
   import java.awt.event.MouseEvent;
   import javax.swing.SwingUtilities;




   public abstract class AbstractInputEventsDispatcher
   {
     protected final EventListenerList<AWTMouseListener> fAWTMouseListeners;
     protected final EventListenerList<AWTKeyListener> fAWTKeyListeners;
     protected AWTEventListener fAWTEventListener;

     public AbstractInputEventsDispatcher()
     {
       this.fAWTMouseListeners = EventListenerList.newEventListenerList(AWTMouseListener.class);

       this.fAWTKeyListeners = EventListenerList.newEventListenerList(AWTKeyListener.class);
     }

     public void addAWTMouseListener(AWTMouseListener paramAWTMouseListener)
     {
       synchronized (this.fAWTMouseListeners) {
         createAWTEventListener();
         this.fAWTMouseListeners.addListener(paramAWTMouseListener);
       }
     }

     public void addAWTKeyListener(AWTKeyListener paramAWTKeyListener) {
       synchronized (this.fAWTMouseListeners) {
         createAWTEventListener();
         this.fAWTKeyListeners.addListener(paramAWTKeyListener);
       }
     }

     protected void createAWTEventListener() {
       if (this.fAWTEventListener == null) {
         this.fAWTEventListener = new AWTEventListener() {
           public void eventDispatched(AWTEvent paramAnonymousAWTEvent) {
             if ((paramAnonymousAWTEvent instanceof KeyEvent)) {
               AbstractInputEventsDispatcher.this.dispatchKeyEvent((KeyEvent)paramAnonymousAWTEvent);
             } else {
               AbstractInputEventsDispatcher.this.dispatchMouseEvent((MouseEvent)paramAnonymousAWTEvent);
             }
           }
         };
         EventUtils.addAWTEventListener(this.fAWTEventListener, 56L);
       }
     }


     protected abstract void dispatchKeyEvent(KeyEvent paramKeyEvent);


     protected void dispatchMouseEvent(MouseEvent paramMouseEvent)
     {
       Component localComponent1 = paramMouseEvent.getComponent();
       Window localWindow = null;
       Component localComponent2 = null;
       if (localComponent1 != null) {
         localWindow = (localComponent1 instanceof Window) ? (Window)localComponent1 : SwingUtilities.getWindowAncestor(localComponent1);
         if (localWindow != null) {
           localComponent2 = SwingUtilities.getDeepestComponentAt(localComponent1, paramMouseEvent.getX(), paramMouseEvent.getY());
         }
       }
       ((AWTMouseListener)this.fAWTMouseListeners.fire()).mouseEventDispatched(paramMouseEvent, localWindow, localComponent2);
     }

     public void removeAWTKeyListener(AWTKeyListener paramAWTKeyListener) {
       synchronized (this.fAWTMouseListeners) {
         this.fAWTKeyListeners.removeListener(paramAWTKeyListener);
         attempRemoveAWTListener();
       }
     }

     public void removeAWTMouseListener(AWTMouseListener paramAWTMouseListener) {
       synchronized (this.fAWTMouseListeners) {
         this.fAWTMouseListeners.removeListener(paramAWTMouseListener);
         attempRemoveAWTListener();
       }
     }

     private void attempRemoveAWTListener() {
       if ((this.fAWTMouseListeners.isEmpty()) && (this.fAWTKeyListeners.isEmpty())) {
         EventUtils.removeAWTEventListener(this.fAWTEventListener);
         this.fAWTEventListener = null;
       }
     }

     private static class DefaultInputEventsDispatcher extends AbstractInputEventsDispatcher {
       protected void dispatchKeyEvent(KeyEvent paramKeyEvent) {
         Window localWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
         ((AWTKeyListener)this.fAWTKeyListeners.fire()).keyEventDispatched(paramKeyEvent, localWindow);
       }
     }

     private static final AbstractInputEventsDispatcher DEFAULT = new DefaultInputEventsDispatcher(null);

     public static AbstractInputEventsDispatcher getDefault() {
       return DEFAULT;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\event\AbstractInputEventsDispatcher.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */