   package temp.com.mathworks.util.event;

   import com.mathworks.util.Disposable;
   import com.mathworks.util.Disposer;
   import java.awt.Component;
   import java.awt.Container;
   import java.awt.Toolkit;
   import java.awt.event.AWTEventListener;
   import java.awt.event.ComponentListener;
   import java.awt.event.ContainerListener;
   import java.awt.event.FocusListener;
   import java.awt.event.HierarchyBoundsListener;
   import java.awt.event.HierarchyListener;
   import java.awt.event.KeyListener;
   import java.awt.event.MouseListener;
   import java.awt.event.MouseMotionListener;
   import java.beans.PropertyChangeListener;
   import javax.swing.JComponent;
   import javax.swing.event.AncestorListener;












   public class EventUtils
   {
     public static void addAWTEventListener(AWTEventListener paramAWTEventListener, long paramLong, Disposable paramDisposable)
     {
       Toolkit.getDefaultToolkit().addAWTEventListener(paramAWTEventListener, paramLong);
       Disposer.register(new Disposable()
       {
         public void dispose() { Toolkit.getDefaultToolkit().removeAWTEventListener(this.val$listener); } }, paramDisposable);
     }








     public static void addAWTEventListener(AWTEventListener paramAWTEventListener, long paramLong)
     {
       Toolkit.getDefaultToolkit().addAWTEventListener(paramAWTEventListener, paramLong);
     }





     public static void removeAWTEventListener(AWTEventListener paramAWTEventListener)
     {
       Toolkit.getDefaultToolkit().removeAWTEventListener(paramAWTEventListener);
     }









     public static void addAncestorListener(JComponent paramJComponent, final AncestorListener paramAncestorListener, Disposable paramDisposable)
     {
       paramJComponent.addAncestorListener(paramAncestorListener);
       Disposer.register(paramDisposable, new Disposable() {
         public void dispose() {
           this.val$c.removeAncestorListener(paramAncestorListener);
         }
       });
     }









     public static void addContainerListener(Container paramContainer, final ContainerListener paramContainerListener, Disposable paramDisposable)
     {
       paramContainer.addContainerListener(paramContainerListener);
       Disposer.register(new Disposable()
       {
         public void dispose() { this.val$c.removeContainerListener(paramContainerListener); } }, paramDisposable);
     }











     public static void addPropertyChangeListener(Component paramComponent, final PropertyChangeListener paramPropertyChangeListener, Disposable paramDisposable)
     {
       paramComponent.addPropertyChangeListener(paramPropertyChangeListener);
       Disposer.register(new Disposable()
       {
         public void dispose() { this.val$c.removePropertyChangeListener(paramPropertyChangeListener); } }, paramDisposable);
     }











     public static void addComponentListener(Component paramComponent, final ComponentListener paramComponentListener, Disposable paramDisposable)
     {
       paramComponent.addComponentListener(paramComponentListener);
       Disposer.register(new Disposable()
       {
         public void dispose() { this.val$c.removeComponentListener(paramComponentListener); } }, paramDisposable);
     }











     public static void addMouseListener(Component paramComponent, final MouseListener paramMouseListener, Disposable paramDisposable)
     {
       paramComponent.addMouseListener(paramMouseListener);
       Disposer.register(new Disposable()
       {
         public void dispose() { this.val$c.removeMouseListener(paramMouseListener); } }, paramDisposable);
     }











     public static void addMouseMotionListener(Component paramComponent, final MouseMotionListener paramMouseMotionListener, Disposable paramDisposable)
     {
       paramComponent.addMouseMotionListener(paramMouseMotionListener);
       Disposer.register(new Disposable()
       {
         public void dispose() { this.val$c.removeMouseMotionListener(paramMouseMotionListener); } }, paramDisposable);
     }











     public static void addFocusListener(Component paramComponent, final FocusListener paramFocusListener, Disposable paramDisposable)
     {
       paramComponent.addFocusListener(paramFocusListener);
       Disposer.register(new Disposable()
       {
         public void dispose() { this.val$c.removeFocusListener(paramFocusListener); } }, paramDisposable);
     }











     public static void addHierarchyBoundsListener(Component paramComponent, final HierarchyBoundsListener paramHierarchyBoundsListener, Disposable paramDisposable)
     {
       paramComponent.addHierarchyBoundsListener(paramHierarchyBoundsListener);
       Disposer.register(new Disposable()
       {
         public void dispose() { this.val$c.removeHierarchyBoundsListener(paramHierarchyBoundsListener); } }, paramDisposable);
     }











     public static void addHierarchyListener(Component paramComponent, final HierarchyListener paramHierarchyListener, Disposable paramDisposable)
     {
       paramComponent.addHierarchyListener(paramHierarchyListener);
       Disposer.register(new Disposable()
       {
         public void dispose() { this.val$c.removeHierarchyListener(paramHierarchyListener); } }, paramDisposable);
     }











     public static void addKeyListener(Component paramComponent, final KeyListener paramKeyListener, Disposable paramDisposable)
     {
       paramComponent.addKeyListener(paramKeyListener);
       Disposer.register(new Disposable()
       {
         public void dispose() { this.val$c.removeKeyListener(paramKeyListener); } }, paramDisposable);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\event\EventUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */