   package temp.com.mathworks.util;

   import java.util.Iterator;










   public final class AsyncReceiverUtils
   {
     public static <T> AsyncReceiver<T> filter(final AsyncReceiver<T> paramAsyncReceiver, Predicate<T> paramPredicate)
     {
       new AsyncReceiver()
       {
         public boolean receive(T paramAnonymousT)
         {
           return (!this.val$predicate.accept(paramAnonymousT)) || (paramAsyncReceiver.receive(paramAnonymousT));
         }

         public void finished()
         {
           paramAsyncReceiver.finished();
         }
       };
     }









     public static <T> void chainInvoke(final Iterator<ParameterRunnable<AsyncReceiver<T>>> paramIterator, AsyncReceiver<T> paramAsyncReceiver)
     {
       if (!paramIterator.hasNext()) {
         paramAsyncReceiver.finished();
       }
       else {
         final Holder localHolder = new Holder();
         localHolder.set(Boolean.valueOf(false));
         ((ParameterRunnable)paramIterator.next()).run(new AsyncReceiver()
         {
           public boolean receive(T paramAnonymousT)
           {
             if (!this.val$argReceiver.receive(paramAnonymousT))
               localHolder.set(Boolean.valueOf(true));
             return !((Boolean)localHolder.get()).booleanValue();
           }

           public void finished()
           {
             if (!((Boolean)localHolder.get()).booleanValue()) {
               AsyncReceiverUtils.chainInvoke(paramIterator, this.val$argReceiver);
             }
           }
         });
       }
     }




     public static <T, U> AsyncReceiver<U> convert(final AsyncReceiver<T> paramAsyncReceiver, Converter<U, T> paramConverter)
     {
       new AsyncReceiver()
       {
         public boolean receive(U paramAnonymousU)
         {
           Object localObject = this.val$argConverter.convert(paramAnonymousU);
           return (localObject == null) || (paramAsyncReceiver.receive(localObject));
         }

         public void finished()
         {
           paramAsyncReceiver.finished();
         }
       };
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\AsyncReceiverUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */