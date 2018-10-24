   package temp.com.mathworks.mvm.eventmgr;

   import java.lang.reflect.Method;


















   public class EventFiring
     extends EventListening
   {
     private final Method fCppEventFactory;

     public <E,  extends FirableMvmEvent> EventFiring(Class<E> paramClass)
       throws InvalidEventTypeException
     {
       super(paramClass);
       this.fCppEventFactory = getCppEventFactory(paramClass);
     }



     Method getCppFactory()
     {
       return this.fCppEventFactory;
     }





     private static Method getCppEventFactory(Class<? extends FirableMvmEvent> paramClass)
       throws InvalidEventTypeException
     {
       try
       {
         return InsecureReflection.getMethod(paramClass, 266, "nativeFactory", Long.TYPE, new Class[] { paramClass });
       }
       catch (NoSuchMethodException localNoSuchMethodException)
       {
         throw new InvalidEventTypeException(localNoSuchMethodException);
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\EventFiring.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */