   package temp.com.mathworks.mvm.eventmgr;

   import java.lang.reflect.Method;






































   public class EventListening
   {
     private final Method fStaticEventType;
     private final Method fJavaEventFactory;
     private final Class<? extends MvmEvent> fClass;

     public <E,  extends MvmTypedEvent> EventListening(Class<E> paramClass)
       throws InvalidEventTypeException
     {
       this.fClass = paramClass;
       this.fStaticEventType = getEventTypeMethod(paramClass);
       this.fJavaEventFactory = getJavaEventFactory(paramClass);
     }












     public EventListening()
       throws InvalidEventTypeException
     {
       this.fClass = MvmDynamicEvent.class;
       this.fStaticEventType = null;
       this.fJavaEventFactory = getJavaEventFactory(MvmDynamicEvent.class);
     }




     public Class<? extends MvmEvent> getEventClass()
     {
       return this.fClass;
     }





     public String getStaticEventType()
       throws InvalidEventTypeException
     {
       try
       {
         return this.fStaticEventType.invoke(null, new Object[0]).toString();
       } catch (Throwable localThrowable) {
         if (!$assertionsDisabled) throw new AssertionError(localThrowable.toString());
         throw new InvalidEventTypeException(localThrowable);
       }
     }




     Method getJavaFactory()
     {
       return this.fJavaEventFactory;
     }





     private static Method getEventTypeMethod(Class<? extends MvmTypedEvent> paramClass)
       throws InvalidEventTypeException
     {
       try
       {
         return InsecureReflection.getMethod(paramClass, 9, "getStaticEventType", String.class, new Class[0]);

       }
       catch (NoSuchMethodException localNoSuchMethodException)
       {
         throw new InvalidEventTypeException("All MvmTypedEvent types require a static public method named getStaticEventType that returns the same string value as the C++ static method EventType() of the corresponding C++ type.", localNoSuchMethodException);
       }
     }












     private static Method getJavaEventFactory(Class<? extends MvmEvent> paramClass)
       throws InvalidEventTypeException
     {
       try
       {
         return InsecureReflection.getMethod(paramClass, 266, "nativeFactory", paramClass, new Class[] { Long.TYPE });

       }
       catch (NoSuchMethodException localNoSuchMethodException)
       {
         throw new InvalidEventTypeException("All MvmEvent types require a static private native method named nativeFactory that returns " + paramClass.toString() + " and has a long parameter.  The long is actually the C++" + " mlutil::eventmgr::BaseEvent const pointer used to" + " construct the " + paramClass.toString() + ".", localNoSuchMethodException);
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\EventListening.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */