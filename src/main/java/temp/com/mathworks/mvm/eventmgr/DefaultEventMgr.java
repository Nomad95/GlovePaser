   package temp.com.mathworks.mvm.eventmgr;

   import com.mathworks.mvm.MVM;
   import com.mathworks.mvm.MvmFactoryWrapper;
   import com.mathworks.mvm.MvmWrapper;
   import java.lang.reflect.Method;
   import org.jetbrains.annotations.Nullable;










   public class DefaultEventMgr
     implements EventMgr
   {
     private final Method fListenerMethod = getListenerMethod();


     private final NativeMethods fNativeMethods;



     public DefaultEventMgr()
     {
       this.fNativeMethods = new SessionNativeMethods();
     }





     public DefaultEventMgr(MvmFactoryWrapper paramMvmFactoryWrapper)
     {
       this.fNativeMethods = new FactoryNativeMethods(paramMvmFactoryWrapper);
     }





     public DefaultEventMgr(MvmWrapper paramMvmWrapper)
     {
       this.fNativeMethods = new MvmNativeMethods(paramMvmWrapper);
     }





     public <E,  extends MvmTypedEvent> void addMvmListener(MvmListener<E> paramMvmListener, Class<E> paramClass)
       throws InvalidEventTypeException
     {
       addMvmListener(paramMvmListener, paramClass, null);
     }





     public <E,  extends MvmTypedEvent> void addMvmListener(MvmListener<E> paramMvmListener, Class<E> paramClass, @Nullable String paramString)
       throws InvalidEventTypeException
     {
       EventListening localEventListening = new EventListening(paramClass);
       if (paramString == null)
       {
         paramString = localEventListening.getStaticEventType();
       }
       Method localMethod = localEventListening.getJavaFactory();

       this.fNativeMethods.addListener(paramMvmListener, paramClass, paramString, localMethod, this.fListenerMethod);
     }




     public <E,  extends MvmEvent> void addMvmListener(MvmListener<E> paramMvmListener, String paramString)
       throws InvalidEventTypeException
     {
       EventListening localEventListening = new EventListening();
       Method localMethod = localEventListening.getJavaFactory();

       this.fNativeMethods.addListener(paramMvmListener, MvmDynamicEvent.class, paramString, localMethod, this.fListenerMethod);
     }




     public <E,  extends MvmTypedEvent> void removeMvmListener(MvmListener<E> paramMvmListener, Class<E> paramClass)
     {
       removeMvmListener(paramMvmListener, paramClass, null);
     }




     public <E,  extends MvmTypedEvent> void removeMvmListener(MvmListener<E> paramMvmListener, Class<E> paramClass, @Nullable String paramString)
     {
       try
       {
         EventListening localEventListening = new EventListening(paramClass);
         if (paramString == null)
         {
           paramString = localEventListening.getStaticEventType();
         }
         this.fNativeMethods.removeListener(paramMvmListener, paramString);
       }
       catch (InvalidEventTypeException localInvalidEventTypeException) {}
     }






     public <E,  extends MvmEvent> void removeMvmListener(MvmListener<E> paramMvmListener, String paramString)
     {
       try
       {
         this.fNativeMethods.removeListener(paramMvmListener, paramString);
       }
       catch (InvalidEventTypeException localInvalidEventTypeException) {}
     }






     public <E extends FirableMvmEvent> void fireMvmEvent(E paramE)
       throws InvalidEventTypeException
     {
       Class localClass = paramE.getClass();
       EventFiring localEventFiring = new EventFiring(localClass);
       Method localMethod = localEventFiring.getCppFactory();

       this.fNativeMethods.notify(paramE, localClass, localMethod);
     }


     public void flush()
     {
       this.fNativeMethods.flush();
     }

     private static Method getListenerMethod()
     {
       Method localMethod = null;
       try
       {
         localMethod = MvmListener.class.getMethod("mvmChanged", new Class[] { MvmEvent.class });
         if ((!$assertionsDisabled) && (null == localMethod)) throw new AssertionError();
       } catch (Exception localException) {
         if (!$assertionsDisabled) throw new AssertionError(localException.toString());
       }
       return localMethod;
     }








     private static abstract interface NativeMethods
     {
       public abstract void addListener(MvmListener<?> paramMvmListener, Class<?> paramClass, String paramString,
        Method paramMethod1, Method paramMethod2);








       public abstract void removeListener(MvmListener<?> paramMvmListener, String paramString);








       public abstract void notify(FirableMvmEvent paramFirableMvmEvent, Class<?> paramClass, Method paramMethod);







       public abstract void flush();
     }







     private static final class FactoryNativeMethods
       implements NativeMethods
     {
       private final long fFactoryHandle;







       FactoryNativeMethods(MvmFactoryWrapper paramMvmFactoryWrapper)
       {
         this.fFactoryHandle = paramMvmFactoryWrapper.getHandle();
       }







       public void addListener(MvmListener<?> paramMvmListener, Class<?> paramClass, String paramString, Method paramMethod1, Method paramMethod2)
       {
         nativeAddListener(this.fFactoryHandle, paramMvmListener, paramClass, paramString, paramMethod1, paramMethod2);
       }





       public void removeListener(MvmListener<?> paramMvmListener, String paramString)
       {
         nativeRemoveListener(this.fFactoryHandle, paramMvmListener, paramString);
       }





       public void notify(FirableMvmEvent paramFirableMvmEvent, Class<?> paramClass, Method paramMethod)
       {
         nativeNotify(this.fFactoryHandle, paramFirableMvmEvent, paramClass, paramMethod);
       }


       public void flush()
       {
         nativeFlush(this.fFactoryHandle);
       }


       private static native void nativeAddListener(long paramLong, MvmListener<?> paramMvmListener, Class<?> paramClass, String paramString, Method paramMethod1, Method paramMethod2);


       private static native void nativeRemoveListener(long paramLong, MvmListener<?> paramMvmListener, String paramString);


       private static native void nativeNotify(long paramLong, FirableMvmEvent paramFirableMvmEvent, Class<?> paramClass, Method paramMethod);

       private static native void nativeFlush(long paramLong);
     }

     private static final class MvmNativeMethods
       implements NativeMethods
     {
       private final long fMvmHandle;

       MvmNativeMethods(MvmWrapper paramMvmWrapper)
       {
         this.fMvmHandle = paramMvmWrapper.get().getHandle();
       }











       public void addListener(MvmListener<?> paramMvmListener, Class<?> paramClass, String paramString, Method paramMethod1, Method paramMethod2)
       {
         nativeAddListener(this.fMvmHandle, paramMvmListener, paramClass, paramString, paramMethod1, paramMethod2);
       }





       public void removeListener(MvmListener<?> paramMvmListener, String paramString)
       {
         nativeRemoveListener(this.fMvmHandle, paramMvmListener, paramString);
       }





       public void notify(FirableMvmEvent paramFirableMvmEvent, Class<?> paramClass, Method paramMethod)
       {
         nativeNotify(this.fMvmHandle, paramFirableMvmEvent, paramClass, paramMethod);
       }


       public void flush()
       {
         nativeFlush(this.fMvmHandle);
       }




       private static native void nativeAddListener(long paramLong, MvmListener<?> paramMvmListener, Class<?> paramClass, String paramString, Method paramMethod1, Method paramMethod2);




       private static native void nativeRemoveListener(long paramLong, MvmListener<?> paramMvmListener, String paramString);




       private static native void nativeNotify(long paramLong, FirableMvmEvent paramFirableMvmEvent, Class<?> paramClass, Method paramMethod);




       private static native void nativeFlush(long paramLong);
     }



     private static final class SessionNativeMethods
       implements NativeMethods
     {
       public void addListener(MvmListener<?> paramMvmListener, Class<?> paramClass, String paramString, Method paramMethod1, Method paramMethod2)
       {
         nativeAddListener(paramMvmListener, paramClass, paramString, paramMethod1, paramMethod2);
       }





       public void removeListener(MvmListener<?> paramMvmListener, String paramString)
       {
         nativeRemoveListener(paramMvmListener, paramString);
       }





       public void notify(FirableMvmEvent paramFirableMvmEvent, Class<?> paramClass, Method paramMethod)
       {
         nativeNotify(paramFirableMvmEvent, paramClass, paramMethod);
       }

       public void flush() {}

       private static native void nativeAddListener(MvmListener<?> paramMvmListener, Class<?> paramClass, String paramString, Method paramMethod1, Method paramMethod2);

       private static native void nativeRemoveListener(MvmListener<?> paramMvmListener, String paramString);

       private static native void nativeNotify(FirableMvmEvent paramFirableMvmEvent, Class<?> paramClass, Method paramMethod);

       private static native void nativeFlush();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\DefaultEventMgr.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */