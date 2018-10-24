   package temp.com.mathworks.util.event;

   import com.mathworks.util.Disposable;
   import com.mathworks.util.Disposer;
   import java.lang.ref.WeakReference;
   import java.lang.reflect.InvocationHandler;
   import java.lang.reflect.InvocationTargetException;
   import java.lang.reflect.Method;
   import java.lang.reflect.Proxy;
   import java.util.ArrayList;
   import java.util.Arrays;
   import java.util.List;




























   public class EventListenerList<L>
   {
     private transient Object[] fListeners;
     private int fSize;
     private final L fProxy;

     public static <L> EventListenerList<L> newEventListenerList(Class<? extends L> paramClass)
     {
       return new EventListenerList(paramClass);
     }

     public EventListenerList(Class<? extends L> paramClass) {
       this.fListeners = new Object[10];


       this.fProxy = Proxy.newProxyInstance(paramClass.getClassLoader(), new Class[] { paramClass }, new InvocationHandler()
       {
         public Object invoke(Object paramAnonymousObject, Method paramAnonymousMethod, Object[] paramAnonymousArrayOfObject) throws Throwable {
           Object[] arrayOfObject1;
           synchronized (EventListenerList.this.fProxy) {
             if (EventListenerList.this.fSize == 0) return null;
             arrayOfObject1 = new Object[EventListenerList.this.fSize];
             System.arraycopy(EventListenerList.this.fListeners, 0, arrayOfObject1, 0, EventListenerList.this.fSize);
           }
           paramAnonymousMethod.setAccessible(true);
           ??? = null;
           for (Object localObject2 : arrayOfObject1) {
             try {
               if ((localObject2 instanceof WeakReference)) {
                 localObject2 = ((WeakReference)localObject2).get();
               }
               if (localObject2 != null) paramAnonymousMethod.invoke(localObject2, paramAnonymousArrayOfObject);
             } catch (InvocationTargetException localInvocationTargetException) {
               if (??? == null) ??? = new ArrayList();
               ((List)???).add(localInvocationTargetException.getTargetException());
             } catch (Exception localException) {
               if (??? == null) ??? = new ArrayList();
               ((List)???).add(localException);
             }
           }

           if (??? != null) {
             throw new EventListenerInvocationException("Error firing event", (Throwable[])((List)???).toArray(new Throwable[((List)???).size()]));
           }


           return null;
         }
       });
     }









     public boolean addListener(final L paramL, Disposable paramDisposable)
     {
       boolean bool = addListenerInternal(paramL, false);
       if (bool) {
         Disposer.register(new Disposable()
         {
           public void dispose() { EventListenerList.this.removeListener(paramL); } }, paramDisposable);
       }



       return bool;
     }






     public boolean addListener(L paramL)
     {
       return addListenerInternal(paramL, false);
     }









     public boolean addWeakListener(final L paramL, Disposable paramDisposable)
     {
       boolean bool = addListenerInternal(paramL, true);
       if (bool) {
         Disposer.register(new Disposable()
         {
           public void dispose() { EventListenerList.this.removeListener(paramL); } }, paramDisposable);
       }



       return bool;
     }






     public boolean addWeakListener(L paramL)
     {
       return addListenerInternal(paramL, true);
     }

     private boolean addListenerInternal(L paramL, boolean paramBoolean) {
       synchronized (this.fProxy) {
         try {
           if (contains(paramL)) {
             bool = false;






             removeCollectedWeak();return bool;
           }
           ensureCapacity(this.fSize + 1);
           this.fListeners[(this.fSize++)] = (paramBoolean ? new WeakReference(paramL) : paramL);
           boolean bool = true;


           removeCollectedWeak();return bool; } finally { removeCollectedWeak();
         }
       }
     }






     public boolean removeListener(L paramL)
     {
       synchronized (this.fProxy) {
         try {
           int i = indexOf(paramL);
           if (i >= 0) {
             remove(i);
             bool = true;




             removeCollectedWeak();return bool;
           }
           boolean bool = false;


           removeCollectedWeak();return bool; } finally { removeCollectedWeak();
         }
       }
     }

     private void remove(int paramInt) {
       int i = this.fSize - paramInt - 1;
       if (i > 0)
         System.arraycopy(this.fListeners, paramInt + 1, this.fListeners, paramInt, i);
       this.fListeners[(--this.fSize)] = null;
     }

     private void ensureCapacity(int paramInt) {
       int i = this.fListeners.length;
       if (paramInt > i) {
         int j = i * 3 / 2 + 1;
         if (j < paramInt) {
           j = paramInt;
         }
         this.fListeners = Arrays.copyOf(this.fListeners, j);
       }
     }


























     public boolean isEmpty()
     {
       synchronized (this.fProxy) {
         return this.fSize == 0;
       }
     }



     public void clear()
     {
       synchronized (this.fProxy)
       {
         for (int i = 0; i < this.fSize; i++) {
           this.fListeners[i] = null;
         }
         this.fSize = 0;
       }
     }

     private void removeCollectedWeak() {
       int i = 0;
       for (int j = 0; j < this.fSize; j++) {
         Object localObject1 = this.fListeners[j];
         if ((localObject1 instanceof WeakReference)) {
           if (((WeakReference)localObject1).get() == null) {
             this.fListeners[j] = null;
           } else {
             i++;
           }
         } else {
           i++;
         }
       }

       if (i != this.fSize) {
         Object[] arrayOfObject = new Object[i];
         int k = 0; for (int m = 0; k < this.fListeners.length; k++) {
           Object localObject2 = this.fListeners[k];
           if (localObject2 != null)
             arrayOfObject[(m++)] = localObject2;
         }
         this.fListeners = arrayOfObject;
         this.fSize = i;
       }
     }





     public L fire()
     {
       return (L)this.fProxy;
     }

     private boolean contains(Object paramObject) {
       return indexOf(paramObject) >= 0;
     }

     private int indexOf(Object paramObject) {
       for (int i = 0; i < this.fSize; i++) {
         Object localObject = this.fListeners[i];
         if ((paramObject == localObject) || (((localObject instanceof WeakReference)) && (((WeakReference)localObject).get() == paramObject)))
           return i;
       }
       return -1;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\event\EventListenerList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */