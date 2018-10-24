   package temp.com.mathworks.util.event;

   import com.mathworks.util.AggregateException;






   public class EventListenerInvocationException
     extends AggregateException
   {
     public EventListenerInvocationException() {}

     public EventListenerInvocationException(String paramString)
     {
       super(paramString);
     }

     public EventListenerInvocationException(Throwable paramThrowable) {
       super(paramThrowable);
     }

     public EventListenerInvocationException(String paramString, Throwable paramThrowable) {
       super(paramString, paramThrowable);
     }

     public EventListenerInvocationException(String paramString, Throwable[] paramArrayOfThrowable) {
       super(paramString, paramArrayOfThrowable);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\event\EventListenerInvocationException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */