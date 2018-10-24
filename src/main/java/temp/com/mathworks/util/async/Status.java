   package temp.com.mathworks.util.async;

   import java.util.ArrayList;
   import java.util.Collection;
   import java.util.List;
   import java.util.concurrent.Future;
   import java.util.concurrent.TimeUnit;









































































































   public abstract class Status<T>
     implements Future
   {
     public abstract Status<T> on(Callback<T> paramCallback);

     public abstract boolean isCompleted();

     public abstract boolean isFailed();

     public abstract boolean isSucceded();

     public T get()
       throws InterruptedException
     {
       return (T)get(0L, TimeUnit.MILLISECONDS);
     }





     public T get(long paramLong, TimeUnit paramTimeUnit)
       throws InterruptedException
     {
       await(paramLong, paramTimeUnit);
       return (T)getResult();
     }




     protected abstract T getResult();



     public boolean await(long paramLong)
       throws InterruptedException
     {
       return await(paramLong, TimeUnit.MILLISECONDS);
     }








     public abstract boolean await(long paramLong, TimeUnit paramTimeUnit)
       throws InterruptedException;







     public Status<T> assertSuccess()
     {
       try
       {
         if ((!await(0L)) || (!isSucceded())) {
           throw new IllegalStateException("Status is not succeded");
         }
         return this;
       } catch (InterruptedException localInterruptedException) {
         throw new IllegalStateException(localInterruptedException);
       }
     }






     public Status<T> assertFailure()
     {
       try
       {
         if ((!await(0L)) || (!isFailed())) {
           throw new IllegalStateException("Status is not failed");
         }
         return this;
       } catch (InterruptedException localInterruptedException) {
         throw new IllegalStateException(localInterruptedException);
       }
     }

     public boolean cancel(boolean paramBoolean) {
       return false;
     }

     public boolean isCancelled() {
       return false;
     }

     public boolean isDone() {
       return isCompleted();
     }

     public static abstract class Completed<T>
       extends Status<T>
     {
       protected final T fArg;

       protected Completed()
       {
         this.fArg = null;
       }

       public Completed(T paramT) {
         this.fArg = paramT;
       }

       public Status<T> on(Callback<T> paramCallback)
       {
         paramCallback.complete(this.fArg);
         return this;
       }

       public boolean isCompleted()
       {
         return true;
       }

       public boolean isFailed()
       {
         return false;
       }

       public boolean isSucceded()
       {
         return false;
       }

       protected T getResult()
       {
         return (T)this.fArg;
       }

       public boolean await(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException
       {
         return true;
       }
     }







     public static final class Success<T>
       extends Completed<T>
     {
       public Success() {}






       public Success(T paramT)
       {
         super();
       }

       public Status<T> on(Callback<T> paramCallback)
       {
         super.on(paramCallback);
         paramCallback.success(this.fArg);
         return this;
       }

       public boolean isSucceded()
       {
         return true;
       }
     }







     public static final class Fail<T>
       extends Completed<T>
     {
       public Fail() {}






       public Fail(T paramT)
       {
         super();
       }

       public Status<T> on(Callback<T> paramCallback)
       {
         super.on(paramCallback);
         paramCallback.fail(this.fArg);
         return this;
       }

       public boolean isFailed()
       {
         return true;
       }
     }

     public static abstract class BaseFuture<T> extends Status<T>
     {
       protected List<Callback<T>> fList;
       protected T fArgOnCompletion;

       public Status<T> on(Callback<T> paramCallback)
       {
         boolean bool2;
         boolean bool1;
         Object localObject1;
         synchronized (this) {
           bool2 = isFailed();
           bool1 = isSucceded();
           if ((!bool2) && (!bool1)) {
             if (this.fList == null) {
               this.fList = new ArrayList(4);
             }
             this.fList.add(paramCallback);
           }
           localObject1 = this.fArgOnCompletion;
         }

         if (bool1) {
           paramCallback.complete(localObject1);
           paramCallback.success(localObject1);
         } else if (bool2) {
           paramCallback.complete(localObject1);
           paramCallback.fail(localObject1);
         }

         return this;
       }

       protected synchronized T getResult()
       {
         return (T)this.fArgOnCompletion;
       }

       public boolean await(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException
       {
         boolean bool;
         synchronized (this) {
           bool = isCompleted();
           if (!bool) {
             wait(paramTimeUnit.toMillis(paramLong));
             bool = isCompleted();
           }
         }
         return bool;
       }

       protected void succedeList(List<Callback<T>> paramList, T paramT) {
         if (paramList != null) {
           for (Callback localCallback : paramList) {
             localCallback.complete(paramT);
             localCallback.success(paramT);
           }
         }
       }

       protected void failList(List<Callback<T>> paramList, T paramT) {
         if (paramList != null) {
           for (Callback localCallback : paramList) {
             localCallback.complete(paramT);
             localCallback.fail(paramT);
           }
         }
       }
     }






     public static final class Future<T>
       extends BaseFuture<T>
     {
       private boolean isFailed;





       private boolean isSucceded;






       public void success(T paramT)
       {
         List localList;




         synchronized (this) {
           if ((this.isFailed) || (this.isSucceded)) { throw new IllegalStateException("Status has been already completed");
           }
           this.isSucceded = true;
           this.fArgOnCompletion = paramT;
           localList = this.fList;

           notifyAll();
         }

         succedeList(localList, paramT);
       }

       public void fail(T paramT) {
         List localList;
         synchronized (this) {
           if ((this.isFailed) || (this.isSucceded)) { throw new IllegalStateException("Status has been already completed");
           }
           this.isFailed = true;
           this.fArgOnCompletion = paramT;
           localList = this.fList;

           notifyAll();
         }

         failList(localList, paramT);
       }

       public synchronized boolean isCompleted()
       {
         return (this.isSucceded) || (this.isFailed);
       }

       public synchronized boolean isFailed()
       {
         return this.isFailed;
       }

       public synchronized boolean isSucceded()
       {
         return this.isSucceded;
       }
     }

     public static final class And extends BaseFuture<Object[]>
     {
       private final Status[] fStatuses;
       private int fCounter;

       public And(Collection<Status> paramCollection) {
         this((Status[])paramCollection.toArray(new Status[paramCollection.size()]), 0);
       }

       public And(Status... paramVarArgs) {
         this((Status[])paramVarArgs.clone(), 0);
       }

       private And(Status[] paramArrayOfStatus, int paramInt) {
         this.fStatuses = paramArrayOfStatus;
         this.fCounter = paramInt;

         Callback local1 = new Callback()
         {
           public void success(Object paramAnonymousObject) {
             completeIfDone(paramAnonymousObject);
           }

           public void fail(Object paramAnonymousObject)
           {
             completeIfDone(paramAnonymousObject);
           }

           private void completeIfDone(Object paramAnonymousObject) {
             List localList = null;
             Object[] arrayOfObject = null;
             boolean bool = false;
             synchronized (And.this) {
               ((Object[])And.this.fArgOnCompletion)[And.access$008(And.this)] = paramAnonymousObject;
               if (And.this.fCounter >= And.this.fStatuses.length) {
                 localList = And.this.fList;
                 arrayOfObject = (Object[])And.this.fArgOnCompletion;
                 bool = And.this.isSucceded();
               }
             }

             if (localList != null) {
               if (bool) {
                 And.this.succedeList(localList, arrayOfObject);
               } else {
                 And.this.failList(localList, arrayOfObject);
               }

             }
           }
         };
         this.fArgOnCompletion = new Object[paramArrayOfStatus.length];
         for (Status localStatus : paramArrayOfStatus)
         {
           localStatus.on(local1);
         }
       }

       public Status[] getStatuses() {
         return this.fStatuses;
       }

       public synchronized boolean isCompleted()
       {
         for (Status localStatus : this.fStatuses) {
           if (!localStatus.isCompleted()) return false;
         }
         return true;
       }

       public synchronized boolean isFailed()
       {
         for (Status localStatus : this.fStatuses) {
           if (localStatus.isFailed()) return true;
         }
         return false;
       }

       public synchronized boolean isSucceded()
       {
         for (Status localStatus : this.fStatuses) {
           if (!localStatus.isSucceded()) return false;
         }
         return true;
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\async\Status.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */